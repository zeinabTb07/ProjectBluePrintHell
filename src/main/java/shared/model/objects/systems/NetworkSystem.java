package shared.model.objects.systems;

import shared.model.objects.packets.ColossusPacket;
import shared.api.enums.PacketType;
import shared.utils.math.Vector2D;
import shared.model.interfaces.Forceable;
import shared.model.interfaces.Updatable;
import shared.model.objects.GameObject;
import shared.model.objects.other.Connection;
import shared.model.objects.packets.MessagerPacket;
import shared.model.objects.packets.Packet;
import shared.model.objects.systems.addon.Inductor;
import shared.model.objects.systems.addon.InputPort;
import shared.model.objects.systems.addon.OutputPort;


import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class NetworkSystem extends GameObject implements Updatable , Forceable , Serializable {
    protected static transient final Logger log = LoggerFactory.getLogger(NetworkSystem.class);
    protected ArrayList<InputPort> inputPorts;
    protected ArrayList<OutputPort> outputPorts;
    protected Point point;

    protected ArrayList<Packet> storage;
    protected Inductor inductor;

    protected boolean active = true;
    protected boolean overlap;
    protected int coldDownCounter = 0 ;

    public NetworkSystem(Point point){
        super();
        this.point = point;
        storage = new ArrayList<>();
        inductor = new Inductor(this);
        inputPorts = new ArrayList<>();
        outputPorts = new ArrayList<>();
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
        PacketType packetPortType = p.getType();
        Connection con = null;
        for (OutputPort output : outputPorts){
           Connection c = output.getConnection();
            if (c!= null && !c.isBusy() && c.getTarget().getParentSystem().isActive()) {
                con = output.getConnection();
                if(p instanceof MessagerPacket){
                    if(output.getPortType().name().equals(packetPortType.name())){
                        return con;
                    }
                } else {
                    return con;
                }
            } else {
                if (c == null) {
                    log.debug("Port {}: No connection", output);
                } else if (c.isBusy()) {
                    log.debug("Connection {}: Busy", c);
                } else if (!c.getTarget().getParentSystem().isActive()) {
                    log.debug("Connection {}: Target system inactive", c);
                }
            }
        }
        return con;
    }

    public void process(){
        if (!storage.isEmpty()) trySendingPacket(storage.get(0));
    }

    public void receivePacket(Packet p){
        if(p instanceof ColossusPacket){
            p.getCurrentConnection().decreaseStrength();
        }
        storage.add(p);
        p.setCurrentSystem(this);
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
                .map(InputPort::getConnection)
                .filter(Objects::nonNull)
                .map(Connection::getTarget)
                .filter(Objects::nonNull)
                .map(InputPort::getParentSystem)
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
    }

    public Inductor getInductor() {
        return inductor;
    }

    public void setInductor(Inductor inductor) {
        this.inductor = inductor;
    }


    public boolean isOverlap() {
        return overlap;
    }

    public void setOverlap(boolean overlap) {
        this.overlap = overlap;
    }

    public int getColdDownCounter() {
        return coldDownCounter;
    }

    public void setColdDownCounter(int coldDownCounter) {
        this.coldDownCounter = coldDownCounter;
    }

    @Override
    public void moveInduced(Vector2D forceVector) {
        Point2D oldCenter = point;
        point = new Point(
                (int) (point.getX() + forceVector.getX()),
                (int) (point.getY() +  forceVector.getY())
        );
        log.debug("System moved by force, oldCenter={}, newCenter={}, force={}",
                oldCenter, point, forceVector);
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if(!active) log.info("System {} deactivate",id);
    }

    public void reset(){
        storage.clear();
        active = true;
        coldDownCounter = 0 ;
    }

    @Override
    public void update() {
        if(!active){
            coldDownCounter++;
        }
        if(coldDownCounter>=300){
            coldDownCounter = 0 ;
            active = true;
            log.info("System {} reactivate",id);
        }
    }

}
