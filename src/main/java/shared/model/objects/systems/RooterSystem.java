package shared.model.objects.systems;

import shared.model.objects.packets.ColossusPacket;
import shared.model.objects.packets.Packet;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

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
        p.setCurrentSystem(this);
    }
    @Override
    public void reset(){
        super.reset();
        initialPackets.clear();
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
