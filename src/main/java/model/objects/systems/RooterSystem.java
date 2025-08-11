package model.objects.systems;

import model.objects.packets.Packet;

import java.awt.*;
import java.util.ArrayList;

public class RooterSystem extends NetworkSystem {
    protected ArrayList<Packet> initialPackets;

    public RooterSystem(Point point) {
        super(point);
        initialPackets = new ArrayList<>();
    }

    public void addPacket(Packet packet){
        initialPackets.add(packet);
    }

    public void removePacket(Packet packet){
        initialPackets.remove(packet);
    }

    public ArrayList<Packet> getInitialPackets() {
        return initialPackets;
    }

    public void setInitialPackets(ArrayList<Packet> initialPackets) {
        this.initialPackets = initialPackets;
    }
}
