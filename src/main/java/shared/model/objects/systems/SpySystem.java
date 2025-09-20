package shared.model.objects.systems;

import shared.model.objects.packets.ColossusPacket;
import shared.model.objects.packets.Packet;
import shared.model.objects.packets.PrivatePacket;

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
        if(p instanceof ColossusPacket){
            p.getCurrentConnection().decreaseStrength();
        }
        if(p instanceof PrivatePacket){
            return;
        }
        SpySystem spySystem = spies.get(random.nextInt(0 , spies.size()));
        if(spySystem.isActive()){
            spySystem.storage.add(p);
            p.setCurrentSystem(spySystem);
        } else {
            this.storage.add(p);
            p.setCurrentSystem(this);
        }
    }

}
