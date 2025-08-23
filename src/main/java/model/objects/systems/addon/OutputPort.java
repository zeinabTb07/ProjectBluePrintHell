package model.objects.systems.addon;

import model.constants.Constants;
import model.enums.PortType;
import model.objects.other.Connection;
import model.objects.systems.NetworkSystem;

import java.awt.*;
import java.io.Serializable;


public class OutputPort extends Port<InputPort>  implements Serializable {
    private Connection connection;
    public OutputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
        n = parentSystem.getOutputPorts().size();
        update();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Point getPoint() {
        Point p = getParentSystem().getPoint();
        return new Point(p.x + Constants.SYSTEMS_WIDTH ,
                p.y + n*Constants.PORT_GAP +2*Constants.INDUCTOR_HEIGHT);
    }

    @Override
    public void update() {
        super.makeShape();
    }
}


