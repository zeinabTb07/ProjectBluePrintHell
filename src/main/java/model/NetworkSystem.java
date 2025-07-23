package model;


import java.awt.*;
import java.util.ArrayList;

public class NetworkSystem implements Processable {
    protected Point point ;
    protected ArrayList<Packet> storage ;
    protected ArrayList<InputPort> inputPorts ;
    protected ArrayList<OutputPort> outputPorts ;
    protected Inductor inductor ;
    public NetworkSystem(Point point ) {
        this.inputPorts = new ArrayList<>();
        this.outputPorts = new ArrayList<>();
        inductor = new Inductor(this);
        this.point = point;
    }
    public ArrayList<InputPort> getInputs(){
        return inputPorts;
    }
    public ArrayList<OutputPort> getOutput(){
        return outputPorts;
    }


    public Point getPoint() {
        return point;
    }
    public void setPoint(Point point){
        this.point = point;
    }

    public Inductor getInductor(){
        return inductor;
    }

    @Override
    public void process(Packet packet) {
//        OutputPort tempPort ;
//      for(OutputPort outputPort : outputPorts){
//          if(outputPort.getConnecetedPort())
//      }
    }
}