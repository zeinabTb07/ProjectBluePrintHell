package controller;

import events.EventBus;
import events.GameEvents;
import events.ShopEvents;
import events.UIEvents;
import model.enums.PacketType;
import model.objects.other.Collision;
import model.constants.Constants;
import model.objects.packets.Packet;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class CollisionController {
    private final List<Packet> packets;
    private final List<Collision> collisions;
    private final Map<Point , Double> acclertionMap;
    private final Map<Point , Double>  backCenterMap;
    public CollisionController(List<Packet> packets, List<Collision> collisions) {
        this.packets = packets;
        this.collisions = collisions;
        acclertionMap = new HashMap<>();
        backCenterMap = new HashMap<>();
        setupEventListeners();
    }

    private void setupEventListeners() {
        EventBus.subscribe(GameEvents.SetPowerUpPoint.class , e->{
            if(e.type()== ShopEvents.PowerUpType.ALIGN_CENTER){
                backCenterMap.put(e.point() , Double.valueOf(0));
            } else if (e.type()==ShopEvents.PowerUpType.ZERO_ACCELERATION) {
                acclertionMap.put(e.point() , Double.valueOf(0));
            }
        });
    }

    private void checkForPowerUp(Packet packet){
        acclertionMap.values().forEach(aDouble -> {aDouble +=0.02;});
        backCenterMap.values().forEach(aDouble -> {aDouble +=0.02;});
        for(Point p : acclertionMap.keySet()){
            if (packet.getAbsolutePoint().distance(p)<50){
                packet.setAcceleration(0);
            }
        }
        for(Point p : backCenterMap.keySet()){
            if (packet.getAbsolutePoint().distance(p)<50){
                Point2D point = packet.getCenterOfMass();
                point.setLocation(point.getX()/2 , point.getY()/2);
                packet.setCenterOfMass(point);
            }
        }

        backCenterMap.entrySet().removeIf(entry -> entry.getValue() > 100.0);
        acclertionMap.entrySet().removeIf(entry -> entry.getValue() > 50.0);

    }


    public void checkForCollision() {
        for (Collision collision : new ArrayList<>(collisions)) {
            collision.update();
            if (collision.getRadius() > Constants.MAX_WAVE_R) {
                collisions.remove(collision);
            }
        }
        for (int i = 0; i < packets.size(); i++) {
            Packet packet = packets.get(i);
            checkForPowerUp(packet);
            for (int j = i + 1; j < packets.size(); j++) {
                Packet packet1 = packets.get(j);
                if (packet.getCurrentConnection() != null && packet1.getCurrentConnection() != null && packet.getDistancePassedOnConnection() > 2) {
                    if (checkCollision(packet, packet1)) {
                        Point p = packet.getAbsolutePoint();
                        Point p1 = packet1.getAbsolutePoint();
                        packet.increaseNoise(packet1.getSize()/2);
                        packet1.increaseNoise(packet.getSize()/2);
                        if(packet.getType()== PacketType.BITE) packet.setComeBack(!packet.isComeBack());
                        Point colCenter = new Point((p.x + p1.x) / 2, (p.y + p1.y) / 2);
                        collisions.add(new Collision(colCenter));
                        EventBus.publish(new UIEvents.PlaySound("src/main/resources/collision.wav"));
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
        Shape shape1 = firstPacket.getShape();
        Shape shape2 = secondPacket.getShape();

        if (shape1 == null || shape2 == null) {
            return false;
        }

        Area area1 = new Area(shape1);
        Area area2 = new Area(shape2);

        area1.intersect(area2);

        return !area1.isEmpty();
    }
}
