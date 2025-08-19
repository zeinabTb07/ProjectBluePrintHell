package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.constants.Constants;
import model.constants.Vector2D;
import model.enums.GameShape;
import model.interfaces.Forceable;
import model.interfaces.Updatable;
import model.objects.GameObject;
import model.objects.other.Connection;
import model.objects.packets.MassagerPacket;
import model.objects.packets.Packet;
import model.objects.systems.addon.Inductor;
import model.objects.systems.addon.InputPort;
import model.objects.systems.addon.OutputPort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.*;




public abstract class NetworkSystem extends GameObject implements Updatable , Forceable {
    protected static final Logger log = LoggerFactory.getLogger(NetworkSystem.class);
    protected ArrayList<InputPort> inputPorts;
    protected ArrayList<OutputPort> outputPorts;

    protected ArrayList<Packet> storage;
    protected Point point;
    protected Inductor inductor;

    protected boolean dirty;

    public NetworkSystem(Point point){
        super();
        this.point = point;
        storage = new ArrayList<>();
        inductor = new Inductor(this);
        inputPorts = new ArrayList<>();
        outputPorts = new ArrayList<>();
        dirty = true ;
    }

    protected void makeShape(){
        RoundRectangle2D rectangle = new RoundRectangle2D.Double(point.getX(),
                point.getY() ,
                Constants.SYSTEMS_WIDTH ,
                Math.max(outputPorts.size(), inputPorts.size())*Constants.PORT_GAP+1.5f*Constants.INDUCTOR_HEIGHT,
                8 ,
                8);
        super.shape = rectangle;
    }

    protected void trySendingPacket(Packet p) {
        Connection connection = getProperConnection(p);
        if (connection != null && !connection.isBusy()) {
            p.sendTo(connection);
            connection.setBusy(true);
            storage.remove(p);
            log.info("Packet {} sent to connection from {} to {}", p, connection.getSource(), connection.getTarget());
        } else {
            log.debug("No available connection for packet {}", p);
        }
    }

    protected Connection getProperConnection(Packet p) {
        GameShape packetPortType = p.getType().getShape();
        Connection con = null;
        for (OutputPort output : outputPorts){
            Connection c = output.getConnection();
            if (c!= null && !c.isBusy()) {
                con = output.getConnection();
                if(p instanceof MassagerPacket){
                    if(output.getPortType().getShape()==packetPortType){
                        return con;
                    }
                } else {
                    return con;
                }
            }
        }
        return con;
    }

    public void process(){
        if (!storage.isEmpty()) trySendingPacket(storage.get(0));
    }

    public void receivePacket(Packet p){
        storage.add(p);
        p.setCurrentSystem(this);
        EventBus.publish(new GameEvents.CoinGeneratedEvent(p.getSize()));
    }


    public void addInputPort(InputPort port) {
        Objects.requireNonNull(port, "Port cannot be null");
        inputPorts.add(port);
    }

    public void addOutputPort(OutputPort port) {
        Objects.requireNonNull(port, "Port cannot be null");
        outputPorts.add(port);
    }



    public Set<NetworkSystem> getNeighbors() {
        Set<NetworkSystem> neighbors = new HashSet<>();

        outputPorts.stream()
                .map(OutputPort::getConnection)
                .filter(Objects::nonNull)
                .map(Connection::getTarget)
                .filter(Objects::nonNull)
                .map(InputPort::getParentSystem)
                .filter(Objects::nonNull)
                .forEach(neighbors::add);

        inputPorts.stream()
                .map(InputPort::getConnectedTo)
                .filter(Objects::nonNull)
                .map(OutputPort::getParentSystem)
                .filter(Objects::nonNull)
                .forEach(neighbors::add);

        return neighbors;
    }


    public ArrayList<InputPort> getInputPorts() {
        return inputPorts;
    }

    public void setInputPorts(ArrayList<InputPort> inputPorts) {
        this.inputPorts = inputPorts;
    }

    public ArrayList<OutputPort> getOutputPorts() {
        return outputPorts;
    }

    public void setOutputPorts(ArrayList<OutputPort> outputPorts) {
        this.outputPorts = outputPorts;
    }

    public ArrayList<Packet> getStorage() {
        return storage;
    }

    public void setStorage(ArrayList<Packet> storage) {
        this.storage = storage;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        dirty = true;
    }

    public Inductor getInductor() {
        return inductor;
    }

    public void setInductor(Inductor inductor) {
        this.inductor = inductor;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public void moveInduced(Vector2D forceVector) {
        Point2D oldCenter = point;
        point = new Point(
                (int) (point.getX() + forceVector.getX()),
                (int) (point.getY() +  forceVector.getY())
        );
        dirty = true ;
        log.debug("System moved by force, oldCenter={}, newCenter={}, force={}",
                oldCenter, point, forceVector);
    }

    @Override
    public void update() {
        if(dirty){
            makeShape();
            inductor.update();
            inputPorts.stream()
                    .forEach(InputPort::update);
            outputPorts.stream()
                    .forEach(OutputPort::update);
            dirty = false;
        }
    }

}
