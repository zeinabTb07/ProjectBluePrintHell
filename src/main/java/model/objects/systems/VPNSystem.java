package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.enums.PacketType;
import model.objects.packets.Packet;
import model.objects.packets.PrivatePacket;
import model.objects.packets.ProtectedPacket;

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
        Packet packet ;
        if(p instanceof ProtectedPacket){
             packet = new PrivatePacket(this , PacketType.SPIRIT);
        } else {
             packet = new ProtectedPacket(p);
        }

        EventBus.publish(new GameEvents.SwapPacketEvent(p , packet));
        storage.add(packet);
        packet.setCurrentSystem(this);
        EventBus.publish(new GameEvents.CoinGeneratedEvent(p.getSize()));
    }
    @Override
    public void update() {
        super.update();
        if(!isActive()&&!packets.isEmpty()){
            for(ProtectedPacket p : packets){
                Packet base = p.getBasePacket();
                base.setCurrentSystem(p.getCurrentSystem());
                base.setCurrentConnection(p.getCurrentConnection());
                base.setDistancePassedOnConnection(p.getDistancePassedOnConnection());
                EventBus.publish(new GameEvents.SwapPacketEvent(p , base));
            }
            packets = new ArrayList<>();
        }
    }
}
