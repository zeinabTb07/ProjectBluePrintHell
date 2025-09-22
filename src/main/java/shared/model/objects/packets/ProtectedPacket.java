package shared.model.objects.packets;

import client.Constants;
import shared.api.enums.PacketType;
import shared.api.service.mapper.Records;
import shared.api.service.mapper.SpeedRules;
import shared.model.objects.other.Connection;

import java.io.Serializable;
import java.util.Random;

public class ProtectedPacket extends Packet implements Serializable {
    private Packet basePacket;

    public ProtectedPacket(Packet packet) {
        super(packet.getCurrentSystem(), PacketType.PROTECTED);
        basePacket = packet;
        this.id = packet.getId();
    }
    @Override
    public void sendTo(Connection connection){
        this.currentConnection = connection;
        PacketType[] types = {PacketType.TRIANGLE, PacketType.RECTANGLE, PacketType.BIT_PACKET};
        Random random = new Random();
        Records.PacketMovement packetRecord = SpeedRules.getProperties(types[random.nextInt(0 , 3)] , connection.getSource().getPortType());
        this.velocity = packetRecord.speed()* Constants.PACKET_SPEED;
        this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
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
