package model.constants;

import model.Level;
import model.enums.PortType;
import model.objects.packets.MassagerPacket;
import model.enums.MassagerPacketType;
import model.objects.systems.*;

import java.awt.*;

public class Level1 extends Level {

    public Level1() {
        super();
        setWireLength(5000);
        setTime(30);
        setNumber(0);
        NetworkSystem ns3 = new NetworkSystem(new Point(300, 260));
        ns3.addInputPort(new InputPort(ns3, PortType.SQUARE));
        ns3.addInputPort(new InputPort(ns3, PortType.SQUARE));
        ns3.addOutputPort(new OutputPort(ns3, PortType.SQUARE));
        ns3.addOutputPort(new OutputPort(ns3, PortType.SQUARE));
        addSystem(ns3);


        NetworkSystem ns4 = new NetworkSystem(new Point(500, 220));
        ns4.addInputPort(new InputPort(ns4, PortType.TRIANGLE));
        ns4.addOutputPort(new OutputPort(ns4, PortType.TRIANGLE));
        ns4.addInputPort(new InputPort(ns4, PortType.SQUARE));
        ns4.addOutputPort(new OutputPort(ns4, PortType.SQUARE));
        addSystem(ns4);

        NetworkSystem ns5 = new NetworkSystem(new Point(650, 300));
        ns5.addInputPort(new InputPort(ns5, PortType.SQUARE));
        ns5.addOutputPort(new OutputPort(ns5, PortType.SQUARE));
        ns5.addInputPort(new InputPort(ns5, PortType.TRIANGLE));
        ns5.addOutputPort(new OutputPort(ns5, PortType.TRIANGLE));
        addSystem(ns5);


        NetworkSystem ns6 = new NetworkSystem(new Point(450, 420));
        ns6.addInputPort(new InputPort(ns6, PortType.SQUARE));
        ns6.addOutputPort(new OutputPort(ns6, PortType.SQUARE));
        ns6.addInputPort(new InputPort(ns6, PortType.TRIANGLE));
        ns6.addOutputPort(new OutputPort(ns6, PortType.TRIANGLE));
        addSystem(ns6);


        RooterSystem rs1 = new RooterSystem(new Point(100, 150));
        rs1.addOutputPort(new OutputPort(rs1, PortType.SQUARE));
        rs1.addOutputPort(new OutputPort(rs1, PortType.SQUARE));
        rs1.addPacket(new MassagerPacket(rs1, MassagerPacketType.TRIANGLE));
        rs1.addPacket(new MassagerPacket(rs1, MassagerPacketType.SQUARE));
        rs1.addPacket(new MassagerPacket(rs1, MassagerPacketType.SQUARE));
        addSystem(rs1);

        RooterSystem rs2 = new RooterSystem(new Point(780, 450));
        rs2.addInputPort(new InputPort(rs2, PortType.TRIANGLE));
        rs2.addInputPort(new InputPort(rs2, PortType.SQUARE));
        addSystem(rs2);
    }
}

