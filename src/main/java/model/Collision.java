package model;

import model.constants.Constants;
import model.interfaces.Updatable;

import java.awt.*;

public class Collision implements Updatable {
    private Point p;
    private double radius;
    public Collision (Point p){
        this.p = p;
    }

    @Override
    public void update() {
        radius += Constants.WAVE_SPEED;
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }
}
