package model.systems;


import model.PortType;

public class
InputPort extends Port<OutputPort> {
    public InputPort(NetworkSystem parentSystem, PortType portType) {
        super(parentSystem, portType);
    }
}
