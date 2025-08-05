package model.packets;
import model.systems.NetworkSystem;
import java.awt.*;

public abstract class Packet {
    protected int size;
    protected int coin;
    protected double velocity;
    protected double acceleration;
    protected NetworkSystem currentSystem;
    protected double distancePassedOnConnection;
    protected Point centerOfMass;

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
        return distancePassedOnConnection;
    }

    public void setDistancePassedOnConnection(double distancePassedOnConnection) {
        this.distancePassedOnConnection = distancePassedOnConnection;
    }

    public Point getCenterOfMass() {
        return centerOfMass;
    }

    public void setCenterOfMass(Point centerOfMass) {
        this.centerOfMass = centerOfMass;
    }

}
