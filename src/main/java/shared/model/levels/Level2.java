package shared.model.levels;

import shared.model.objects.systems.SpySystem;
import shared.model.objects.systems.SystemFactory;
import shared.model.objects.systems.addon.InputPort;
import shared.model.objects.systems.addon.OutputPort;
import shared.api.enums.PortType;
import shared.api.enums.SystemType;

import java.util.ArrayList;

public class Level2 extends Level {
    public Level2(){
        super();
        setNumber(1);
        setMessage(" They Are Spying On You");
        setTime(20);
        ArrayList<SpySystem> spySystems = new ArrayList<>();
        SpySystem spy1 = (SpySystem) SystemFactory.createSystem(SystemType.SPY , 510 , 490);
        spy1.addOutputPort(new OutputPort(spy1 , PortType.TRIANGLE));
        spy1.addOutputPort(new OutputPort(spy1 , PortType.SQUARE));
        spy1.addOutputPort(new OutputPort(spy1 , PortType.BIT_PACKET));
        spy1.addInputPort(new InputPort(spy1 , PortType.BIT_PACKET));
        spy1.addInputPort(new InputPort(spy1 , PortType.SQUARE));

        SpySystem spy2 = (SpySystem) SystemFactory.createSystem(SystemType.SPY , 230 , 300);
        spy2.addInputPort(new InputPort(spy2 , PortType.BIT_PACKET));
        spy2.addOutputPort(new OutputPort(spy2 , PortType.TRIANGLE));
        spy2.addOutputPort(new OutputPort(spy2 , PortType.SQUARE));


        SpySystem spy3 = (SpySystem) SystemFactory.createSystem(SystemType.SPY , 600 , 200);
        spy3.addInputPort(new InputPort(spy3 , PortType.TRIANGLE));
        spy3.addOutputPort(new OutputPort(spy3 , PortType.TRIANGLE));
        spy3.addInputPort(new InputPort(spy3 , PortType.TRIANGLE));
        spy3.addOutputPort(new OutputPort(spy3 , PortType.TRIANGLE));



        spySystems.add(spy1);
        spySystems.add(spy2);
        spySystems.add(spy3);

        spy1.setSpies(spySystems);
        spy2.setSpies(spySystems);
        spy3.setSpies(spySystems);

        addSystem(spy1);
        addSystem(spy2);
        addSystem(spy3);
    }
}