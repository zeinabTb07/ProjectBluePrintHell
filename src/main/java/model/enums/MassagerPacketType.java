package model.enums;

import model.PacketRecord;

public enum MassagerPacketType {
    SQUARE(GameShape.SQUARE, new PacketRecord.PacketProperties(2, 2)),
    TRIANGLE(GameShape.TRIANGLE, new PacketRecord.PacketProperties(3, 3));

    private final GameShape shape;
    private final PacketRecord.PacketProperties props;

    MassagerPacketType(GameShape shape, PacketRecord.PacketProperties props) {
        this.shape = shape;
        this.props = props;
    }

    public GameShape getShape() { return shape; }
    public PacketRecord.PacketProperties getProperties() { return props;}


}

