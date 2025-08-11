package model.objects.packets;

import model.Vector2D;
import model.constants.Constants;
import model.enums.MassagerPacketType;
import model.objects.systems.RooterSystem;

import java.awt.*;
import java.awt.geom.Point2D;

public class MassagerPacket extends Packet  {
    private MassagerPacketType type;

    public MassagerPacket(RooterSystem system, MassagerPacketType type){
        super(system);
        this.type = type;
        super.currentSystem = system;
        super.size = type.getProperties().size();
        super.coin = type.getProperties().coin();
        update();
    }

    public Point getAbsolutePoint(){
        if(super.currentConnection==null){
            Point p = super.getCurrentSystem().getPoint();
            return new Point(p.x+ Constants.SYSTEMS_WIDTH/2,
                    p.y+ 2 * Constants.INDUCTOR_HEIGHT);
        } else {
            Point p = currentConnection.getRelativePoint(distance/currentConnection.getLength());
            return new Point(centerOfMass.x+p.x , centerOfMass.y+p.y);
        }
    }


    private void makeShape(){
        super.shape = type.getShape().getShape(getAbsolutePoint() , size*Constants.PACKET_SIZE_SCALE);
    }

    public void setType(MassagerPacketType type) {
        this.type = type;
    }

    public MassagerPacketType getType() {
        return type;
    }

    @Override
    public void moveNormal(int deltaTime) {

    }

    @Override
    public void moveInduced(Vector2D vector2D) {

    }

    @Override
    public void update() {
        makeShape();
    }
}
