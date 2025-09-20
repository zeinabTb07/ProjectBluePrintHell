package shared.model.objects.other;


import shared.model.objects.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import shared.model.objects.systems.addon.InputPort;
import shared.model.objects.systems.addon.OutputPort;
import shared.utils.math.GeometryUtils;


import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Connection extends GameObject implements  Serializable {
    private static transient final Logger logger = LoggerFactory.getLogger(Connection.class);

    private List<Point2D> helperPoint;
    private InputPort target;
    private OutputPort source;
    private boolean isBusy;
    private boolean freeze;
    private int connectionStrength = 3 ;
    private double length;

    public Connection(InputPort target, OutputPort source) {
        super();
        this.target = target;
        this.source = source;
        source.setConnection(this);
        target.setConnection(this);
        helperPoint = new ArrayList<Point2D>();
        connect();
        logger.info("New connection created: from {} to {}", source, target);
    }

    public void addHelperPoint(Point2D point){
        logger.info("Helper point added: {}", point);
        helperPoint.add(point);
    }

    public void removeHelperPoint(Point2D point) {
        try {
            helperPoint.remove(point);
            logger.info("Helper point removed: {}", point);
        } catch (Exception e) {
            logger.error("Error removing helper point", e);
        }
    }

    public Point2D getRelativePoint(double dis){
        Path2D path2D = GeometryUtils.getPath2d(this);
        return GeometryUtils.getPointAtDistance(path2D , dis);
    }

    public List<Point2D> getHelperPoints(){
        return helperPoint;
    }
    public void setHelperPoints(List<Point2D> helperPoint){
        this.helperPoint = helperPoint;
    }
    private void connect(){
        target.setConnection(this);
        source.setConnection(this);
    }
    public void disconnect(){
        source.setConnection(null);
        target.setConnection(null);
    }


    public void decreaseStrength(){
        connectionStrength--;
    }

    public List<Point2D> getHelperPoint() {
        return helperPoint;
    }

    public void setHelperPoint(List<Point2D> helperPoint) {
        this.helperPoint = helperPoint;
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
