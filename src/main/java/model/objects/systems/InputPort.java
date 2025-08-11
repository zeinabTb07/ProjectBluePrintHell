package model.objects.systems;


import model.constants.Constants;
import model.enums.PortType;

import java.awt.*;

public class
InputPort extends Port<OutputPort> {
    public InputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
        Point p = parentSystem.getPoint();
        super.setPoint(new Point(p.x ,
                parentSystem.getPoint().y+ Constants.PORT_GAP*parentSystem.getInputPortsSize()+2*Constants.INDUCTOR_HEIGHT));
        update();
    }

    @Override
    public void update() {
        super.makeShape();
    }
}
