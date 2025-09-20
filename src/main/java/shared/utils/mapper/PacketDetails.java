package shared.utils.mapper;


import shared.api.enums.PacketType;

import java.util.HashMap;
import java.util.Map;

public class PacketDetails {
    private static final Map<PacketType, PacketRecord.PacketProperties> rules = new HashMap<>();

    static {
        rules.put(PacketType.RECTANGLE , new PacketRecord.PacketProperties(2 , 2));
        rules.put(PacketType.TRIANGLE , new PacketRecord.PacketProperties(3 , 3));
        rules.put(PacketType.BIT_PACKET , new PacketRecord.PacketProperties(1 , 1));
        rules.put(PacketType.PROTECTED , new PacketRecord.PacketProperties(5 , 4));
        rules.put(PacketType.CLASSIFIED1 , new PacketRecord.PacketProperties(3 , 4));
        rules.put(PacketType.CLASSIFIED2 , new PacketRecord.PacketProperties(4 , 6));
        rules.put(PacketType.BIG_PACKET1 , new PacketRecord.PacketProperties(8 , 8));
        rules.put(PacketType.BIG_PACKET2 , new PacketRecord.PacketProperties(10 , 10));
    }

    public static PacketRecord.PacketProperties getProperties(PacketType packetType) {
        return rules.get(packetType);
    }
}