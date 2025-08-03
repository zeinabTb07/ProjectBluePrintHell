package model;


import model.packets.PacketProperties;

public enum PortType {
    SQUARE(Shape.SQUARE),
    TRIANGLE(Shape.TRIANGLE);

    private final Shape shape;

    PortType(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() { return shape; }

}

