package model.objects.systems;

import model.constants.Constants;
import model.enums.PortType;

import java.awt.*;

public class OutputPort extends Port<InputPort> {
    public OutputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
        Point p = parentSystem.getPoint();
        super.setPoint(new Point(p.x + Constants.SYSTEMS_WIDTH ,
                p.y + Constants.PORT_GAP * parentSystem.getOutPortsSize()+2*Constants.INDUCTOR_HEIGHT));
        update();
    }

    @Override
    public void update() {
        super.makeShape();
    }
}


