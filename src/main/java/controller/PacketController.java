package controller;


import events.EventBus;
import events.GameEvents;
import model.GameState;
import model.constants.Constants;
import model.objects.packets.Connection;
import model.objects.packets.Packet;
import model.objects.systems.NetworkSystem;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PacketController{
    private List<Packet> packets;

    public PacketController(ArrayList<Packet> packets){
        this.packets = packets;
        EventBus.subscribe(GameEvents.PacketReachedEnd.class,e->{packets.remove(e.packet());});
    }


    public void updatePackets(double deltaTime) {
        for(Packet packet: new ArrayList<>(packets)) {
            packet.update();
            if (packet.getCurrentConnection() == null) {
                NetworkSystem system = packet.getCurrentSystem();
                system.process();
            } else {
                packet.moveNormal(deltaTime);

                if(isPacketFallen(packet)||isPacketDisruptedByNoise(packet)){
                    EventBus.publish(new GameEvents.PacketLostEvent(packet));
                    packets.remove(packet);
                }

                if(isPacketReachedEnd(packet)){
                    Connection con = packet.getCurrentConnection();
                    NetworkSystem end = con.getTarget().getParentSystem();
                    end.receivePacket(packet);
                    resetPacket(packet);
                }
            }
        }
    }

    public NetworkSystem getReceiverSystem(Connection connection){
        return connection.getTarget().getParentSystem();
    }


    private boolean isPacketFallen(Packet packet){
        return packet.getCenterOfMass().distance(new Point(0 , 0))>packet.getSize()* Constants.PACKET_SIZE_SCALE+5;
    }

    private boolean isPacketDisruptedByNoise(Packet packet){
        return packet.getNoise() > packet.getSize();
    }

    private boolean isPacketReachedEnd(Packet packet){
        Connection connection = packet.getCurrentConnection();
        return Math.abs(packet.getDistancePassedOnConnection() - connection.getLength()/2) < 1;
    }

    private void resetPacket(Packet packet) {
        packet.setNoise(0);
        packet.setDistancePassedOnConnection(0);
        packet.setCenterOfMass(new Point(0, 0));
        packet.setVelocity(0);
        packet.setAcceleration(0);
        Connection con = packet.getCurrentConnection();
        if (con != null) {
            con.setBusy(false);
            packet.setCurrentConnection(null);
        }
    }
}
