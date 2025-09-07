package model.objects.packets;

import model.constants.Constants;
import utils.PacketRecord;
import utils.PacketSpeedRules;
import model.enums.PacketType;
import model.objects.other.Connection;

import java.io.Serializable;
import java.util.Random;

public class ProtectedPacket extends Packet implements Serializable {
    private Packet basePacket;

    public ProtectedPacket(Packet packet) {
        super(packet.getCurrentSystem(), packet.getType());
        setType(PacketType.LOCK);
        setSize(2*packet.size);
        basePacket = packet;
        packet.setCoin(5);
        this.id = packet.getId();
    }
    @Override
    public void sendTo(Connection connection){
        this.currentConnection = connection;
        PacketType[] types = {PacketType.TRIANGLE, PacketType.SQUARE, PacketType.BITE};
        Random random = new Random();
        PacketRecord.PacketMovement packetRecord = PacketSpeedRules.getProperties(types[random.nextInt(0 , 3)] , connection.getSource().getPortType());
        this.velocity = packetRecord.speed()* Constants.PACKET_SPEED;
        this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
        dirty = true;
    }

    public Packet getBasePacket() {
        return basePacket;
    }

    public void setBasePacket(Packet basePacket) {
        this.basePacket = basePacket;
    }
    @Override
    public Packet clon() {
        return new ProtectedPacket(basePacket);
    }
}
