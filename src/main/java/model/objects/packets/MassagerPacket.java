package model.objects.packets;


import model.constants.Constants;
import model.constants.PacketRecord;
import model.constants.PacketSpeedRules;
import model.enums.GameRecords;
import model.enums.PacketType;
import model.objects.other.Connection;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;

import java.io.Serializable;
import java.util.UUID;


public class MassagerPacket extends Packet implements Serializable {
    private GameRecords.ColossusPackets parentColossus;
    public MassagerPacket(NetworkSystem system, PacketType type) {
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

    public GameRecords.ColossusPackets getParentColossusId() {
        return parentColossus;
    }

    public void setParentColossusId(GameRecords.ColossusPackets parentColossusId) {
        this.parentColossus = parentColossusId;
    }
}
