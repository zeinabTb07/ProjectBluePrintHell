package shared.model.objects.packets;


import client.Constants;
import shared.api.enums.PacketType;
import shared.utils.mapper.PacketRecord;
import shared.utils.mapper.SpeedRules;
import shared.model.objects.other.Connection;
import shared.model.objects.systems.NetworkSystem;


import java.io.Serializable;



public class MessagerPacket extends Packet implements Serializable {
    private PacketRecord.ColossusPackets parentColossus;
    public MessagerPacket(NetworkSystem system, PacketType type) {
        super(system,type);
    }

    @Override
    public void sendTo(Connection connection){
        this.currentConnection = connection;
        PacketRecord.PacketMovement packetRecord = SpeedRules.getProperties(type, connection.getSource().getPortType());
        if(!(connection.getTarget().getPortType().name().equals(type.name()))){
            this.velocity = 2*packetRecord.speed()* Constants.PACKET_SPEED;
        } else this.velocity = packetRecord.speed()* Constants.PACKET_SPEED;
        this.acceleration = packetRecord.acceleration()*Constants.PACKET_ACCELERATION;
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
