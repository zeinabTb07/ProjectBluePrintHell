package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.enums.GameRecords;
import model.enums.PacketType;
import model.objects.packets.ColossusPacket;
import model.objects.packets.MassagerPacket;
import model.objects.packets.Packet;

import java.awt.*;
import java.io.Serializable;

public class DistributeSystem extends NetworkSystem implements Serializable {
    public DistributeSystem(Point point) {
        super(point);
    }
    @Override
    public void receivePacket(Packet p){
        if(p instanceof ColossusPacket){
            GameRecords.ColossusPackets record = new GameRecords.ColossusPackets(p.getId() , p.getType());
            for(int i = 0 ; i < p.getSize(); i++){
                MassagerPacket bit = new MassagerPacket(this , PacketType.BITE);
                storage.add(bit);
                bit.setParentColossusId(record);
                EventBus.publish(new GameEvents.SwapPacketEvent(p , bit));
            }
        }
        EventBus.publish(new GameEvents.CoinGeneratedEvent(p.getSize()));
    }
}
