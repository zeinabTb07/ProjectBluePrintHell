package model.constants;

import model.Level;
import model.enums.PortType;
import model.objects.packets.ColossusPacket;
import model.objects.packets.MassagerPacket;
import model.enums.PacketType;
import model.objects.packets.PrivatePacket;
import model.objects.packets.ProtectedPacket;
import model.objects.systems.*;
import model.objects.systems.addon.InputPort;
import model.objects.systems.addon.OutputPort;

import java.awt.*;
import java.util.ArrayList;

public class Level1 extends Level {

    public Level1() {
        super();
        setWireLength(6000);
        setTime(20);
        setNumber(0);
        DistributeSystem ns3 = new DistributeSystem(new Point(300, 260));
        ns3.addInputPort(new InputPort(ns3, PortType.SQUARE));
        ns3.addInputPort(new InputPort(ns3, PortType.SQUARE));
        ns3.addOutputPort(new OutputPort(ns3, PortType.SQUARE));
        ns3.addOutputPort(new OutputPort(ns3, PortType.BITE));
        ns3.addOutputPort(new OutputPort(ns3, PortType.SQUARE));
        addSystem(ns3);


        MergeSystem ns4 = new MergeSystem(new Point(600, 270));
        ns4.addInputPort(new InputPort(ns4, PortType.TRIANGLE));
        ns4.addInputPort(new InputPort(ns4, PortType.BITE));
        ns4.addOutputPort(new OutputPort(ns4, PortType.TRIANGLE));
        ns4.addInputPort(new InputPort(ns4, PortType.SQUARE));
        ns4.addOutputPort(new OutputPort(ns4, PortType.SQUARE));
        addSystem(ns4);

//
//        SpySystem ns5 = new SpySystem(new Point(650, 300));
//        ns5.addInputPort(new InputPort(ns5, PortType.SQUARE));
//        ns5.addOutputPort(new OutputPort(ns5, PortType.SQUARE));
//        ns5.addInputPort(new InputPort(ns5, PortType.TRIANGLE));
//        ns5.addOutputPort(new OutputPort(ns5, PortType.TRIANGLE));
//        addSystem(ns5);


//        ChaosSystem ns6 = new ChaosSystem(new Point(450, 200));
//        ns6.addInputPort(new InputPort(ns6, PortType.SQUARE));
//        ns6.addOutputPort(new OutputPort(ns6, PortType.SQUARE));
//        ns6.addInputPort(new InputPort(ns6, PortType.TRIANGLE));
//        ns6.addOutputPort(new OutputPort(ns6, PortType.TRIANGLE));
//        addSystem(ns6);


        RooterSystem rs1 = new RooterSystem(new Point(100, 150));
        rs1.addOutputPort(new OutputPort(rs1, PortType.SQUARE));
        rs1.addOutputPort(new OutputPort(rs1, PortType.SQUARE));
      //  rs1.addPacket(new PrivatePacket(rs1, PacketType.PHANTOM));
        rs1.addPacket(new ColossusPacket(rs1, PacketType.TITAN));
//        rs1.addPacket(new MassagerPacket(rs1, PacketType.SQUARE));
//        rs1.addPacket(new MassagerPacket(rs1, PacketType.BITE));
        addSystem(rs1);

        RooterSystem rs2 = new RooterSystem(new Point(780, 450));
        rs2.addInputPort(new InputPort(rs2, PortType.TRIANGLE));
        rs2.addInputPort(new InputPort(rs2, PortType.SQUARE));
        addSystem(rs2);
    }
}

