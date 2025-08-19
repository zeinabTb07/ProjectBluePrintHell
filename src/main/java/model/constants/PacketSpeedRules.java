package model.constants;

import model.enums.PacketType;
import model.enums.PortType;

import java.util.HashMap;
import java.util.Map;

public class PacketSpeedRules {
    private static final Map<PacketType, Map<PortType, PacketRecord.PacketMovement>> rules = new HashMap<>();

    static {
       HashMap<PortType, PacketRecord.PacketMovement> squarePacket = new HashMap<>();
        for(PortType portType : PortType.values()){
            squarePacket.put(portType, new PacketRecord.PacketMovement(2 , 0));
        }
       squarePacket.put(PortType.SQUARE , new PacketRecord.PacketMovement(4 , 0));
       rules.put(PacketType.SQUARE , squarePacket);

        HashMap<PortType, PacketRecord.PacketMovement> trianglePacket = new HashMap<>();
        for(PortType portType : PortType.values()){
            trianglePacket.put(portType , new PacketRecord.PacketMovement(2 , 2));
        }
        trianglePacket.put(PortType.TRIANGLE , new PacketRecord.PacketMovement(3 , 0));
        rules.put(PacketType.TRIANGLE , trianglePacket);

        HashMap<PortType, PacketRecord.PacketMovement> bitePackets = new HashMap<>();
        for(PortType portType : PortType.values()){
            bitePackets.put(portType , new PacketRecord.PacketMovement(4 , -1));
        }
        bitePackets.put(PortType.BITE , new PacketRecord.PacketMovement(1 , 1));
        rules.put(PacketType.BITE , trianglePacket);

    }

    public static PacketRecord.PacketMovement getProperties(PacketType packetType, PortType portType) {
        return rules.get(packetType).get(portType);
    }
}
