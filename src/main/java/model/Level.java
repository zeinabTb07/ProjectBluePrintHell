package model;

import model.objects.systems.NetworkSystem;

import java.util.ArrayList;

public abstract class
Level {
    protected ArrayList<NetworkSystem> systems;
    protected double wireLength ;
    public Level(){
        systems = new ArrayList<>();
        wireLength = 0 ;
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

}
