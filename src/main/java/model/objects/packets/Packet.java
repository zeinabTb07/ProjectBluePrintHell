package model.objects.packets;
import model.interfaces.Movable;
import model.interfaces.Updatable;
import model.objects.GameObject;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;

import java.awt.*;

public abstract class Packet extends GameObject implements Updatable , Movable {
    protected int size;
    protected int coin;
    protected double velocity;
    protected double acceleration;
    protected NetworkSystem currentSystem;
    protected Connection currentConnection;
    protected double distance;
    protected Point centerOfMass;


    public Packet(RooterSystem system){
        super();
        this.currentSystem = system;
        centerOfMass = new Point();
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

    public Point getCenterOfMass() {
        return centerOfMass;
    }

    public void setCenterOfMass(Point centerOfMass) {
        this.centerOfMass = centerOfMass;
    }

    public Connection getCurrentConnection() {
        return currentConnection;
    }

    public void setCurrentConnection(Connection currentConnection) {
        this.currentConnection = currentConnection;
    }


}
