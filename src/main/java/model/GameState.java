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
import java.util.List;

public class GameState implements Serializable {
    private Level gameLevel;
    private final List<Connection> connections = new ArrayList<>();
    private final List<Packet> packets = new ArrayList<>();
    private final List<Packet> initialPackets = new ArrayList<>();
    private final List<Collision> collisions = new ArrayList<>();
    private final List<NetworkSystem> networkSystems = new ArrayList<>();
    private int coin;
    private int totalPackets;
    private int lostPackets;
    private double timePassed;

    public GameState() {
        setupEventListeners();
    }

    public GameState(Level level) {
        this();
        this.gameLevel = level;
        initializeState();
    }

    public void goToLevel(Level level) {
        this.gameLevel = level;
        timePassed = 0;
        addNewSystems(level.getSystems());
        resetPacketsToInitial();
        addInitialPacketsFromLevelSystems(level.getSystems());
        totalPackets = packets.size();
        lostPackets = 0;
        clonePackets();
    }

    private void setupEventListeners() {
        EventBus.subscribe(GameEvents.PacketLostEvent.class, e -> {
            packets.remove(e.packet());
            lostPackets++;
        });

        EventBus.subscribe(GameEvents.SwapPacketEvent.class, e -> {
            packets.remove(e.from());
            if (!packets.contains(e.to())) {
                packets.add(e.to());
            }
        });

        EventBus.subscribe(GameEvents.ConnectionDestroyEvent.class, e -> {
            e.connection().disconnect();
            connections.remove(e.connection());
        });

        EventBus.subscribe(GameEvents.CoinGeneratedEvent.class, e -> {
            coin += e.n();
        });

        EventBus.subscribe(ShopEvents.PowerUpEvent.class, e -> {
            coin -= e.powerUpType().getPrice();
        });
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
        totalPackets = packets.size();
        clonePackets();
    }

    private void addInitialPacketsFromLevelSystems(List<NetworkSystem> systems) {
        for (NetworkSystem system : systems) {
            if (system instanceof RooterSystem) {
                List<Packet> initial = ((RooterSystem) system).getInitialPackets();
                packets.addAll(initial);
                totalPackets += initial.size();
            }
        }
    }

    private void resetPacketsToInitial() {
        packets.clear();
        packets.addAll(initialPackets);
        for (Packet packet : packets) {
            RooterSystem rooterSystem = (RooterSystem) packet.getCurrentSystem();
            rooterSystem.addPacket(packet);
            if (packet.getCurrentConnection() != null) {
                packet.getCurrentConnection().setBusy(false);
            }
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

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections.clear();
        if (connections != null) {
            this.connections.addAll(connections);
        }
    }

    public List<Packet> getInitialPackets() {
        return initialPackets;
    }

    public void setInitialPackets(List<Packet> initialPackets) {
        this.initialPackets.clear();
        if (initialPackets != null) {
            this.initialPackets.addAll(initialPackets);
        }
    }

    public List<Packet> getPackets() {
        return packets;
    }

    public void setPackets(List<Packet> packets) {
        this.packets.clear();
        if (packets != null) {
            this.packets.addAll(packets);
        }
    }

    public List<Collision> getCollisions() {
        return collisions;
    }

    public void setCollisions(List<Collision> collisions) {
        this.collisions.clear();
        if (collisions != null) {
            this.collisions.addAll(collisions);
        }
    }

    public List<NetworkSystem> getNetworkSystems() {
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

    public int getTotalPackets() {
        return totalPackets;
    }

    public void setTotalPackets(int totalPackets) {
        this.totalPackets = totalPackets;
    }

    public int getLostPackets() {
        return lostPackets;
    }

    public void setLostPackets(int lostPackets) {
        this.lostPackets = lostPackets;
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

    public double getPacketLossPercentage() {
        return totalPackets == 0 ? 0 : (double) lostPackets / totalPackets * 100;
    }
}