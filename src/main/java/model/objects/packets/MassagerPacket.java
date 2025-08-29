package model.objects.packets;

import model.constants.Vector2D;
import model.constants.Constants;
import model.constants.PacketRecord;
import model.constants.PacketSpeedRules;
import model.enums.PacketType;
import model.objects.other.Connection;
import model.objects.systems.RooterSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Random;

public class MassagerPacket extends Packet implements Serializable {
    public MassagerPacket(RooterSystem system, PacketType type) {
        super(system,type);
    }
    @Override
    public void sendTo(Connection connection){
        this.currentConnection = connection;
        PacketRecord.PacketMovement packetRecord = PacketSpeedRules.getProperties(type, connection.getSource().getPortType());
        if(!(connection.getTarget().getPortType().getShape()==type.getShape())){
            this.velocity = 2*packetRecord.speed()* Constants.PACKET_SPEED;
        } else this.velocity = packetRecord.speed()* Constants.PACKET_SPEED;
        this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
        dirty = true;
    }
}
