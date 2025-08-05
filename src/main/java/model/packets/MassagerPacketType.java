package model.packets;

import model.Shape;

public enum MassagerPacketType {
    SQUARE(Shape.SQUARE, new PacketProperties(2, 2)),
    TRIANGLE(Shape.TRIANGLE, new PacketProperties(3, 3));

    private final Shape shape;
    private final PacketProperties props;

    MassagerPacketType(Shape shape, PacketProperties props) {
        this.shape = shape;
        this.props = props;
    }

    public Shape getShape() { return shape; }
    public PacketProperties getProperties() { return props; }
}

