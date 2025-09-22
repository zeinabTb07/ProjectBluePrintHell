package shared.model.objects.packets;
import client.Constants;
import shared.model.interfaces.Cloneable;
import shared.api.enums.PacketType;
import shared.api.service.mapper.Records;
import shared.api.service.mapper.SpeedRules;
import shared.utils.math.Vector2D;
import shared.model.interfaces.Forceable;
import shared.model.interfaces.Movable;
import shared.model.objects.other.Connection;
import shared.model.objects.GameObject;
import shared.model.objects.systems.NetworkSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

public abstract class Packet extends GameObject implements Movable  , Forceable , Serializable , Cloneable<Packet> {
    protected static transient final Logger log = LoggerFactory.getLogger(Packet.class);
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


    public Packet(NetworkSystem system , PacketType packetType){
        super();
        this.type = packetType;
        this.currentSystem = system;
        centerOfMass = new Point();
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
        Records.PacketMovement packetRecord = SpeedRules.getProperties(type, connection.getSource().getPortType());
        this.velocity = packetRecord.speed()* Constants.PACKET_SPEED;
        this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
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
    }

    public double getDistancePassedOnConnection() {
        return distance;
    }

    public void setDistancePassedOnConnection(double distance) {
        this.distance = distance;
    }

    public Point2D getCenterOfMass() {
        return centerOfMass;
    }

    public void setCenterOfMass(Point2D centerOfMass) {
        this.centerOfMass = centerOfMass;
    }

    public Connection getCurrentConnection() {
        return currentConnection;
    }

    public void setCurrentConnection(Connection currentConnection) {
        this.currentConnection = currentConnection;
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
    }
    @Override
    public void moveNormal(double deltaTime) {
        int sign = comeBack ? -1 : 1;
        velocity += sign*acceleration * deltaTime;
        distance += sign* velocity * deltaTime;
    }

    public boolean isComeBack() {
        return comeBack;
    }

    public void setComeBack(boolean comeBack) {
        this.comeBack = comeBack;
    }
}
