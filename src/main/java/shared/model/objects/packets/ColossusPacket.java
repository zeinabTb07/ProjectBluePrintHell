package shared.model.objects.packets;

import shared.model.objects.systems.NetworkSystem;
import shared.api.enums.PacketType;

import java.io.Serializable;

public class ColossusPacket extends Packet implements Serializable {

    public ColossusPacket(NetworkSystem system, PacketType packetType) {
        super(system, packetType);
    }

    @Override
    public void moveNormal(double deltaTime) {
       super.moveNormal(deltaTime);
       if(type==PacketType.CLASSIFIED2){
           centerOfMass.setLocation(centerOfMass.getX() , centerOfMass.getY()+0.02);
       }
    }

    @Override
    public Packet clon() {
       return new ColossusPacket(currentSystem , type);
    }
}
