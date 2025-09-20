package shared.model.objects.packets;

import shared.model.objects.systems.NetworkSystem;
import shared.api.enums.PacketType;

public class PacketFactory {
    public static Packet createPacket(NetworkSystem system, PacketType packetType) {
        switch (packetType) {
            case BIG_PACKET1:
            case BIG_PACKET2:
                return new ColossusPacket(system, packetType);
            case RECTANGLE:
            case TRIANGLE:
            case BIT_PACKET:
                return new MessagerPacket(system, packetType);
            case CLASSIFIED1:
            case CLASSIFIED2:
                return new PrivatePacket(system, packetType);
            default:
                throw new IllegalArgumentException("Incorrect packet type");
        }
    }

    public static ProtectedPacket createProtectedPacket(Packet basePacket) {
        return new ProtectedPacket(basePacket);
    }
}