package model.objects.other;

import model.constants.Constants;
import model.objects.GameObject;
import utils.Vector2D;
import model.enums.PacketType;
import model.interfaces.Updatable;

import java.awt.*;
import model.objects.packets.Packet;

import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Collision extends GameObject implements Updatable , Serializable {
    private Point p;
    private double radius;
    private Set<Packet> affectedPackets;

    public Collision(Point p) {
        super();
        this.p = p;
        this.radius = 0;
        this.affectedPackets = new HashSet<>();
    }

    private void makeShape(){
        shape = new  Ellipse2D.Double(p.x - radius/2, p.y - radius/2, radius, radius);
    }

    @Override
    public void update() {
        radius += Constants.WAVE_SPEED;
        makeShape();
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
        if(packet.getType()== PacketType.BITE){
            return;
        }
        Vector2D v = new Vector2D(getPoint() , packet.getAbsolutePoint());
        if( Math.abs(v.magnitude()-getRadius())<30){
            packet.moveInduced(v.multiply(300/(v.magnitude()* v.magnitude())));
        }
    }
}