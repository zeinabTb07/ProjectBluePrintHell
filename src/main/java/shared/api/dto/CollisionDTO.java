package shared.api.dto;

import java.awt.*;

public class CollisionDTO {
    private  double radius ;
    private Point center ;

    public CollisionDTO (double radius , Point center){
        this.center =center;
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }
}
