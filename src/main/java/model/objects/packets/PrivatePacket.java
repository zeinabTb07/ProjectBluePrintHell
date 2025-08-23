package model.objects.packets;

import model.constants.Constants;
import model.constants.PacketRecord;
import model.constants.PacketSpeedRules;
import model.enums.PacketType;
import model.objects.other.Connection;
import model.objects.systems.RooterSystem;

import java.io.Serializable;
import java.util.Random;

public class PrivatePacket extends Packet implements Serializable {
    public PrivatePacket(RooterSystem system, PacketType packetType) {
        super(system, packetType);
    }
    public void sendTo(Connection connection){
        this.currentConnection = connection;
        PacketType[] types = {PacketType.TRIANGLE, PacketType.SQUARE, PacketType.BITE};
        Random random = new Random();
        PacketRecord.PacketMovement packetRecord = PacketSpeedRules.getProperties(types[random.nextInt(0 , 3)] , connection.getSource().getPortType());
        this.velocity = packetRecord.speed()* Constants.PACKET_SPEED;
        this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
        dirty = true;
    }
}
