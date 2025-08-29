package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.objects.packets.Packet;
import model.objects.packets.PrivatePacket;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpySystem extends NetworkSystem implements Serializable {
    private List<SpySystem> spies;
    private Random random ;
    public SpySystem(Point point) {
        super(point);
        random = new Random();
        spies = new ArrayList<>();
    }

    public List<SpySystem> getSpies() {
        return spies;
    }

    public void setSpies(List<SpySystem> spies) {
        this.spies = spies;
    }

    @Override
    public void receivePacket(Packet p){
        if(p instanceof PrivatePacket){
            EventBus.publish(new GameEvents.PacketLostEvent(p));
            return;
        }
      //  SpySystem spySystem = spies.get(random.nextInt(0 , spies.size()));
        this.storage.add(p);
        p.setCurrentSystem(this);
        EventBus.publish(new GameEvents.CoinGeneratedEvent(p.getSize()));
    }

}
