package shared.model;

import shared.events.Publisher;
import shared.model.levels.Level;
import shared.model.objects.other.Collision;
import shared.model.objects.other.Connection;
import shared.model.objects.packets.Packet;
import shared.model.objects.systems.NetworkSystem;
import shared.model.objects.systems.RooterSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameState implements Serializable {
    private Level gameLevel;
    private UUID gameID;
    private final ArrayList<Connection> connections = new ArrayList<>();
    private final ArrayList<Packet> packets = new ArrayList<>();
    private final ArrayList<Packet> initialPackets = new ArrayList<>();
    private final ArrayList<Collision> collisions = new ArrayList<>();
    private final ArrayList<NetworkSystem> networkSystems = new ArrayList<>();
    private transient Publisher publisher;
    private int coin;
    private double timePassed;


    public GameState(Level level) {
        this.gameLevel = level;
        gameID = UUID.randomUUID();
        initializeState();
    }
    public void reset(Level level){
        this.gameLevel = level;
        connections.clear();
        packets.clear();
        initialPackets.clear();
        collisions.clear();
        networkSystems.clear();
        coin = 0 ;
        timePassed = 0 ;
        initializeState();
    }

    public void goToLevel(Level level) {
        this.gameLevel = level;
        timePassed = 0;
        networkSystems.forEach(system->{system.reset();});
        connections.forEach(c ->{c.setBusy(false);});
        addNewSystems(level.getSystems());
        resetPacketsToInitial();
        addInitialPacketsFromLevelSystems(level.getSystems());
        clonePackets();
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }

    private void initializeState() {
        addNewSystems(gameLevel.getSystems());
        addInitialPacketsFromLevelSystems(gameLevel.getSystems());
        clonePackets();
    }

    private void addInitialPacketsFromLevelSystems(List<NetworkSystem> systems) {
        for (NetworkSystem system : systems) {
            if (system instanceof RooterSystem) {
                List<Packet> initial = ((RooterSystem) system).getInitialPackets();
                packets.addAll(initial);
            }
        }
    }


    private void resetPacketsToInitial() {
        packets.clear();
        packets.addAll(initialPackets);
        for (Packet packet : packets) {
            RooterSystem rooterSystem = (RooterSystem) packet.getCurrentSystem();
            rooterSystem.addPacket(packet);
        }
    }

    private void clonePackets() {
        initialPackets.clear();
        for (Packet p : packets) {
            Packet clone = p.clon();
            initialPackets.add(clone);
        }
    }

    private void addNewSystems(List<NetworkSystem> newSystems) {
        networkSystems.addAll(newSystems);
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

    public void setConnections(List<Connection> connections) {
        this.connections.clear();
        if (connections != null) {
            this.connections.addAll(connections);
        }
    }

    public ArrayList<Packet> getInitialPackets() {
        return initialPackets;
    }

    public void setInitialPackets(List<Packet> initialPackets) {
        this.initialPackets.clear();
        if (initialPackets != null) {
            this.initialPackets.addAll(initialPackets);
        }
    }

    public ArrayList<Packet> getPackets() {
        return packets;
    }

    public void setPackets(List<Packet> packets) {
        this.packets.clear();
        if (packets != null) {
            this.packets.addAll(packets);
        }
    }

    public ArrayList<Collision> getCollisions() {
        return collisions;
    }

    public void setCollisions(List<Collision> collisions) {
        this.collisions.clear();
        if (collisions != null) {
            this.collisions.addAll(collisions);
        }
    }

    public ArrayList<NetworkSystem> getNetworkSystems() {
        return networkSystems;
    }

    public void setNetworkSystems(List<NetworkSystem> networkSystems) {
        this.networkSystems.clear();
        if (networkSystems != null) {
            this.networkSystems.addAll(networkSystems);
        }
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public double getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(double timePassed) {
        this.timePassed = timePassed;
    }

    public void timePass(double delta) {
        timePassed += delta;
    }

    public UUID getGameID() {
        return gameID;
    }

    public void setGameID(UUID gameID) {
        this.gameID = gameID;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}