package model;

import model.objects.systems.NetworkSystem;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class
Level implements Serializable {
    protected ArrayList<NetworkSystem> systems;
    protected double wireLength ;
    protected double time;
    protected int number ;
    public Level(){
        systems = new ArrayList<>();
        wireLength = 0 ;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<NetworkSystem> getSystems() {
        return systems;
    }

    public void setSystems(ArrayList<NetworkSystem> systems) {
        this.systems = systems;
    }

    public double getWireLength() {
        return wireLength;
    }

    public void setWireLength(double wireLength) {
        this.wireLength = wireLength;
    }

    public void addSystem(NetworkSystem system){
        systems.add(system);
    }
    public void removeSystem(NetworkSystem system){
        systems.remove(system);
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
