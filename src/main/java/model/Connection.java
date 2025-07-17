package model;

import view.InfoBar;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Connection {
    private double distance;
    private InputPort target;
    private OutputPort source;
    private Curve curve ;

    public Connection(InputPort target, OutputPort source) {
        this.target = target;
        this.source = source;
        connect();
        makeCurve();
       // this.distance = calculateDistance();
    }

    public int getLength (){
        return (int) Point2D.distance(curve.getPoints().getFirst().getX(), curve.getPoints().getFirst().getY(),
                curve.getPoints().getLast().getX(), curve.getPoints().getLast().getY());
    }

    private void makeCurve(){
        ArrayList<Point> list = new ArrayList<>();
        Point start = new Point(source.getX() + source.getParentSystem().getX()
                , source.getY() +  source.getParentSystem().getY());

        Point end = new Point(target.getX() + target.getParentSystem().getX(),
                target.getY() + target.getParentSystem().getY());


         list.add(start);

         list.add( new Point(
                 (start.x + end.x)/2 ,
                 (start.y + end.y)/2
         ) );
         list.add(end);

        curve = new Curve(list);
    }
    private void connect(){
        target.connect(source);
        source.connect(target);
    }
    public void disconnect(){
        target.disconnect();
        source.disconnect();
        curve = null;
    }
    public InputPort getTarget(){
        return target;
    }

    public OutputPort getSource(){
        return source;
    }


    public Curve getCurve() {
        return curve;
    }
}
