package model;

import model.systems.InputPort;
import model.systems.OutputPort;

import java.awt.*;
import java.util.ArrayList;

public class Connection {
    private InputPort target;
    private OutputPort source;
    private ArrayList<Point> points;

    public Connection(InputPort target, OutputPort source) {
        this.target = target;
        this.source = source;
        connect();

    }

    private void connect(){
        target.connect(source);
        source.connect(target);
    }
    public void disconnect(){
        target.disconnect();
        source.disconnect();
    }
    public InputPort getTarget(){
        return target;
    }

    public OutputPort getSource(){
        return source;
    }

}
