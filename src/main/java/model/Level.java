package model;

import model.objects.systems.NetworkSystem;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Level implements Serializable {
    protected ArrayList<NetworkSystem> systems;
    protected double time;
    protected int number ;
    protected String message;

    public Level(){
        systems = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    protected ArrayList<NetworkSystem> getSystems() {
        return systems;
    }

    public void setSystems(ArrayList<NetworkSystem> systems) {
        this.systems = systems;
    }

    public void addSystem(NetworkSystem system){
        systems.add(system);
    }
    public void removeSystem(NetworkSystem system){
        systems.remove(system);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
