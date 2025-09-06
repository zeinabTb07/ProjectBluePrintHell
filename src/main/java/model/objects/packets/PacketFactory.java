package model.objects.packets;

import model.enums.PacketType;
import model.objects.systems.NetworkSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketFactory {
    private static final Logger logger = LoggerFactory.getLogger(PacketFactory.class);
    public static Packet createPacket(NetworkSystem system, PacketType packetType) {
        switch (packetType) {
            case TITAN:
            case RANGAROK:
                return new ColossusPacket(system, packetType);
            case SQUARE:
            case TRIANGLE:
            case BITE:
                return new MessagerPacket(system, packetType);
            case PHANTOM:
            case SPIRIT:
                return new PrivatePacket(system, packetType);
            default:
                logger.error("Invalid packet type");
                throw new IllegalArgumentException("Incorrect packet type");

        }
    }

    public static ProtectedPacket createProtectedPacket(Packet basePacket) {
        return new ProtectedPacket(basePacket);
    }
}