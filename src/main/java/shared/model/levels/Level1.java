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


public class Level1 extends Level {
    public Level1(){
        super();
        setNumber(0);
        setMessage(" Warming Up");
        setTime(25);

        RooterSystem rooter1 = (RooterSystem) SystemFactory.createSystem(SystemType.ROOTER , 50 , 150);
        rooter1.addOutputPort(new OutputPort(rooter1 , PortType.RECTANGLE));
        rooter1.addOutputPort(new OutputPort(rooter1 , PortType.BIT_PACKET));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.RECTANGLE));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.TRIANGLE));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.BIT_PACKET));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.RECTANGLE));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.TRIANGLE));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.CLASSIFIED1));

        RooterSystem rooter2 = (RooterSystem) SystemFactory.createSystem(SystemType.ROOTER , 880 , 500);
        rooter2.addInputPort(new InputPort(rooter2 , PortType.BIT_PACKET));
        rooter2.addInputPort(new InputPort(rooter2 , PortType.TRIANGLE));
        rooter2.addInputPort(new InputPort(rooter2 , PortType.TRIANGLE));


        NetworkSystem ns1 =  SystemFactory.createSystem(SystemType.DDOS , 280  , 110);
        ns1.addInputPort(new InputPort(ns1 , PortType.TRIANGLE));
        ns1.addInputPort(new InputPort(ns1 , PortType.RECTANGLE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.BIT_PACKET));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.RECTANGLE));

        NetworkSystem ns2 =  SystemFactory.createSystem(SystemType.NORMAL , 600 , 350);
        ns2.addInputPort(new InputPort(ns2 , PortType.TRIANGLE));
        ns2.addInputPort(new InputPort(ns2 , PortType.BIT_PACKET));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.RECTANGLE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.TRIANGLE));

        addSystem(rooter1);
        addSystem(rooter2);
        addSystem(ns1);
        addSystem(ns2);
    }
}
