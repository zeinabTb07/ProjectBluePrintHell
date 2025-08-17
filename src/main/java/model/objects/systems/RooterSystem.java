package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.objects.packets.Packet;

import java.awt.*;
import java.util.ArrayList;

public class RooterSystem extends NetworkSystem {
    protected ArrayList<Packet> initialPackets;

    public RooterSystem(Point point) {
        super(point);
        initialPackets = new ArrayList<>();
    }

    @Override
    public void process(){
        if (!initialPackets.isEmpty()) {
            trySendingPacket(initialPackets.get(0));
            if(initialPackets.get(0).getCurrentConnection()!=null){
                initialPackets.removeFirst();
            }
        }

    }
    @Override
    public void receivePacket(Packet p){
        p.setCurrentSystem(this);
        EventBus.publish(new GameEvents.CoinGeneratedEvent(p.getSize()));
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
