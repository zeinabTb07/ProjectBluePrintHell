package client.controller;

import shared.api.enums.PortType;
import client.Constants;
import shared.events.GameEvents;
import shared.model.GameState;
import shared.model.objects.other.Connection;
import shared.model.objects.packets.ColossusPacket;
import shared.model.objects.packets.Packet;
import shared.model.objects.systems.NetworkSystem;
import shared.model.objects.systems.RooterSystem;
import shared.api.service.mapper.PacketDetails;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PacketController {
    private final GameState gameState ;
    private final ArrayList<Packet> packets;

    public PacketController(GameState gameState) {
        this.gameState = gameState;
        packets = gameState.getPackets();
    }

    public void timesUp(){
        for(Packet packet :new ArrayList<>(packets)){
            if(packet.getCurrentConnection()!=null ||! (packet.getCurrentSystem() instanceof RooterSystem)){
                resetPacket(packet);
                packets.remove(packet);
            }
        }
    }

    public void updatePackets(double deltaTime) {
        for (Packet packet : new ArrayList<>(packets)) {

            if (packet.getCurrentConnection() == null) {
                NetworkSystem system = packet.getCurrentSystem();
                system.process();
            } else {
                packet.moveNormal(deltaTime);
                if (isPacketReachedEnd(packet)) {
                    Connection con = packet.getCurrentConnection();
                    if(con.getTarget().getParentSystem().isActive()){
                        if(packet instanceof ColossusPacket){
                            con.getTarget().setPortType(getRandomPortType());
                            NetworkSystem system = con.getTarget().getParentSystem();
                            for(Packet p : system.getStorage()){
                                gameState.getPublisher().publish(new GameEvents.PacketLostEvent(p));
                            }
                            system.getStorage().clear();
                        }
                        NetworkSystem end = con.getTarget().getParentSystem();
                        end.receivePacket(packet);
                        if(packet.getVelocity()>200){
                            end.setActive(false);
                        }
                        resetPacket(packet);
                    } else packet.setComeBack(true);
                }

                if(isPacketComingBack(packet)){
                    Connection con = packet.getCurrentConnection();
                    NetworkSystem head = con.getSource().getParentSystem();
                    packet.setComeBack(false);
                    head.receivePacket(packet);
                    resetPacket(packet);
                }

                if (isPacketFallen(packet) || isPacketDisruptedByNoise(packet)) {
                    gameState.getPublisher().publish(new GameEvents.PacketLostEvent(packet));
                    resetPacket(packet);
                }
            }
        }
    }

    private boolean isPacketFallen(Packet packet) {
        int size = PacketDetails.getProperties(packet.getType()).size();
        return packet.getCenterOfMass().distance(new Point(0, 0)) > size * Constants.PACKET_SIZE_SCALE + 5;
    }

    private boolean isPacketDisruptedByNoise(Packet packet) {
        int size = PacketDetails.getProperties(packet.getType()).size();
        return packet.getNoise() > size;
    }

    private boolean isPacketComingBack(Packet packet){
        return packet.getDistancePassedOnConnection()<0&&packet.isComeBack();
    }

    private boolean isPacketReachedEnd(Packet packet) {
        Connection connection = packet.getCurrentConnection();
        double distance = packet.getDistancePassedOnConnection();
        boolean reached = distance >= connection.getLength();
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