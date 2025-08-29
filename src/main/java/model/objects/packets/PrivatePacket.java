package model.objects.packets;

import model.constants.Constants;
import model.constants.PacketRecord;
import model.constants.PacketSpeedRules;
import model.enums.PacketType;
import model.objects.other.Connection;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;

import java.io.Serializable;
import java.util.Random;

public class PrivatePacket extends Packet implements Serializable {
    public PrivatePacket(RooterSystem system, PacketType packetType) {
        super(system, packetType);
    }

    @Override
    public void moveNormal(double deltaTime) {
        velocity += acceleration * deltaTime;
        NetworkSystem targetSystem = this.getCurrentConnection().getTarget().getParentSystem();
        if(!targetSystem.getStorage().isEmpty()&&type == PacketType.PHANTOM){
            distance += Constants.PACKET_SPEED/3 * deltaTime;
        } else distance += velocity * deltaTime;
        dirty = true;
    }
}
