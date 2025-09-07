package controller;

import events.EventBus;
import events.GameEvents;
import events.ShopEvents;
import model.constants.Constants;
import model.enums.PortType;
import model.objects.other.Connection;
import model.objects.packets.ColossusPacket;
import model.objects.packets.Packet;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PacketController {
    private final List<Packet> packets;

    public PacketController(ArrayList<Packet> packets) {
        this.packets = packets;
        setupEventListeners();
    }

    private void setupEventListeners() {
    }

    public void timesUp(){
        for(Packet packet :new ArrayList<>(packets)){
            if(packet.getCurrentConnection()!=null ||! (packet.getCurrentSystem() instanceof RooterSystem)){
                EventBus.publish(new GameEvents.PacketLostEvent(packet));
                resetPacket(packet);
                packets.remove(packet);
            }
        }
    }

    public void updatePackets(double deltaTime) {
        for (Packet packet : new ArrayList<>(packets)) {
            packet.update();
            if (packet.getCurrentConnection() == null) {
                NetworkSystem system = packet.getCurrentSystem();
                system.process();
            } else {
                packet.moveNormal(deltaTime);

                if (isPacketReachedEnd(packet)) {
                    Connection con = packet.getCurrentConnection();
                    if(packet instanceof ColossusPacket){
                        con.getTarget().setPortType(getRandomPortType());
                        con.getTarget().getParentSystem().setDirty(true);
                    }
                    NetworkSystem end = con.getTarget().getParentSystem();
                    end.receivePacket(packet);
                    resetPacket(packet);
                }

                if(isPacketComingBack(packet)){
                    Connection con = packet.getCurrentConnection();
                    NetworkSystem head = con.getSource().getParentSystem();
                    head.receivePacket(packet);
                    resetPacket(packet);
                }

                if (isPacketFallen(packet) || isPacketDisruptedByNoise(packet)) {
                    resetPacket(packet);
                    EventBus.publish(new GameEvents.PacketLostEvent(packet));
                }
            }
        }
    }

    public NetworkSystem getReceiverSystem(Connection connection) {
        return connection.getTarget().getParentSystem();
    }

    private boolean isPacketFallen(Packet packet) {
        return packet.getCenterOfMass().distance(new Point(0, 0)) > packet.getSize() * Constants.PACKET_SIZE_SCALE + 5;
    }

    private boolean isPacketDisruptedByNoise(Packet packet) {
        return packet.getNoise() > packet.getSize();
    }

    private boolean isPacketComingBack(Packet packet){
        return packet.getDistancePassedOnConnection()<0&&packet.getVelocity()<0;
    }

    private boolean isPacketReachedEnd(Packet packet) {
        Connection connection = packet.getCurrentConnection();
        double distance = packet.getDistancePassedOnConnection();
        double target = connection.getLength();
        boolean reached = distance >= target;
        return reached;
    }

    private void resetPacket(Packet packet) {
        packet.setDistancePassedOnConnection(0);
        packet.setVelocity(0);
        packet.setAcceleration(0);
        Connection con = packet.getCurrentConnection();
        if (con != null) {
            con.setBusy(false);
            packet.setCurrentConnection(null);
        }
    }

    private PortType getRandomPortType(){
        PortType[] types = PortType.values();
        Random random = new Random();
        return types[random.nextInt(0 , types.length)];
    }
}