package controller;

import events.EventBus;
import events.ShopEvents;
import events.UIEvents;
import model.Collision;
import model.constants.Constants;
import model.objects.packets.Packet;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;

public class CollisionController {
    private final ArrayList<Packet> packets;
    private final ArrayList<Collision> collisions;
    private boolean stopImpactActive;
    private boolean stopCollisionActive;
    private double stopImpactTimeRemaining;
    private double stopCollisionTimeRemaining;

    public CollisionController(ArrayList<Packet> packets, ArrayList<Collision> collisions) {
        this.packets = packets;
        this.collisions = collisions;
        this.stopImpactActive = false;
        this.stopCollisionActive = false;
        this.stopImpactTimeRemaining = 0;
        this.stopCollisionTimeRemaining = 0;
        setupEventListeners();
    }

    private void setupEventListeners() {
        EventBus.subscribe(ShopEvents.PowerUpEvent.class, e -> {
            ShopEvents.PowerUpType powerUp = e.powerUpType();
            switch (powerUp) {
                case STOP_IMPACT:
                    stopImpactActive = true;
                    stopImpactTimeRemaining = powerUp.getDuration();
                    break;
                case STOP_COLLISION:
                    stopCollisionActive = true;
                    stopCollisionTimeRemaining = powerUp.getDuration();
                    break;
            }
        });
    }

    public void updatePowerUps(double deltaTime) {
        if (stopImpactActive) {
            stopImpactTimeRemaining -= deltaTime;
            if (stopImpactTimeRemaining <= 0) {
                stopImpactActive = false;
                stopImpactTimeRemaining = 0;
            }
        }
        if (stopCollisionActive) {
            stopCollisionTimeRemaining -= deltaTime;
            if (stopCollisionTimeRemaining <= 0) {
                stopCollisionActive = false;
                stopCollisionTimeRemaining = 0;
            }
        }
    }

    public void checkForCollision() {
        if (!stopImpactActive) {
            for (Collision collision : new ArrayList<>(collisions)) {
                collision.update();
                if (collision.getRadius() > Constants.MAX_WAVE_R) {
                    collisions.remove(collision);
                }
            }
        }
        if (!stopCollisionActive) {
            for (int i = 0; i < packets.size(); i++) {
                Packet packet = packets.get(i);
                for (int j = i + 1; j < packets.size(); j++) {
                    Packet packet1 = packets.get(j);
                    if (packet.getCurrentConnection() != null && packet1.getCurrentConnection() != null && packet.getDistancePassedOnConnection() > 2) {
                        if (checkCollision(packet, packet1)) {
                            Point p = packet.getAbsolutePoint();
                            Point p1 = packet1.getAbsolutePoint();
                            packet.increaseNoise(packet1.getSize());
                            packet1.increaseNoise(packet.getNoise());
                            Point colCenter = new Point((p.x + p1.x) / 2, (p.y + p1.y) / 2);
                            if (!stopImpactActive) {
                                collisions.add(new Collision(colCenter));
                            }
                            EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/collision.wav"));
                        }
                    }
                }
            }
        }
    }

    public void applyCollisions() {
        if (!stopImpactActive) {
            for (Collision collision : new ArrayList<>(collisions)) {
                applyCollision(collision);
            }
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
