package model.objects.other;


import events.EventBus;
import events.GameEvents;
import utils.Vector2D;
import model.objects.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import utils.GeometryUtils;
import model.interfaces.Updatable;
import model.objects.systems.addon.InputPort;
import model.objects.systems.addon.OutputPort;


import java.awt.geom.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Connection extends GameObject implements Updatable , Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Connection.class);

    private List<Point2D> helperPoint;
    private double length;
    private InputPort target;
    private OutputPort source;
    private boolean isBusy;
    private boolean dirty;
    private boolean freeze;
    private int connectionStrength = 3 ;

    public Connection(InputPort target, OutputPort source) {
        super();
        this.target = target;
        this.source = source;
        source.setConnection(this);
        target.setConnection(this);
        helperPoint = new ArrayList<Point2D>();
        connect();
        makeShape();
        length = GeometryUtils.calcPathLength((Path2D) shape);
        logger.info("New connection created: from {} to {}", source, target);
    }

    public void addHelperPoint(Point2D point){
        logger.info("Helper point added: {}", point);
        helperPoint.add(point);
        dirty = true;
        update();
    }

    public void removeHelperPoint(Point2D point) {
        try {
            helperPoint.remove(point);

            logger.info("Helper point removed: {}", point);
        } catch (Exception e) {
            logger.error("Error removing helper point", e);
        }
        dirty = true;
    }

    public List<Point2D> getHelperPoints(){
        return helperPoint;
    }
    public void setHelperPoints(List<Point2D> helperPoint){
        this.helperPoint = helperPoint;
    }
    private void connect(){
        target.connect(source);
        source.connect(target);
    }
    public void disconnect(){
        target.disconnect();
        source.disconnect();
        source.setConnection(null);
        target.setConnection(null);
    }

    private void makeShape() {
        Path2D path = new Path2D.Double();
        List<Point2D> controlPoints = new ArrayList<>(helperPoint);
        controlPoints.addFirst(source.getPoint());
        controlPoints.addLast(target.getPoint());
        path.moveTo(controlPoints.get(0).getX(), controlPoints.get(0).getY());

        if (controlPoints.size() == 2) {
            Point2D p1 = controlPoints.get(1);
            path.lineTo(p1.getX(), p1.getY());
        } else {
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
            length = GeometryUtils.calcPathLength((Path2D) shape);
            logger.info("Connection with ID : {} reshaped with length {}"  , id , length);
        }

        super.shape = path;
    }

    public Point2D getRelativePoint(double t) {
        return shape != null ? GeometryUtils.getPointAtDistance( (Path2D)shape, t) : null;
    }

    public Vector2D getTangentAt(double dist) {
        return  shape != null ? GeometryUtils.getTangent((Path2D)shape, dist) : null;
    }

    public void decreaseStrength(){
        connectionStrength--;
        if(connectionStrength<=0){
            EventBus.publish(new GameEvents.ConnectionDestroyEvent(this));
        }
    }

    public List<Point2D> getHelperPoint() {
        return helperPoint;
    }

    public void setHelperPoint(List<Point2D> helperPoint) {
        this.helperPoint = helperPoint;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

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

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public int getConnectionStrength() {
        return connectionStrength;
    }

    public void setConnectionStrength(int connectionStrength) {
        this.connectionStrength = connectionStrength;
    }

    @Override
    public void update() {
        if (dirty){
            makeShape();
        }
    }
}
