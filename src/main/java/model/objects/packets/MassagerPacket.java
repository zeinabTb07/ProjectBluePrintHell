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

public class MassagerPacket extends Packet {
    public MassagerPacket(RooterSystem system, PacketType type) {
        super(system,type);
        super.currentSystem = system;
        super.size = type.getProperties().size();
        super.coin = type.getProperties().coin();
        makeShape();
    }

    public void sendTo(Connection connection){
        this.currentConnection = connection;
        PacketRecord.PacketMovement packetRecord = PacketSpeedRules.getProperties(getType() , connection.getSource().getPortType());
                this.velocity = packetRecord.speed()*Constants.PACKET_SPEED;
                this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
    }


    private void makeShape() {
        try {
            super.shape = getType().getShape().getShape(getAbsolutePoint(), size * Constants.PACKET_SIZE_SCALE);
        } catch (Exception e) {
            log.error("Failed to create shape for packet: {}", e.getMessage(), e);
        }
    }

    @Override
    public void moveNormal(double deltaTime) {
        velocity += acceleration * deltaTime;
        super.distance += velocity * deltaTime;
    }

    @Override
    public void update() {
        makeShape();
    }



}
