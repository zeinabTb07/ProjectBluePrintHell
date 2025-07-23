package model;

public class Vector2D {
    private float x;
    private float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this(0, 0);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }


    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D multiply(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

//    public Vector2D normalize() {
//        float mag = magnitude();
//        if (mag == 0) return new Vector2D(0, 0);
//        return new Vector2D(x / mag, y / mag);
//    }
//
//    public float distance(Vector2D other) {
//        float dx = this.x - other.x;
//        float dy = this.y - other.y;
//        return (float) Math.sqrt(dx * dx + dy * dy);
//    }
//
//
//    public static Vector2D fromAngle(float angleRadians) {
//        return new Vector2D(
//                (float) Math.cos(angleRadians),
//                (float) Math.sin(angleRadians)
//        );
//    }
//
//    public float toAngle() {
//        return (float) Math.atan2(y, x);
//    }
//
//    @Override
//    public String toString() {
//        return String.format("(%.2f, %.2f)", x, y);
//    }
}