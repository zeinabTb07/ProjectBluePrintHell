package model;

import view.InfoBar;

import java.awt.*;

public abstract class Port<T extends Port<?>> {
    private G_System parentSystem;
    private Type portType;
    private T connectedTo;
    private int x , y ;
    private Polygon shape ;

    public Port(G_System parentSystem, Type portType) {
        this.parentSystem = parentSystem ;
        this.portType = portType ;
    }

    public void connect(T connectedTo) {
        if(!isConnected()){
            this.connectedTo = connectedTo;
        }
        InfoBar.checkRunable();
    }

    public void disconnect() {
        if (isConnected()) {
            T temp = connectedTo;
            connectedTo = null;
            temp.disconnect();
        }
        InfoBar.checkRunable();
    }
    public boolean isConnected() {
        return connectedTo != null;
    }

    public Type getPortType(){
        return portType;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public Polygon getShape(){
        return shape;
    }

    public void setShape(Polygon polygon){
        shape = polygon;
    }

    public G_System getParentSystem(){
        return parentSystem;
    }

    public Port getConnecetedPort(){
        return connectedTo;
    }

}