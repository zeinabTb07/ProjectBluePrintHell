package model;


import java.awt.*;
import java.util.List;

public class Curve  {
    private List<Point> points;
    private int stroke = 2;

    public Curve(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public Stroke getStroke() {
       return new BasicStroke(stroke);
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }
}