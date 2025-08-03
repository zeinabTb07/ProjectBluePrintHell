package model.packets;

import model.Shape;

public enum MassagerPacketType {
    SQUARE(Shape.SQUARE, new PacketProperties(10, 3, 2.0, 0.0)),
    TRIANGLE(Shape.TRIANGLE, new PacketProperties(5, 2, 1.5, 1.2));

    private final Shape shape;
    private final PacketProperties props;

    MassagerPacketType(Shape shape, PacketProperties props) {
        this.shape = shape;
        this.props = props;
    }

    public Shape getShape() { return shape; }
    public PacketProperties getProperties() { return props; }
}

