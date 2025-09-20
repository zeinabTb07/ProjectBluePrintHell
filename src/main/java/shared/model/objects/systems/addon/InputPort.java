package shared.model.objects.systems.addon;


import client.Constants;

import shared.model.objects.systems.NetworkSystem;
import shared.api.enums.PortType;

import java.awt.*;
import java.io.Serializable;

public class
InputPort extends Port implements Serializable {
    public InputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
        n = parentSystem.getInputPorts().size();
    }

    @Override
    public Point getPoint() {
        Point p = getParentSystem().getPoint();
        return new Point(p.x-5,
                p.y + n*Constants.PORT_GAP +2*Constants.INDUCTOR_HEIGHT);
    }
}
