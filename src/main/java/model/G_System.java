package model;

import java.util.ArrayList;

public class G_System {
    protected static int STORAGE_SIZE = 5 ;
    protected final int x ;
    protected final int y ;
    protected int width ;
    protected int height ;
    protected ArrayList<Packet> storage ;
    protected ArrayList<InputPort> inputPorts ;
    protected ArrayList<OutputPort> outputPorts ;
    protected Inductor inductor ;
    public G_System(int x , int y ) {
        this.inputPorts = new ArrayList<>();
        this.outputPorts = new ArrayList<>();
        inductor = new Inductor(this);
        this.x = x ;
        this.y = y ;
        width = 120 ;
        height = 30 ;
    }

    public void addInputPort(InputPort port){
        port.setX(0);
        port.setY((1+inputPorts.size())*30+10);
        inputPorts.add(port);
        setSize();
    }

    private void setSize(){
        height = 25 + 30*(1+Math.max(inputPorts.size(), outputPorts.size()));

    }

    public void addOutputPort (OutputPort port){
        port.setX(this.getWidth());
        port.setY((1+outputPorts.size())*30 + 10);
        outputPorts.add(port);
        setSize();
    }


    public ArrayList<InputPort> getInputs(){
        return inputPorts;
    }
    public ArrayList<OutputPort> getOutput(){
        return outputPorts;
    }

    public class Inductor {
        protected G_System system ;
        protected Boolean connected ;

        protected Inductor(G_System system){
            this.system = system ;
            connected = false ;
        }

        public boolean checkConnections (){
            connected = true ;
            for(InputPort port : system.getInputs()){
                if(!port.isConnected()){
                    connected = false ;
                    return connected;
                }

            }
            for (OutputPort port : system.getOutput()){
                if(!port.isConnected()) {
                    connected = false ;
                    return connected;
                }
            }
            return connected;
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    public Inductor getInductor(){
        return inductor;
    }
}

