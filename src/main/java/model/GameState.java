package model;

import events.EventBus;
import events.GameEvents;
import model.objects.packets.Connection;
import model.objects.packets.Packet;
import model.objects.systems.RooterSystem;

import java.awt.*;
import java.util.ArrayList;

public class GameState {

    private Level gameLevel;
    private ArrayList<Connection> connections;
    private ArrayList<Packet> packets;
    private ArrayList<Collision> collisions;

    public GameState(){
        connections = new ArrayList<>();
        packets = new ArrayList<>();
        connections = new ArrayList<>();
        collisions = new ArrayList<>();
    }

    public GameState(Level level){
        this();
        this.gameLevel = level;
        initialState();

    }
    public void resetLevel(Level level){
        this.gameLevel = level;
        initialState();
    }

    public void addConnection(Connection connection){
        connections.add(connection);
    }
    public void removeConnection(Connection connection){
        connections.remove(connection);
    }

    private void initialState() {
        connections = new ArrayList<>();
        packets = new ArrayList<>();
        gameLevel.getSystems().forEach(system -> {
            if (system instanceof RooterSystem) {
                packets.addAll(((RooterSystem) system).getInitialPackets());
            }
        });
    }

    public Level getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(Level gameLevel) {
        this.gameLevel = gameLevel;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }

    public ArrayList<Packet> getPackets() {
        return packets;
    }

    public void setPackets(ArrayList<Packet> packets) {
        this.packets = packets;
    }

    public ArrayList<Collision> getCollisions() {
        return collisions;
    }

    public void setCollisions(ArrayList<Collision> collisions) {
        this.collisions = collisions;
    }
}