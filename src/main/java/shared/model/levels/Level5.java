package shared.model.levels;

import shared.model.objects.systems.NetworkSystem;
import shared.model.objects.systems.SystemFactory;
import shared.model.objects.systems.addon.InputPort;
import shared.model.objects.systems.addon.OutputPort;
import shared.api.enums.PortType;
import shared.api.enums.SystemType;

public class Level5 extends Level {
    public Level5(){
        super();
        setNumber(4);
        setMessage(" The Last Step");
        setTime(30);
        NetworkSystem ns1 =  SystemFactory.createSystem(SystemType.ANTI_TROJAN , 750  , 180);
        ns1.addInputPort(new InputPort(ns1 , PortType.TRIANGLE));
        ns1.addInputPort(new InputPort(ns1 , PortType.RECTANGLE));
        ns1.addInputPort(new InputPort(ns1 , PortType.RECTANGLE));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.BIT_PACKET));
        ns1.addOutputPort(new OutputPort(ns1 , PortType.TRIANGLE));

        addSystem(ns1);
    }
}