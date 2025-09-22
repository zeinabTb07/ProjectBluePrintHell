package shared.api.service.mapper;


import shared.api.enums.PacketType;
import shared.api.enums.PortType;

import java.util.HashMap;
import java.util.Map;

public class SpeedRules {
    private static final Map<PacketType, Map<PortType, Records.PacketMovement>> rules = new HashMap<>();

    static {
       HashMap<PortType, Records.PacketMovement> squarePacket = new HashMap<>();
        for(PortType portType : PortType.values()){
            squarePacket.put(portType, new Records.PacketMovement(2 , 0));
        }
       squarePacket.put(PortType.RECTANGLE , new Records.PacketMovement(4 , 0));
       rules.put(PacketType.RECTANGLE , squarePacket);

        HashMap<PortType, Records.PacketMovement> trianglePacket = new HashMap<>();
        for(PortType portType : PortType.values()){
            trianglePacket.put(portType , new Records.PacketMovement(2 , 2));
        }
        trianglePacket.put(PortType.TRIANGLE , new Records.PacketMovement(3 , 0));
        rules.put(PacketType.TRIANGLE , trianglePacket);

        HashMap<PortType, Records.PacketMovement> bitePackets = new HashMap<>();
        for(PortType portType : PortType.values()){
            bitePackets.put(portType , new Records.PacketMovement(4 , -1));
        }
        bitePackets.put(PortType.BIT_PACKET , new Records.PacketMovement(1 , 1));
        rules.put(PacketType.BIT_PACKET , trianglePacket);


        HashMap<PortType, Records.PacketMovement> phantomPacket = new HashMap<>();
        for(PortType portType : PortType.values()){
            phantomPacket.put(portType , new Records.PacketMovement(4 , 0));
        }
        rules.put(PacketType.CLASSIFIED1 , phantomPacket);

        HashMap<PortType, Records.PacketMovement> spiritPacket = new HashMap<>();
        for(PortType portType : PortType.values()){
            spiritPacket.put(portType , new Records.PacketMovement(4 , 0));
        }
        rules.put(PacketType.CLASSIFIED2 , spiritPacket);

        HashMap<PortType, Records.PacketMovement> titanPacket = new HashMap<>();
        for(PortType portType : PortType.values()){
            titanPacket.put(portType , new Records.PacketMovement(1 , 0));
        }
        rules.put(PacketType.BIG_PACKET1 , titanPacket);

        HashMap<PortType, Records.PacketMovement> rangarok = new HashMap<>();
        for(PortType portType : PortType.values()){
            rangarok.put(portType , new Records.PacketMovement(1 , 0));
        }
        rules.put(PacketType.BIG_PACKET2 , rangarok);
    }

    public static Records.PacketMovement getProperties(PacketType packetType, PortType portType) {
        return rules.get(packetType).get(portType);
    }
}
