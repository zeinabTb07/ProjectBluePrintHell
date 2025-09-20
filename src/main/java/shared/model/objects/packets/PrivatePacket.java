package shared.model.objects.packets;

import client.Constants;
import shared.model.objects.systems.NetworkSystem;
import shared.api.enums.PacketType;

import java.io.Serializable;

public class PrivatePacket extends Packet implements Serializable {
    public PrivatePacket(NetworkSystem system, PacketType packetType) {
        super(system, packetType);
    }

    @Override
    public void moveNormal(double deltaTime) {
        super.moveNormal(deltaTime);
        NetworkSystem targetSystem = this.getCurrentConnection().getTarget().getParentSystem();
        if(!targetSystem.getStorage().isEmpty() && type == PacketType.CLASSIFIED1){
            velocity = Constants.PACKET_SPEED/3;
        }
    }
    @Override
    public Packet clon() {
        return new PrivatePacket(currentSystem , type);
    }
}
