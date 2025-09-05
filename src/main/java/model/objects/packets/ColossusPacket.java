package model.objects.packets;

import model.constants.Constants;
import model.constants.PacketRecord;
import model.constants.PacketSpeedRules;
import model.enums.PacketType;
import model.objects.other.Connection;
import model.objects.systems.NetworkSystem;

import java.io.Serializable;
import java.util.Random;

public class ColossusPacket extends Packet implements Serializable {

    public ColossusPacket(NetworkSystem system, PacketType packetType) {
        super(system, packetType);
    }

    @Override
    public void moveNormal(double deltaTime) {
       super.moveNormal(deltaTime);
       if(type==PacketType.RANGAROK){
           centerOfMass.setLocation(centerOfMass.getX() , centerOfMass.getY()+0.02);
       } else if (type==PacketType.TITAN) {
           acceleration = currentConnection.getTangentAt(distance).getSize()*30;
       }
    }
}
