package model.objects.packets;

import events.EventBus;
import events.GameEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import model.constants.GeometryUtils;
import model.interfaces.Updatable;
import model.objects.GameObject;
import model.objects.systems.InputPort;
import model.objects.systems.OutputPort;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class Connection extends GameObject implements Updatable {
    private static final Logger logger = LoggerFactory.getLogger(Connection.class);

    private List<Point> controlPoints;
    private double length;
    private InputPort target;
    private OutputPort source;
    private boolean isBusy;

    public Connection(InputPort target, OutputPort source) {
        super();
        this.target = target;
        this.source = source;
        source.setConnection(this);
        controlPoints = new ArrayList<Point>();
        controlPoints.add(source.getPoint());
        controlPoints.add(target.getPoint());
        connect();
        update();
        logger.info("New connection created: from {} to {}", source, target);
    }
    public void addHelperPoint(Point point){
        logger.info("Helper point added: {}", point);
                controlPoints.add(controlPoints.size()-1 , point);
    }

    public void removeHelperPoint(Point2D point){
        try {
            controlPoints.remove(point);

            logger.info("Helper point removed: {}", point);
        } catch (Exception e) {
            logger.error("Error removing helper point", e);
        }
    }

    public List<Point> getHelperPoints(){
        List<Point> temp = controlPoints;
        temp.removeFirst();
        temp.removeLast();
        return temp;
    }
    private void connect(){
        target.connect(source);
        source.connect(target);
    }
    public void disconnect(){
        target.disconnect();
        source.disconnect();
        source.setConnection(null);
    }

    private void makeShape() {
        Path2D path = new Path2D.Double();

        path.moveTo(controlPoints.get(0).getX(), controlPoints.get(0).getY());

        if (controlPoints.size() == 2) {
            Point2D p1 = controlPoints.get(1);
            path.lineTo(p1.getX(), p1.getY());
        }
        for (int i = 0; i < controlPoints.size() - 1; i++) {
            Point2D p0 = (i > 0) ? controlPoints.get(i - 1) : controlPoints.get(i);
            Point2D p1 = controlPoints.get(i);
            Point2D p2 = controlPoints.get(i + 1);
            Point2D p3 = (i + 2 < controlPoints.size()) ? controlPoints.get(i + 2) : p2;

            double ctrl1X = p1.getX() + (p2.getX() - p0.getX()) / 6.0;
            double ctrl1Y = p1.getY() + (p2.getY() - p0.getY()) / 6.0;

            double ctrl2X = p2.getX() - (p3.getX() - p1.getX()) / 6.0;
            double ctrl2Y = p2.getY() - (p3.getY() - p1.getY()) / 6.0;

            path.curveTo(ctrl1X, ctrl1Y, ctrl2X, ctrl2Y, p2.getX(), p2.getY());
        }

        super.shape = path;
    }



    public void updateLength() {
        if (shape != null)
            length = GeometryUtils.calcPathLength( (Path2D)shape);
        logger.debug("Connection length updated: {}", length);
    }

    public Point getRelativePoint(double t) {
        return shape != null ? GeometryUtils.getRelativePoint( (Path2D)shape, t) : null;
    }

    public Point getUnitTangentAt(double dist) {
        return  shape != null ? GeometryUtils.getUnitTangent((Path2D)shape, dist) : null;
    }

    public double getLength() { return length; }

    public InputPort getTarget() {
        return target;
    }

    public void setTarget(InputPort target) {
        this.target = target;
    }

    public OutputPort getSource() {
        return source;
    }

    public void setSource(OutputPort source) {
        this.source = source;
    }

    public List<Point> getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(List<Point> controlPoints) {
        this.controlPoints = controlPoints;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    @Override
    public void update() {
        makeShape();
    }
}

