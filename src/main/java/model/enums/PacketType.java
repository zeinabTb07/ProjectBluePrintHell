package model.enums;

import model.constants.PacketRecord;

public enum PacketType {
    SQUARE(GameShape.SQUARE, new PacketRecord.PacketProperties(2, 2)),
    TRIANGLE(GameShape.TRIANGLE, new PacketRecord.PacketProperties(3, 3)),
    BITE(GameShape.BITE , new PacketRecord.PacketProperties(1 , 1)),

    LOCK(GameShape.LOCK , new PacketRecord.PacketProperties(5 , 0)),

    PHANTOM(GameShape.PHANTOM , new PacketRecord.PacketProperties(3 , 4)),
    SPIRIT(GameShape.SPIRIT , new PacketRecord.PacketProperties(4 , 6)),

    TITAN(GameShape.TITAN , new PacketRecord.PacketProperties(8 , 8)),
    RANGAROK (GameShape.RANGAROK , new PacketRecord.PacketProperties(10 , 10));

    private final GameShape shape;
    private final PacketRecord.PacketProperties props;

    PacketType(GameShape shape, PacketRecord.PacketProperties props) {
        this.shape = shape;
        this.props = props;
    }

    public GameShape getShape() { return shape; }
    public PacketRecord.PacketProperties getProperties() { return props;}


}

