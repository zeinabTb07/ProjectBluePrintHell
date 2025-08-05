package model.systems;

import model.PortType;

import java.awt.*;

public abstract class Port<T extends Port<?>> {
    private NetworkSystem parentSystem;
    private PortType portType;
    private T connectedTo;

    public Port(NetworkSystem parentSystem, PortType portType) {
        this.parentSystem = parentSystem ;
        this.portType = portType ;
    }

    public void connect(T connectedTo) {
        if(!isConnected()){
            this.connectedTo = connectedTo;
        }
    }

    public void disconnect() {
        if (isConnected()) {
            T temp = connectedTo;
            connectedTo = null;
            temp.disconnect();
        }
    }
    public boolean isConnected() {
        return connectedTo != null;
    }

    public PortType getPortType(){
        return portType;
    }

    public NetworkSystem getParentSystem(){
        return parentSystem;
    }

    public Port getConnecetedPort(){
        return connectedTo;
    }

}