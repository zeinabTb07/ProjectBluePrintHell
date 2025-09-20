package shared.model.levels;


import shared.model.objects.packets.PacketFactory;
import shared.model.objects.systems.NetworkSystem;
import shared.model.objects.systems.RooterSystem;
import shared.model.objects.systems.SystemFactory;
import shared.model.objects.systems.addon.InputPort;
import shared.model.objects.systems.addon.OutputPort;
import shared.api.enums.PacketType;
import shared.api.enums.PortType;
import shared.api.enums.SystemType;

public class Level4 extends Level {
    public Level4(){
        super();
        setNumber(3);
        setMessage(" Can Your Network Handel Colossus Attack?");
        setTime(30);

        RooterSystem rooter1 = (RooterSystem) SystemFactory.createSystem(SystemType.ROOTER , 40 , 300);
        rooter1.addOutputPort(new OutputPort(rooter1 , PortType.RECTANGLE));
        rooter1.addOutputPort(new OutputPort(rooter1 , PortType.BIT_PACKET));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.BIG_PACKET1));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.BIT_PACKET));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.BIG_PACKET2));


        NetworkSystem ns1 =  SystemFactory.createSystem(SystemType.DISTRIBUTE , 300  , 460);
        ns1.addInputPort(new InputPort(ns1 , PortType.TRIANGLE));
        ns1.addInputPort(new InputPort(ns1 , PortType.BIT_PACKET));
        ns1.addInputPort(new InputPort(ns1 , PortType.RECTANGLE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.BIT_PACKET));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.TRIANGLE));

        NetworkSystem ns2 =  SystemFactory.createSystem(SystemType.MERGE , 700 , 500);
        ns2.addInputPort(new InputPort(ns2 , PortType.TRIANGLE));
        ns2.addInputPort(new InputPort(ns2 , PortType.BIT_PACKET));
        ns2.addInputPort(new InputPort(ns2 , PortType.TRIANGLE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.RECTANGLE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.TRIANGLE));

        addSystem(rooter1);
        addSystem(ns1);
        addSystem(ns2);

    }
}