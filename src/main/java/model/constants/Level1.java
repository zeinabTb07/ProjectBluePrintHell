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

public class Level1 extends Level {

    public Level1() {
        super();
        setWireLength(6000);
        setTime(35);
        setNumber(0);

//        DistributeSystem ns3 = new DistributeSystem(new Point(
//                (int)(300 * Constants.SCALE),
//                (int)(260 * Constants.SCALE)
//        ));
//        ns3.addInputPort(new InputPort(ns3, PortType.SQUARE));
//        ns3.addInputPort(new InputPort(ns3, PortType.SQUARE));
//        ns3.addOutputPort(new OutputPort(ns3, PortType.SQUARE));
//        ns3.addOutputPort(new OutputPort(ns3, PortType.BITE));
//        ns3.addOutputPort(new OutputPort(ns3, PortType.SQUARE));
//        addSystem(ns3);


        MergeSystem ns4 = new MergeSystem(new Point(
                (int)(550 * Constants.SCALE),
                (int)(400 * Constants.SCALE)
        ));
        ns4.addInputPort(new InputPort(ns4, PortType.TRIANGLE));
  //      ns4.addInputPort(new InputPort(ns4, PortType.BITE));
        ns4.addOutputPort(new OutputPort(ns4, PortType.TRIANGLE));
        ns4.addInputPort(new InputPort(ns4, PortType.SQUARE));
        ns4.addOutputPort(new OutputPort(ns4, PortType.SQUARE));
        addSystem(ns4);


        DistributeSystem ns5 = new DistributeSystem(new Point(
                (int)(650 * Constants.SCALE),
                (int)(300 * Constants.SCALE)
        ));
        ns5.addInputPort(new InputPort(ns5, PortType.SQUARE));
        ns5.addOutputPort(new OutputPort(ns5, PortType.SQUARE));
        ns5.addInputPort(new InputPort(ns5, PortType.TRIANGLE));
        ns5.addOutputPort(new OutputPort(ns5, PortType.TRIANGLE));
        addSystem(ns5);


//        ChaosSystem ns6 = new ChaosSystem(new Point(
//                (int)(450 * Constants.SCALE),
//                (int)(200 * Constants.SCALE)
//        ));
//        ns6.addInputPort(new InputPort(ns6, PortType.SQUARE));
//        ns6.addOutputPort(new OutputPort(ns6, PortType.SQUARE));
//        ns6.addInputPort(new InputPort(ns6, PortType.TRIANGLE));
//        ns6.addOutputPort(new OutputPort(ns6, PortType.TRIANGLE));
//        addSystem(ns6);
//

        RooterSystem rs1 = new RooterSystem(new Point(
                (int)(100 * Constants.SCALE),
                (int)(150 * Constants.SCALE)
        ));
        rs1.addOutputPort(new OutputPort(rs1, PortType.SQUARE));
        rs1.addOutputPort(new OutputPort(rs1, PortType.SQUARE));
        rs1.addPacket(new ColossusPacket(rs1, PacketType.RANGAROK));
        rs1.addPacket(new ColossusPacket(rs1, PacketType.TITAN));
//        rs1.addPacket(new MassagerPacket(rs1, PacketType.SQUARE));
//        rs1.addPacket(new MassagerPacket(rs1, PacketType.BITE));
        addSystem(rs1);


        RooterSystem rs2 = new RooterSystem(new Point(
                (int)(780 * Constants.SCALE),
                (int)(450 * Constants.SCALE)
        ));
        rs2.addInputPort(new InputPort(rs2, PortType.TRIANGLE));
        rs2.addInputPort(new InputPort(rs2, PortType.SQUARE));
        addSystem(rs2);
    }
}
