package model.objects.packets;

import model.constants.Vector2D;
import model.constants.Constants;
import model.constants.PacketRecord;
import model.constants.PacketSpeedRules;
import model.enums.MassagerPacketType;
import model.objects.systems.RooterSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Point;
import java.awt.geom.Point2D;

public class MassagerPacket extends Packet {
    private static final Logger log = LoggerFactory.getLogger(MassagerPacket.class);
    private MassagerPacketType type;

    public MassagerPacket(RooterSystem system, MassagerPacketType type) {
        super(system);
        this.type = type;
        super.currentSystem = system;
        super.size = type.getProperties().size();
        super.coin = type.getProperties().coin();
        makeShape();
    }

    public void sendTo(Connection connection){
        this.currentConnection = connection;
        PacketRecord.PacketMovement packetRecord = PacketSpeedRules.getProperties(this.type , connection.getSource().getPortType());
                this.velocity = packetRecord.speed()*Constants.PACKET_SPEED;
                this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
    }

    public Point getAbsolutePoint() {
        if (super.currentConnection == null) {
            Point p = super.getCurrentSystem().getPoint();
            return new Point(
                    p.x + Constants.SYSTEMS_WIDTH / 2,
                    p.y + 2 * Constants.INDUCTOR_HEIGHT
            );
        } else {
            Point2D p = currentConnection.getRelativePoint(distance);
            return new Point((int) (centerOfMass.getX() + p.getX()),
                    (int) (centerOfMass.getY() + p.getY())
            );
        }
    }

    private void makeShape() {
        try {
            super.shape = type.getShape().getShape(getAbsolutePoint(), size * Constants.PACKET_SIZE_SCALE);
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

    @Override
    public void moveInduced(Vector2D forceVector) {
        Point2D oldCenter = centerOfMass;
        centerOfMass = new Point2D.Double(
                centerOfMass.getX() + (int) forceVector.getX(),
                centerOfMass.getY() + (int) forceVector.getY()
        );

        log.debug("Packet moved by force: type={}, oldCenter={}, newCenter={}, force={}",
                type, oldCenter, centerOfMass, forceVector);
    }


    public MassagerPacketType getType() {
        return type;
    }

    public void setType(MassagerPacketType type) {
        this.type = type;
    }
}
