package model.systems;

import model.PortType;

public class OutputPort extends Port<InputPort> {
    public OutputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
    }

}


