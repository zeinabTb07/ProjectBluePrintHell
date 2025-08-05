package model.systems;

import model.packets.Packet;

import java.awt.*;
import java.util.ArrayList;

public class NetworkSystem {
    private ArrayList<InputPort> inputPorts;
    private ArrayList<OutputPort> outputPorts;
    private ArrayList<Packet> storage;
    private Point placeOnScreen;

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

    public Point getPlaceOnScreen() {
        return placeOnScreen;
    }

    public void setPlaceOnScreen(Point placeOnScreen) {
        this.placeOnScreen = placeOnScreen;
    }
}
