package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.constants.Constants;
import model.enums.GameShape;
import model.interfaces.Updatable;
import model.objects.GameObject;
import model.objects.other.Connection;
import model.objects.packets.MassagerPacket;
import model.objects.packets.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.*;
import java.util.List;



public class NetworkSystem extends GameObject implements Updatable {
    protected static final Logger logger = LoggerFactory.getLogger(NetworkSystem.class);
    protected HashMap<GameShape, ArrayList<InputPort>> inputPorts;
    protected HashMap<GameShape , ArrayList<OutputPort>> outputPorts;
    protected ArrayList<Packet> storage;
    protected Point point;
    protected Inductor inductor;

    public NetworkSystem(Point point){
        super();
        this.point = point;
        storage = new ArrayList<>();
        inductor = new Inductor(this);
        inputPorts = new HashMap<>();
        outputPorts = new HashMap<>();
        update();
    }

    private <T extends Port> void addPort(HashMap<GameShape, ArrayList<T>> portMap, T port) {
        Objects.requireNonNull(port, "Port cannot be null");
        portMap.computeIfAbsent(port.getPortType().getShape(), k -> new ArrayList<>()).add(port);
        update();
    }

    private void makeShape(){
        RoundRectangle2D rectangle = new RoundRectangle2D.Double(point.getX(),
                point.getY() ,
                Constants.SYSTEMS_WIDTH ,
                Math.max(getOutPortsSize(), getInputPortsSize())*Constants.PORT_GAP+1.5f*Constants.INDUCTOR_HEIGHT,
                8 ,
                8);
        super.shape = rectangle;
    }

    protected void trySendingPacket(Packet p) {
        if (!(p instanceof MassagerPacket)) {
            logger.error("Packet is not a MassagerPacket: {}", p);
            return;
        }
        Connection connection = getProperConnection(p);
        if (connection != null && !connection.isBusy()) {
            p.sendTo(connection);
            connection.setBusy(true);
            storage.remove(p);
            logger.info("Packet {} sent to connection from {} to {}", p, connection.getSource(), connection.getTarget());
        } else {
            logger.debug("No available connection for packet {}", p);
        }
    }

    protected Connection getProperConnection(Packet p) {
        if (!(p instanceof MassagerPacket)) {
            logger.error("Packet is not a MassagerPacket: {}", p);
            return null;
        }
        MassagerPacket mp = (MassagerPacket) p;
        GameShape packetPortType = mp.getType().getShape();
        ArrayList<OutputPort> ports = getOutputPorts().get(packetPortType);
        if(ports!=null){
            for (OutputPort output : ports){
                if (output.getConnection() != null && !output.getConnection().isBusy()) {
                    return output.getConnection();
                }
            }
        }

        for (ArrayList<OutputPort> outputs : getOutputPorts().values()) {
            for (OutputPort output : outputs){
                if (output.getConnection() != null && !output.getConnection().isBusy()) {
                    return output.getConnection();
                }
            }
        }
        return null;
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
        addPort(inputPorts, port);
    }

    public void addOutputPort(OutputPort port) {
        addPort(outputPorts, port);
    }

    public int getInputPortsSize() {
        int sum = 0;
        for (ArrayList<InputPort> ports : inputPorts.values()) {
            sum += ports.size();
        }
        return sum;
    }
    public int getOutPortsSize() {
        int sum = 0;
        for (ArrayList<OutputPort> ports : outputPorts.values()) {
            sum += ports.size();
        }
        return sum;
    }


    public Set<NetworkSystem> getNeighbors() {
        Set<NetworkSystem> neighbors = new HashSet<>();

        outputPorts.values().stream()
                .flatMap(List::stream)
                .map(OutputPort::getConnection)
                .filter(Objects::nonNull)
                .map(Connection::getTarget)
                .filter(Objects::nonNull)
                .map(InputPort::getParentSystem)
                .filter(Objects::nonNull)
                .forEach(neighbors::add);

        inputPorts.values().stream()
                .flatMap(List::stream)
                .map(InputPort::getConnectedTo)
                .filter(Objects::nonNull)
                .map(OutputPort::getParentSystem)
                .filter(Objects::nonNull)
                .forEach(neighbors::add);

        return neighbors;
    }

    public HashMap<GameShape, ArrayList<InputPort>> getInputPorts() {
        return inputPorts;
    }

    public void setInputPorts(HashMap<GameShape, ArrayList<InputPort>> inputPorts) {
        this.inputPorts = inputPorts;
    }

    public HashMap<GameShape, ArrayList<OutputPort>> getOutputPorts() {
        return outputPorts;
    }

    public void setOutputPorts(HashMap<GameShape, ArrayList<OutputPort>> outputPorts) {
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

    @Override
    public void update() {
        makeShape();
    }

}
