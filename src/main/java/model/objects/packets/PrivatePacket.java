package model.objects.packets;

import model.constants.Constants;
import model.enums.PacketType;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;

import java.io.Serializable;

public class PrivatePacket extends Packet implements Serializable {
    public PrivatePacket(NetworkSystem system, PacketType packetType) {
        super(system, packetType);
    }

    @Override
    public void moveNormal(double deltaTime) {
        super.moveNormal(deltaTime);
        NetworkSystem targetSystem = this.getCurrentConnection().getTarget().getParentSystem();
        if(!targetSystem.getStorage().isEmpty() && type == PacketType.PHANTOM){
            velocity = Constants.PACKET_SPEED/3;
        }
    }
    @Override
    public Packet clon() {
        return new PrivatePacket(currentSystem , type);
    }
}
