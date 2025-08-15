package model.objects.systems;


import model.constants.Constants;
import model.enums.PortType;

import java.awt.*;
import java.awt.geom.Point2D;

public class
InputPort extends Port<OutputPort> {
    public InputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
        Point p = parentSystem.getPoint();
        super.setPoint(new Point((int) p.getX(),
                (int) (parentSystem.getPoint().getY() + Constants.PORT_GAP*parentSystem.getInputPortsSize()+2*Constants.INDUCTOR_HEIGHT)));
        update();
    }

    @Override
    public void update() {
        super.makeShape();
    }
}
