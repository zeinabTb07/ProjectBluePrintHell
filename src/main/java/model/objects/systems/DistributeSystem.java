package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.enums.PacketType;
import model.objects.packets.ColossusPacket;
import model.objects.packets.MessagerPacket;
import model.objects.packets.Packet;
import utils.PacketRecord;

import java.awt.*;
import java.io.Serializable;

public class DistributeSystem extends NetworkSystem implements Serializable {
    public DistributeSystem(Point point) {
        super(point);
    }
    @Override
    public void receivePacket(Packet p){
        if(p instanceof ColossusPacket){
            PacketRecord.ColossusPackets record = new PacketRecord.ColossusPackets(p.getId() , p.getType());
            for(int i = 0 ; i < p.getSize(); i++){
                MessagerPacket bit = new MessagerPacket(this , PacketType.BITE);
                storage.add(bit);
                bit.setParentColossusId(record);
                EventBus.publish(new GameEvents.SwapPacketEvent(p , bit));
            }
            p.getCurrentConnection().decreaseStrength();

        } else {

            storage.add(p);
            p.setCurrentSystem(this);
        }
        EventBus.publish(new GameEvents.CoinGeneratedEvent(p.getSize()));
    }
}
