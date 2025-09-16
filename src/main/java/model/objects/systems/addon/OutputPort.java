package model.objects.systems.addon;

import model.constants.Constants;
import model.enums.PortType;
import model.objects.other.Connection;
import model.objects.systems.NetworkSystem;

import java.awt.*;
import java.io.Serializable;


public class OutputPort extends Port<InputPort>  implements Serializable {
    public OutputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
        n = parentSystem.getOutputPorts().size();
        update();
    }

    @Override
    public Point getPoint() {
        Point p = getParentSystem().getPoint();
        return new Point(p.x + Constants.SYSTEMS_WIDTH +5,
                p.y + n*Constants.PORT_GAP +2*Constants.INDUCTOR_HEIGHT);
    }
}


