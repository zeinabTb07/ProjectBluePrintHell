package model.constants;


import model.Level;
import model.enums.PortType;
import model.objects.packets.MassagerPacket;
import model.enums.MassagerPacketType;
import model.objects.systems.*;

import java.awt.*;

public class Level2 extends Level {

    public Level2() {
        super();

        setWireLength(15000);

        NetworkSystem ns1 = new NetworkSystem(new Point(370, 380));
        ns1.addInputPort(new InputPort(ns1, PortType.TRIANGLE));
        ns1.addOutputPort(new OutputPort(ns1, PortType.SQUARE));
        ns1.addOutputPort(new OutputPort(ns1, PortType.TRIANGLE));
        addSystem(ns1);


        NetworkSystem ns2 = new NetworkSystem(new Point(320, 140));
        ns2.addInputPort(new InputPort(ns2, PortType.SQUARE));
        ns2.addOutputPort(new OutputPort(ns2, PortType.SQUARE));
        ns2.addInputPort(new InputPort(ns2, PortType.TRIANGLE));
        ns2.addOutputPort(new OutputPort(ns2, PortType.TRIANGLE));
        addSystem(ns2);


        NetworkSystem ns3 = new NetworkSystem(new Point(200, 300));
        ns3.addInputPort(new InputPort(ns3, PortType.SQUARE));
        ns3.addOutputPort(new OutputPort(ns3, PortType.TRIANGLE));
        addSystem(ns3);

        NetworkSystem ns4 = new NetworkSystem(new Point(500, 220));
        ns4.addInputPort(new InputPort(ns4, PortType.TRIANGLE));
        ns4.addOutputPort(new OutputPort(ns4, PortType.TRIANGLE));
        ns4.addInputPort(new InputPort(ns4, PortType.SQUARE));
        ns4.addInputPort(new InputPort(ns4, PortType.SQUARE));
        ns4.addOutputPort(new OutputPort(ns4, PortType.SQUARE));
        addSystem(ns4);

        NetworkSystem ns5 = new NetworkSystem(new Point(850, 160));
        ns5.addOutputPort(new OutputPort(ns5, PortType.TRIANGLE));
        ns5.addInputPort(new InputPort(ns5, PortType.TRIANGLE));
        addSystem(ns5);


        NetworkSystem ns6 = new NetworkSystem(new Point(750, 480));
        ns6.addInputPort(new InputPort(ns6, PortType.SQUARE));
        ns6.addInputPort(new InputPort(ns6, PortType.TRIANGLE));
        ns6.addOutputPort(new OutputPort(ns6, PortType.TRIANGLE));
        addSystem(ns6);

        RooterSystem rs1 = new RooterSystem(new Point(650, 300));
        rs1.addOutputPort(new OutputPort(rs1, PortType.SQUARE));
        rs1.addInputPort(new InputPort(rs1, PortType.SQUARE));
        rs1.addInputPort(new InputPort(rs1, PortType.TRIANGLE));
        rs1.addPacket(new MassagerPacket(rs1, MassagerPacketType.SQUARE));
        addSystem(rs1);

        RooterSystem rs2 = new RooterSystem(new Point(50, 150));
        rs2.addOutputPort(new OutputPort(rs2, PortType.TRIANGLE));
        rs2.addOutputPort(new OutputPort(rs2, PortType.SQUARE));
        rs2.addPacket(new MassagerPacket(rs2, MassagerPacketType.TRIANGLE));
        rs2.addPacket(new MassagerPacket(rs2, MassagerPacketType.SQUARE));
        rs2.addPacket(new MassagerPacket(rs2, MassagerPacketType.SQUARE));
        addSystem(rs2);
    }
}
