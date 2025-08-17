package model.objects.other;

import model.constants.Constants;
import model.constants.Vector2D;
import model.interfaces.Updatable;

import java.awt.*;
import model.objects.packets.Packet;

import java.util.HashSet;
import java.util.Set;

public class Collision implements Updatable {
    private Point p;
    private double radius;
    private Set<Packet> affectedPackets; // لیست پکت‌هایی که این collision بهشون اثر کرده

    public Collision(Point p) {
        this.p = p;
        this.radius = 0;
        this.affectedPackets = new HashSet<>();
    }

    @Override
    public void update() {
        radius += Constants.WAVE_SPEED;
    }

    public Point getPoint() {
        return p;
    }

    public void setPoint(Point p) {
        this.p = p;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean hasAffectedPacket(Packet packet) {
        return affectedPackets.contains(packet);
    }


    public void markPacketAffected(Packet packet) {
        affectedPackets.add(packet);
        applyCollision(packet);
    }

    private void applyCollision(Packet packet){
        Vector2D v = new Vector2D(getPoint() , packet.getAbsolutePoint());
        if( Math.abs(v.magnitude()-getRadius())<30){
            packet.moveInduced(v.multiply(500/(v.magnitude()* v.magnitude())));
        }
    }
}