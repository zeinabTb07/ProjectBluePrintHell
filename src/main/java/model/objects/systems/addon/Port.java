package model.objects.systems.addon;

import model.constants.Constants;
import model.interfaces.Updatable;
import model.objects.GameObject;
import model.enums.PortType;
import model.objects.other.Connection;
import model.objects.systems.NetworkSystem;

import java.awt.*;
import java.io.Serializable;

public abstract class Port<T extends Port<?>> extends GameObject  implements Updatable , Serializable {
    private NetworkSystem parentSystem;
    private PortType portType;
    private T connectedTo;
    protected  int n ;
    private Connection connection;

    public Port(NetworkSystem parentSystem, PortType portType) {
        super();
        this.parentSystem = parentSystem ;
        this.portType = portType ;
    }

    public void connect(T connectedTo) {
        if(!isConnected()){
            this.connectedTo = connectedTo;
        }
    }


    protected void makeShape(){
        super.shape = portType.getShape().getShape(getPoint() , Constants.PORT_SIZE);
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

    public T getConnectedTo() {
        return connectedTo;
    }

    public void setConnectedTo(T connectedTo) {
        this.connectedTo = connectedTo;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
    public abstract Point getPoint();
    @Override
    public void update() {
        makeShape();
        if(getConnection()!=null){
            connection.setDirty(true);
            getConnection().update();
        }
    }
}