package model.objects.packets;

import model.Vector2D;
import model.constants.Constants;
import model.enums.MassagerPacketType;
import model.objects.systems.RooterSystem;

import java.awt.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Point;

public class MassagerPacket extends Packet {
    private static final Logger log = LoggerFactory.getLogger(MassagerPacket.class);
    private MassagerPacketType type;

    public MassagerPacket(RooterSystem system, MassagerPacketType type) {
        super(system);
        this.type = type;
        super.currentSystem = system;
        super.size = type.getProperties().size();
        super.coin = type.getProperties().coin();

        log.debug("Created new MassagerPacket: type={}, size={}, coin={}",
                type, size, coin);
        update();
    }

    public Point getAbsolutePoint() {
        if (super.currentConnection == null) {
            Point p = super.getCurrentSystem().getPoint();
            return new Point(
                    p.x + Constants.SYSTEMS_WIDTH / 2,
                    p.y + 2 * Constants.INDUCTOR_HEIGHT
            );
        } else {
            Point p = currentConnection.getRelativePoint(distance / currentConnection.getLength());
            return new Point(
                    centerOfMass.x + p.x,
                    centerOfMass.y + p.y
            );
        }
    }

    private void makeShape() {
        try {
            super.shape = type.getShape().getShape(getAbsolutePoint(), size * Constants.PACKET_SIZE_SCALE);
            log.trace("Updated shape for packet: type={}, position={}",
                    type, getAbsolutePoint());
        } catch (Exception e) {
            log.error("Failed to create shape for packet: {}", e.getMessage(), e);
        }
    }

    @Override
    public void moveNormal(double deltaTime) {
        double oldDistance = super.distance;
        super.distance += acceleration * deltaTime * deltaTime / 2 + velocity * deltaTime;

        log.trace("Packet moved: type={}, oldDistance={}, newDistance={}",
                type, oldDistance, distance);
    }

    @Override
    public void update() {
        log.debug("Updating packet: type={}, currentSystem={}",
                type, currentSystem.getId());
        moveNormal(10);
        makeShape();
    }

    @Override
    public void moveInduced(Vector2D forceVector) {
        Point oldCenter = centerOfMass;
        centerOfMass = new Point(
                centerOfMass.x + (int) forceVector.getX(),
                centerOfMass.y + (int) forceVector.getY()
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
