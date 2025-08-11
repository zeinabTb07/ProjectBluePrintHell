package model.constants;

import model.PacketRecord;
import model.enums.MassagerPacketType;
import model.enums.PortType;

import java.util.HashMap;
import java.util.Map;

public class PacketSpeedRules {
    private static final Map<MassagerPacketType, Map<PortType, PacketRecord.PacketMovement>> rules = new HashMap<>();

    static {
       HashMap<PortType, PacketRecord.PacketMovement> squarePacket = new HashMap<>();
       squarePacket.put(PortType.SQUARE , new PacketRecord.PacketMovement(2 , 0));
       squarePacket.put(PortType.TRIANGLE , new PacketRecord.PacketMovement(1 , 0));
       rules.put(MassagerPacketType.SQUARE , squarePacket);

        HashMap<PortType, PacketRecord.PacketMovement> trianglePacket = new HashMap<>();
        trianglePacket.put(PortType.SQUARE , new PacketRecord.PacketMovement(1 , 1));
        trianglePacket.put(PortType.TRIANGLE , new PacketRecord.PacketMovement(2 , 0));
        rules.put(MassagerPacketType.TRIANGLE , trianglePacket);

    }

    public static PacketRecord.PacketMovement getProperties(MassagerPacketType packetType, PortType portType) {
        return rules.get(packetType).get(portType);
    }
}
