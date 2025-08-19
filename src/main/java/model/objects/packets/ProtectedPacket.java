package model.objects.packets;

import model.enums.PacketType;
import model.objects.systems.RooterSystem;

public class ProtectedPacket extends Packet {
    public ProtectedPacket(RooterSystem system, PacketType packetType) {
        super(system, packetType);
    }
}
