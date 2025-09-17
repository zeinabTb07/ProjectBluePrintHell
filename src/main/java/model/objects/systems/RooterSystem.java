package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.objects.packets.ColossusPacket;
import model.objects.packets.MessagerPacket;
import model.objects.packets.Packet;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class RooterSystem extends NetworkSystem implements Serializable {
    protected ArrayList<Packet> initialPackets;

    public RooterSystem(Point point) {
        super(point);
        initialPackets = new ArrayList<>();
    }

    @Override
    public void process() {
        if (!initialPackets.isEmpty()) {
            trySendingPacket(initialPackets.get(0));
            if (initialPackets.get(0).getCurrentConnection() != null) {
                initialPackets.removeFirst();
            }
        }

    }

    @Override
    public void receivePacket(Packet p) {
        if(p instanceof ColossusPacket){
            p.getCurrentConnection().decreaseStrength();
        }
        if(p instanceof MessagerPacket){
            MessagerPacket packet =(MessagerPacket) p;
            if(packet.getParentColossusId()!=null){
                EventBus.publish(new GameEvents.PacketLostEvent(p));
            }
        }
        p.setCurrentSystem(this);
        EventBus.publish(new GameEvents.CoinGeneratedEvent(p.getSize()));
    }
    @Override
    public void reset(){
        super.reset();
        initialPackets = new ArrayList<>();
    }

    public void addPacket(Packet packet) {
        initialPackets.add(packet);
    }

    public void removePacket(Packet packet) {
        initialPackets.remove(packet);
    }

    public ArrayList<Packet> getInitialPackets() {
        return initialPackets;
    }

    public void setInitialPackets(ArrayList<Packet> initialPackets) {
        this.initialPackets = initialPackets;
    }

}
