package model.objects.packets;


import model.constants.Constants;
import utils.PacketRecord;
import utils.PacketSpeedRules;
import model.enums.PacketType;
import model.objects.other.Connection;
import model.objects.systems.NetworkSystem;


import java.io.Serializable;



public class MessagerPacket extends Packet implements Serializable {
    private PacketRecord.ColossusPackets parentColossus;
    public MessagerPacket(NetworkSystem system, PacketType type) {
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

    public PacketRecord.ColossusPackets getParentColossusId() {
        return parentColossus;
    }

    public void setParentColossusId(PacketRecord.ColossusPackets parentColossusId) {
        this.parentColossus = parentColossusId;
    }
    @Override
    public Packet clon() {
        return new MessagerPacket(currentSystem , type);
    }
}
