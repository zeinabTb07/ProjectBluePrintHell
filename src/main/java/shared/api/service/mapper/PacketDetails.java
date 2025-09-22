package shared.api.service.mapper;


import shared.api.enums.PacketType;

import java.util.HashMap;
import java.util.Map;

public class PacketDetails {
    private static final Map<PacketType, Records.PacketProperties> rules = new HashMap<>();

    static {
        rules.put(PacketType.RECTANGLE , new Records.PacketProperties(2 , 2));
        rules.put(PacketType.TRIANGLE , new Records.PacketProperties(3 , 3));
        rules.put(PacketType.BIT_PACKET , new Records.PacketProperties(1 , 1));
        rules.put(PacketType.PROTECTED , new Records.PacketProperties(5 , 4));
        rules.put(PacketType.CLASSIFIED1 , new Records.PacketProperties(3 , 4));
        rules.put(PacketType.CLASSIFIED2 , new Records.PacketProperties(4 , 6));
        rules.put(PacketType.BIG_PACKET1 , new Records.PacketProperties(8 , 8));
        rules.put(PacketType.BIG_PACKET2 , new Records.PacketProperties(10 , 10));
    }

    public static Records.PacketProperties getProperties(PacketType packetType) {
        return rules.get(packetType);
    }
}