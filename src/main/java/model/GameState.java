package model;

import events.EventBus;
import events.GameEvents;
import events.ShopEvents;
import model.objects.other.Collision;
import model.objects.other.Connection;
import model.objects.packets.Packet;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;


import java.io.Serializable;
import java.util.ArrayList;


public class GameState implements Serializable {
    private Level gameLevel;
    private ArrayList<Connection> connections;
    private ArrayList<Packet> packets;
    private ArrayList<Packet> initialPackets;
    private ArrayList<Collision> collisions;
    private ArrayList<NetworkSystem> networkSystems;
    private int coin;
    private double currentLengthUsed;
    private int totalPackets;
    private int lostPackets;

    public GameState() {
        setupEventListeners();
        this.connections = new ArrayList<>();
        this.packets = new ArrayList<>();
        this.collisions = new ArrayList<>();
        this.networkSystems = new ArrayList<>();
        this.initialPackets = new ArrayList<>();
    }

    public GameState(Level level) {
        this();
        this.gameLevel = level;
        initialState();
    }


    public void goToLevel(Level level){
        this.gameLevel = level;

        addNewSystems(level.systems);
        packets = initialPackets;
        for(Packet packet : packets){
            RooterSystem rooterSystem =(RooterSystem) packet.getCurrentSystem();
            rooterSystem.addPacket(packet);
        }
        for(NetworkSystem system : level.getSystems()){
            if(system instanceof RooterSystem){
                packets.addAll(((RooterSystem) system).getInitialPackets());
            }
        }
        totalPackets=packets.size();
        lostPackets = 0 ;
        clonePackets();
    }

    private void setupEventListeners() {

        EventBus.subscribe(GameEvents.PacketLostEvent.class, e -> {
            packets.remove(e.packet());
            lostPackets++;
        });
        EventBus.subscribe(GameEvents.SwapPacketEvent.class, e -> {
            if(packets.contains(e.from())){
                packets.remove(e.from());
            }
            if(!packets.contains(e.to())){
                packets.add(e.to());
            }
        });


        EventBus.subscribe(GameEvents.CoinGeneratedEvent.class, e -> {
            coin += e.n();
        });

        EventBus.subscribe(ShopEvents.PowerUpEvent.class , e->
        {coin-=e.powerUpType().getPrice();});
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
        addNewSystems(gameLevel.systems);

        gameLevel.getSystems().forEach(system -> {
            if (system instanceof RooterSystem) {
                packets.addAll(((RooterSystem) system).getInitialPackets());
                totalPackets += ((RooterSystem) system).getInitialPackets().size();
            }
        });
        clonePackets();
    }

    private void clonePackets(){
        initialPackets = new ArrayList<>();
        for(Packet p : packets){
            initialPackets.add(p.clon());
        }
    }

    private void addNewSystems(ArrayList<NetworkSystem> newSystems){
        networkSystems.addAll(newSystems);
    }

    public Level getGameLevel() {
        return gameLevel;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
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

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }


    public double getCurrentLengthUsed() {
        return currentLengthUsed;
    }

    public void setCurrentLengthUsed(double currentLengthUsed) {
        this.currentLengthUsed = currentLengthUsed;
    }
    public ArrayList<NetworkSystem> getNetworkSystems() {
        return networkSystems;
    }

    public double getPacketLossPercentage() {
        return totalPackets == 0 ? 0 : (double) lostPackets / totalPackets * 100;
    }
}