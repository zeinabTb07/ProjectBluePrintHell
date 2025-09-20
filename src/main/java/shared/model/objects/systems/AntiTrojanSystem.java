package shared.model.objects.systems;

import shared.model.objects.packets.Packet;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class AntiTrojanSystem extends NetworkSystem implements Serializable {
    public AntiTrojanSystem(Point point) {
        super(point);
    }

    @Override
    public void update(){
        super.update();
        Set<NetworkSystem> neighbors = getNeighbors();
        for(NetworkSystem sys : neighbors){
            ArrayList<Packet> packets = sys.getStorage();
            for(Packet packet:packets){
                if(packet.isTrojan()){
                    packet.setTrojan(false);
                    setActive(false);
                }
            }
        }
    }
}
