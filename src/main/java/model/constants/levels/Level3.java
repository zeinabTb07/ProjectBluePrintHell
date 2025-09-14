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

public class Level3 extends Level {
    public Level3(){
     super();
        setNumber(2);
        setMessage(" Chaos Fight VPN");
        setTime(1);


        NetworkSystem ns1 =  SystemFactory.createSystem(SystemType.CHAOS , 100  , 550);
        ns1.addInputPort(new InputPort(ns1 , PortType.SQUARE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.BITE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.TRIANGLE));

        NetworkSystem ns2 =  SystemFactory.createSystem(SystemType.VPN , 430 , 320);
        ns2.addInputPort(new InputPort(ns2 , PortType.TRIANGLE));
        ns2.addInputPort(new InputPort(ns2 , PortType.BITE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.SQUARE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.TRIANGLE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.BITE));


        addSystem(ns1);
        addSystem(ns2);
    }
}