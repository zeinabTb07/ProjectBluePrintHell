package shared.model.objects.systems;

import shared.events.GameEvents;
import shared.model.objects.packets.ColossusPacket;
import shared.model.objects.packets.MessagerPacket;
import shared.model.objects.packets.Packet;
import shared.api.enums.PacketType;
import shared.api.service.mapper.PacketDetails;
import shared.api.service.mapper.PacketRecord;

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
            for(int i = 0; i < PacketDetails.getProperties(p.getType()).size(); i++){
                MessagerPacket bit = new MessagerPacket(this , PacketType.BIT_PACKET);
                storage.add(bit);
                publisher.publish(new GameEvents.SwapPacketEvent(p , bit));
                bit.setParentColossusId(record);
            }
            p.getCurrentConnection().decreaseStrength();

        } else {

            storage.add(p);
            p.setCurrentSystem(this);
        }
    }
}
