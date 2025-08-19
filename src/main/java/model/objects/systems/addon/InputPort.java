package model.objects.systems.addon;


import model.constants.Constants;
import model.enums.PortType;
import model.objects.systems.NetworkSystem;

import java.awt.*;

public class
InputPort extends Port<OutputPort> {
    public InputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
        n = parentSystem.getInputPorts().size();
        update();
    }

    @Override
    public Point getPoint() {
        Point p = getParentSystem().getPoint();
        return new Point(p.x,
                p.y + n*Constants.PORT_GAP +2*Constants.INDUCTOR_HEIGHT);
    }

    @Override
    public void update() {
        super.makeShape();
    }
}
