package model;

import events.EventBus;
import events.GameEvents;
import model.objects.packets.Connection;
import model.objects.packets.Packet;
import model.objects.systems.RooterSystem;

import java.awt.*;
import java.util.ArrayList;
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
    private int coin;
    private double currentLengthUsed;
    private int totalPackets;
    private int lostPackets;

    public GameState() {
        connections = new ArrayList<>();
        packets = new ArrayList<>();
        collisions = new ArrayList<>();
        totalPackets = 0;
        lostPackets = 0;
        setupEventListeners();
    }

    public GameState(Level level) {
        this();
        this.gameLevel = level;
        initialState();
    }

    private void setupEventListeners() {

        EventBus.subscribe(GameEvents.PacketLostEvent.class, e -> {
            lostPackets++;
        });
        // ردیابی سکه‌ها
        EventBus.subscribe(GameEvents.CoinGeneratedEvent.class, e -> {
            coin += e.n();
        });
    }

    public void resetLevel(Level level) {
        this.gameLevel = level;
        initialState();
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
        currentLengthUsed += connection.getLength();
    }

    public void removeConnection(Connection connection) {
        connections.remove(connection);
        currentLengthUsed -= connection.getLength();
    }

    private void initialState() {
        connections = new ArrayList<>();
        packets = new ArrayList<>();
        totalPackets = 0;
        lostPackets = 0;
        gameLevel.getSystems().forEach(system -> {
            if (system instanceof RooterSystem) {
                packets.addAll(((RooterSystem) system).getInitialPackets());
                totalPackets += ((RooterSystem) system).getInitialPackets().size();
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

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void addCoin(int n) {
        this.coin += n;
    }

    public double getCurrentLengthUsed() {
        return currentLengthUsed;
    }

    public void setCurrentLengthUsed(double currentLengthUsed) {
        this.currentLengthUsed = currentLengthUsed;
    }

    public int getTotalPackets() {
        return totalPackets;
    }

    public int getLostPackets() {
        return lostPackets;
    }

    public double getPacketLossPercentage() {
        return totalPackets == 0 ? 0 : (double) lostPackets / totalPackets * 100;
    }
}