package model.objects.systems;

import model.constants.Constants;
import model.interfaces.Updatable;
import model.objects.GameObject;

import java.awt.*;
import java.util.List;

public class Inductor extends GameObject implements Updatable {
    protected NetworkSystem system ;

    protected Inductor(NetworkSystem system){
        this.system = system ;
        super.id = system.getId();
        makeShape();
    }

    private void makeShape(){
        Point p = system.getPoint();
        Rectangle rectangle = new Rectangle(p.x + 2 ,
                p.y + 2 ,
                Constants.SYSTEMS_WIDTH-4 ,
                Constants.INDUCTOR_HEIGHT);
        super.shape = rectangle;
    }

    public boolean checkConnections() {
        boolean inputsConnected = system.getInputPorts().values()
                .stream()
                .flatMap(List::stream)
                .allMatch(InputPort::isConnected);
        if (!inputsConnected) return false;

        return system.getOutputPorts().values()
                .stream()
                .flatMap(List::stream)
                .allMatch(OutputPort::isConnected);
    }

    public NetworkSystem getSystem() {
        return system;
    }

    public void setSystem(NetworkSystem system) {
        this.system = system;
    }


    @Override
    public void update() {
        makeShape();
    }
}