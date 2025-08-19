package model.objects.packets;

import model.enums.PacketType;
import model.objects.systems.RooterSystem;

public class PrivatePacket extends Packet{
    public PrivatePacket(RooterSystem system, PacketType packetType) {
        super(system, packetType);
    }
}
