package model.constants.levels;

import model.Level;
import model.enums.PacketType;
import model.enums.PortType;
import model.enums.SystemType;
import model.objects.packets.PacketFactory;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;
import model.objects.systems.SystemFactory;
import model.objects.systems.addon.InputPort;
import model.objects.systems.addon.OutputPort;

public class TestLevel extends Level {
    public TestLevel(){
        setTime(45);

        RooterSystem rooter1 = (RooterSystem) SystemFactory.createSystem(SystemType.ROOTER , 40 , 300);
        rooter1.addOutputPort(new OutputPort(rooter1 , PortType.SQUARE));
        rooter1.addOutputPort(new OutputPort(rooter1 , PortType.BITE));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.TITAN));
      //  rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.BITE));
        rooter1.addPacket(PacketFactory.createPacket(rooter1 , PacketType.RANGAROK));


//        RooterSystem rooter2 = (RooterSystem) SystemFactory.createSystem(SystemType.ROOTER , 80 , 500);
//        rooter2.addOutputPort(new OutputPort(rooter2 , PortType.BITE));
//        rooter2.addOutputPort(new OutputPort(rooter2 , PortType.TRIANGLE));
//        rooter2.addPacket(PacketFactory.createPacket(rooter2 , PacketType.SQUARE));
//        rooter2.addPacket(PacketFactory.createPacket(rooter2 , PacketType.TRIANGLE));
//        rooter2.addPacket(PacketFactory.createPacket(rooter2 , PacketType.BITE));
//        rooter2.addPacket(PacketFactory.createPacket(rooter2 , PacketType.SQUARE));
//        rooter2.addPacket(PacketFactory.createPacket(rooter2 , PacketType.TRIANGLE));
//        rooter2.addPacket(PacketFactory.createPacket(rooter2 , PacketType.SPIRIT));


        NetworkSystem ns1 =  SystemFactory.createSystem(SystemType.LINK , 300  , 460);
        ns1.addInputPort(new InputPort(ns1 , PortType.TRIANGLE));
        ns1.addInputPort(new InputPort(ns1 , PortType.BITE));
        ns1.addInputPort(new InputPort(ns1 , PortType.SQUARE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.BITE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.TRIANGLE));

        NetworkSystem ns2 =  SystemFactory.createSystem(SystemType.LINK , 700 , 500);
        ns2.addInputPort(new InputPort(ns2 , PortType.TRIANGLE));
        ns2.addInputPort(new InputPort(ns2 , PortType.BITE));
        ns2.addInputPort(new InputPort(ns2 , PortType.TRIANGLE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.SQUARE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.TRIANGLE));

        addSystem(rooter1);
        //addSystem(rooter2);
        addSystem(ns1);
        addSystem(ns2);
    }

}
