package client.controller.offline;

import shared.api.enums.PacketType;
import shared.model.objects.other.Collision;
import client.Constants;
import shared.model.objects.packets.Packet;
import shared.api.service.mapper.PacketDetails;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CollisionController {
    private final List<Packet> packets;
    private final List<Collision> collisions;

    public CollisionController(List<Packet> packets, List<Collision> collisions) {
        this.packets = packets;
        this.collisions = collisions;
    }

    public void updateCollisions(){
        for (Collision collision : new ArrayList<>(collisions)) {
            collision.update();
            if (collision.getRadius() > Constants.MAX_WAVE_R) {
                collisions.remove(collision);
            }
        }
    }

    public void checkForCollision() {
        for (int i = 0; i < packets.size(); i++) {
            Packet packet = packets.get(i);
            for (int j = i + 1; j < packets.size(); j++) {
                Packet packet1 = packets.get(j);
                if (packet.getCurrentConnection() != null && packet1.getCurrentConnection() != null && packet.getDistancePassedOnConnection() > 2) {
                    if (checkCollision(packet, packet1)) {
                        Point p = packet.getAbsolutePoint();
                        Point p1 = packet1.getAbsolutePoint();
                        packet.increaseNoise(PacketDetails.getProperties(packet1.getType()).size() / 2);
                        packet1.increaseNoise(PacketDetails.getProperties(packet.getType()).size() / 2);
                        if (packet.getType() == PacketType.BIT_PACKET) packet.setComeBack(!packet.isComeBack());
                        Point colCenter = new Point((p.x + p1.x) / 2, (p.y + p1.y) / 2);
                        collisions.add(new Collision(colCenter));
                    }
                }
            }
        }
    }

    public void applyCollisions() {
        for (Collision collision : new ArrayList<>(collisions)) {
            applyCollision(collision);
        }
    }

    private void applyCollision(Collision collision) {
        for (Packet packet : new ArrayList<>(packets)) {
            if (packet.getCurrentConnection() != null) {
                if (!collision.hasAffectedPacket(packet)) {
                    collision.markPacketAffected(packet);
                }
            }
        }
    }

    private boolean checkCollision(Packet firstPacket, Packet secondPacket) {
        int n = PacketDetails.getProperties(firstPacket.getType()).size();
        n+=PacketDetails.getProperties(secondPacket.getType()).size();
        return firstPacket.getAbsolutePoint().distance(secondPacket.getAbsolutePoint()) < n*Constants.PACKET_SIZE_SCALE ;
    }
}
