package model.objects.packets;
import model.constants.Constants;
import model.interfaces.Cloneable;
import utils.PacketRecord;
import utils.PacketSpeedRules;
import utils.Vector2D;
import model.enums.PacketType;
import model.interfaces.Forceable;
import model.interfaces.Movable;
import model.interfaces.Updatable;
import model.objects.other.Connection;
import model.objects.GameObject;
import model.objects.systems.NetworkSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

public abstract class Packet extends GameObject implements Updatable , Movable  , Forceable , Serializable , Cloneable<Packet> {
    protected static transient final Logger log = LoggerFactory.getLogger(Packet.class);

    protected int size;
    protected int coin;
    protected int noise;
    protected double velocity;
    protected double acceleration;
    protected NetworkSystem currentSystem;
    protected Connection currentConnection;
    protected double distance;
    protected Point2D centerOfMass;
    protected PacketType type;
    protected boolean comeBack;
    protected boolean trojan;

    protected boolean dirty;

    public Packet(NetworkSystem system , PacketType packetType){
        super();
        this.type = packetType;
        this.currentSystem = system;
        this.size = type.getProperties().size();
        this.coin = type.getProperties().coin();
        centerOfMass = new Point();
        dirty = true;
    }

    protected void makeShape() {
        try {
            super.shape = getType().getShape().getShape(getAbsolutePoint(), size * Constants.PACKET_SIZE_SCALE);
        } catch (Exception e) {
            log.error("Failed to create shape for packet: {}", e.getMessage(), e);
            super.shape = new Rectangle(getAbsolutePoint().x - 5, getAbsolutePoint().y - 5, 10 , 10);
        }
    }


    public void increaseNoise(int n){
        noise+=n;
    }

    public Point getAbsolutePoint() {
        if (currentConnection == null) {
            Point p = currentSystem.getPoint();
            return new Point(
                    p.x + Constants.SYSTEMS_WIDTH / 2,
                    p.y + 2 * Constants.INDUCTOR_HEIGHT
            );
        } else {
            Point2D p = currentConnection.getRelativePoint(distance);
            return new Point((int) (centerOfMass.getX() + p.getX()),
                    (int) (centerOfMass.getY() + p.getY())
            );
        }
    }

    public boolean isTrojan() {
        return trojan;
    }

    public void setTrojan(boolean trojan) {
        this.trojan = trojan;
    }
    public void sendTo(Connection connection){
        this.currentConnection = connection;
        PacketRecord.PacketMovement packetRecord = PacketSpeedRules.getProperties(type, connection.getSource().getPortType());
        this.velocity = packetRecord.speed()* Constants.PACKET_SPEED;
        this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
        dirty = true;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public NetworkSystem getCurrentSystem() {
        return currentSystem;
    }

    public void setCurrentSystem(NetworkSystem currentSystem) {
        this.currentSystem = currentSystem;
        dirty = true;
    }

    public double getDistancePassedOnConnection() {
        return distance;
    }

    public void setDistancePassedOnConnection(double distance) {
        this.distance = distance;
        dirty = true;
    }

    public Point2D getCenterOfMass() {
        return centerOfMass;
    }

    public void setCenterOfMass(Point2D centerOfMass) {
        this.centerOfMass = centerOfMass;
        dirty = true;
    }

    public Connection getCurrentConnection() {
        return currentConnection;
    }

    public void setCurrentConnection(Connection currentConnection) {
        this.currentConnection = currentConnection;
        dirty = true;
    }

    public int getNoise() {
        return noise;
    }

    public void setNoise(int noise) {
        this.noise = noise;
    }

    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
        dirty = true;
    }

    @Override
    public void moveInduced(Vector2D forceVector) {
        Point2D oldCenter = centerOfMass;
        centerOfMass = new Point2D.Double(
                centerOfMass.getX() +  forceVector.getX(),
                centerOfMass.getY() +  forceVector.getY()
        );

        log.debug("Packet moved by force, oldCenter={}, newCenter={}, force={}",
                oldCenter, centerOfMass, forceVector);
        dirty = true;
    }
    @Override
    public void moveNormal(double deltaTime) {
        int sign = comeBack ? -1 : 1;
        velocity += sign*acceleration * deltaTime;
        distance += sign* velocity * deltaTime;
        dirty = true;
    }

    public boolean isComeBack() {
        return comeBack;
    }

    public void setComeBack(boolean comeBack) {
        this.comeBack = comeBack;
    }

    @Override
    public void update() {
        if (dirty){
            makeShape();
        }
    }
}
