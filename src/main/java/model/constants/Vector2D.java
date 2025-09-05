package model.constants;

import java.awt.*;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D (Point startPoint, Point endPoint) {
        this.x = endPoint.getX() - startPoint.getX();
        this.y = endPoint.getY() - startPoint.getY();
    }

    public double getSize(){
        return Math.sqrt(x*x + y*y);
    }

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }


    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }


    public Vector2D normalize() {
        double mag = magnitude();
        return mag != 0 ? new Vector2D(x / mag, y / mag) : new Vector2D();
    }


    public Vector2D rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector2D(
                x * cos - y * sin,
                x * sin + y * cos
        );
    }

    public double distanceTo(Vector2D other) {
        return this.subtract(other).magnitude();
    }

    public double angleTo(Vector2D other) {
        return Math.acos(this.dot(other) / (this.magnitude() * other.magnitude()));
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector2D)) return false;
        Vector2D other = (Vector2D) obj;
        return Double.compare(x, other.x) == 0
                && Double.compare(y, other.y) == 0;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}
