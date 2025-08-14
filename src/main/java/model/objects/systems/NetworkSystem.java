package model.objects.systems;

import model.constants.Constants;
import model.enums.GameShape;
import model.interfaces.Updatable;
import model.objects.GameObject;
import model.enums.PortType;
import model.objects.packets.Connection;
import model.objects.packets.Packet;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;



public class NetworkSystem extends GameObject implements Updatable {
    private HashMap<GameShape, ArrayList<InputPort>> inputPorts;
    private HashMap<GameShape , ArrayList<OutputPort>> outputPorts;
    private ArrayList<Packet> storage;
    private Point point;
    private Inductor inductor;

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
        RoundRectangle2D rectangle = new RoundRectangle2D.Float(point.x,
                point.y ,
                Constants.SYSTEMS_WIDTH ,
                Math.max(getOutPortsSize(), getInputPortsSize())*Constants.PORT_GAP+1.5f*Constants.INDUCTOR_HEIGHT,
                8 ,
                8);
        super.shape = rectangle;
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
