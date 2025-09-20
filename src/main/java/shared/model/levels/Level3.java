package shared.model.levels;

import shared.model.objects.systems.NetworkSystem;
import shared.model.objects.systems.SystemFactory;
import shared.model.objects.systems.addon.InputPort;
import shared.model.objects.systems.addon.OutputPort;
import shared.api.enums.PortType;
import shared.api.enums.SystemType;

public class Level3 extends Level {
    public Level3(){
     super();
        setNumber(2);
        setMessage(" Chaos Fight VPN");
        setTime(25);


        NetworkSystem ns1 =  SystemFactory.createSystem(SystemType.DDOS , 100  , 550);
        ns1.addInputPort(new InputPort(ns1 , PortType.RECTANGLE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.BIT_PACKET));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.TRIANGLE));

        NetworkSystem ns2 =  SystemFactory.createSystem(SystemType.VPN , 430 , 320);
        ns2.addInputPort(new InputPort(ns2 , PortType.TRIANGLE));
        ns2.addInputPort(new InputPort(ns2 , PortType.BIT_PACKET));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.RECTANGLE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.TRIANGLE));
        ns2.addOutputPort(new OutputPort(ns2 , PortType.BIT_PACKET));


        addSystem(ns1);
        addSystem(ns2);
    }
}