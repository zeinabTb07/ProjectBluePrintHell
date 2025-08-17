package model.objects.packets;
import model.constants.Constants;
import model.interfaces.Forceable;
import model.interfaces.Movable;
import model.interfaces.Updatable;
import model.objects.other.Connection;
import model.objects.GameObject;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Packet extends GameObject implements Updatable , Movable  , Forceable {
    protected int size;
    protected int coin;
    protected int noise;
    protected double velocity;
    protected double acceleration;
    protected NetworkSystem currentSystem;
    protected Connection currentConnection;
    protected double distance;
    protected Point2D centerOfMass;


    public Packet(RooterSystem system){
        super();
        this.currentSystem = system;
        centerOfMass = new Point();
    }

    public abstract void sendTo(Connection connection);

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
}
