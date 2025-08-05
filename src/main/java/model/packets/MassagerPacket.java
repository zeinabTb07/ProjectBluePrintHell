package model.packets;

import model.systems.RooterSystem;

public class MassagerPacket extends Packet {
    private MassagerPacketType type;

    public MassagerPacket(RooterSystem system, MassagerPacketType type){
        this.type = type;
        super.currentSystem = system;
        super.size = type.getProperties().size();
        super.coin = type.getProperties().coin();
    }

    public void setType(MassagerPacketType type) {
        this.type = type;
    }

    public MassagerPacketType getType() {
        return type;
    }
}
