package controller;

import events.EventBus;
import events.GameEvents;
import events.ShopEvents;
import events.UIEvents;
import model.constants.Constants;
import model.objects.packets.Connection;
import model.objects.packets.Packet;
import model.objects.systems.NetworkSystem;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PacketController {
    private final List<Packet> packets;

    public PacketController(ArrayList<Packet> packets) {
        this.packets = packets;
        setupEventListeners();
    }

    private void setupEventListeners() {
        EventBus.subscribe(ShopEvents.PowerUpEvent.class, e -> {
            ShopEvents.PowerUpType powerUp = e.powerUpType();
            if (powerUp == ShopEvents.PowerUpType.CLEAR_NOISE) {
                for (Packet packet : packets) {
                    packet.setNoise(0);
                }
            }
        });
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
                    NetworkSystem end = con.getTarget().getParentSystem();
                    end.receivePacket(packet);
                    resetPacket(packet);
                }

                if (isPacketFallen(packet) || isPacketDisruptedByNoise(packet)) {
                    EventBus.publish(new GameEvents.PacketLostEvent(packet));
                    resetPacket(packet);
                    packets.remove(packet);
                    EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/lost.wav"));
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

    private boolean isPacketReachedEnd(Packet packet) {
        Connection connection = packet.getCurrentConnection();
        double distance = packet.getDistancePassedOnConnection();
        double target = connection.getLength() / 2;
        boolean reached = distance >= target;
        return reached;
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