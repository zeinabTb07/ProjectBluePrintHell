package shared.model.objects.systems.addon;

import shared.model.objects.GameObject;
import shared.model.objects.other.Connection;
import shared.model.objects.systems.NetworkSystem;
import shared.api.enums.PortType;

import java.awt.*;
import java.io.Serializable;

public abstract class Port extends GameObject  implements Serializable {
    private NetworkSystem parentSystem;
    private PortType portType;
    protected int n ;
    private Connection connection;

    public Port(NetworkSystem parentSystem, PortType portType) {
        super();
        this.parentSystem = parentSystem ;
        this.portType = portType ;
    }

    public boolean isConnected(){
        return connection!=null;
    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public NetworkSystem getParentSystem() {
        return parentSystem;
    }

    public void setParentSystem(NetworkSystem parentSystem) {
        this.parentSystem = parentSystem;
    }

    public PortType getPortType() {
        return portType;
    }

    public void setPortType(PortType portType) {
        this.portType = portType;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public abstract Point getPoint();
}