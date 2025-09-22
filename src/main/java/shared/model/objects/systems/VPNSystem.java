package shared.model.objects.systems;

import shared.api.enums.PacketType;
import shared.events.GameEvents;
import shared.model.objects.packets.ColossusPacket;
import shared.model.objects.packets.Packet;
import shared.model.objects.packets.PrivatePacket;
import shared.model.objects.packets.ProtectedPacket;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VPNSystem extends NetworkSystem implements Serializable {
    private List<ProtectedPacket> packets ;
    public VPNSystem(Point point) {
        super(point);
        packets = new ArrayList<>();
    }
    @Override
    public void receivePacket(Packet p){
        if(p instanceof ColossusPacket){
            p.getCurrentConnection().decreaseStrength();
        }
        Packet packet ;
        if(p instanceof ProtectedPacket){
             packet = new PrivatePacket(this , PacketType.CLASSIFIED1);
        } else {
            packet = new ProtectedPacket(p);
            packets.add((ProtectedPacket) packet);
        }
        publisher.publish(new GameEvents.SwapPacketEvent(p , packet));
        storage.add(packet);
        packet.setCurrentSystem(this);
        System.out.println("straoge:" +storage.size());

    }
    @Override
    public void update() {
        super.update();
        if(!isActive()&&!packets.isEmpty()){
            for(ProtectedPacket p : packets){
                Packet base = p.getBasePacket();
                NetworkSystem sys = p.getCurrentSystem();
                sys.getStorage().add(base);
                base.setCurrentSystem(sys);
                if(p.getCurrentConnection()!=null){
                    base.setCurrentConnection(p.getCurrentConnection());
                    base.setDistancePassedOnConnection(p.getDistancePassedOnConnection());
                    base.setVelocity(20);
                }
                publisher.publish(new GameEvents.SwapPacketEvent(p , base));
            }
            packets.clear();
        }
    }
    @Override
    public void reset(){
        super.reset();
        packets = new ArrayList<>();
    }
}
