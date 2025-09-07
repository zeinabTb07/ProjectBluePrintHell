package model.constants.levels;


import model.Level;
import model.enums.PortType;
import model.enums.SystemType;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;
import model.objects.systems.SystemFactory;
import model.objects.systems.addon.InputPort;
import model.objects.systems.addon.OutputPort;

public class Level5 extends Level {
    public Level5(){
        super();
        setNumber(4);
        setMessage(" The Last Step");
        setWireLength(6000);
        setTime(1);
        NetworkSystem ns1 =  SystemFactory.createSystem(SystemType.ANTI_TROJAN , 750  , 180);
        ns1.addInputPort(new InputPort(ns1 , PortType.TRIANGLE));
        ns1.addInputPort(new InputPort(ns1 , PortType.SQUARE));
        ns1.addInputPort(new InputPort(ns1 , PortType.SQUARE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.BITE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.TRIANGLE));

        addSystem(ns1);
    }
}