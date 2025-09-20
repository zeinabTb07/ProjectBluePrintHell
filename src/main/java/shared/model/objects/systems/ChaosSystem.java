package shared.model.objects.systems;

import shared.model.objects.other.Connection;
import shared.model.objects.packets.MessagerPacket;
import shared.model.objects.packets.Packet;
import shared.model.objects.packets.ProtectedPacket;
import shared.model.objects.systems.addon.OutputPort;
import shared.api.enums.PacketType;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class ChaosSystem extends NetworkSystem implements Serializable {
    public ChaosSystem(Point point) {
        super(point);
    }

    @Override
    public void receivePacket(Packet p){
        super.receivePacket(p);
       if(!(p instanceof ProtectedPacket)){
           if(p.getNoise() == 0){
               p.setNoise(1);
           }
           int n = new Random().nextInt(3);
           if(n==1) p.setTrojan(true);
       }
    }
    @Override
    protected Connection getProperConnection(Packet p) {
        PacketType packetPortType = p.getType();
        Connection con = null;
        for (OutputPort output : outputPorts){
            Connection c = output.getConnection();
            if (c!= null && !c.isBusy() && c.getTarget().getParentSystem().isActive()) {
                con = output.getConnection();
                if(p instanceof MessagerPacket){
                    if(!output.getPortType().name().equals(packetPortType.name())){
                        return con;
                    }
                } else {
                    return con;
                }
            } else {
                if (c == null) {
                    log.debug("Port {}: No connection", output);
                } else if (c.isBusy()) {
                    log.debug("Connection {}: Busy", c);
                } else if (!c.getTarget().getParentSystem().isActive()) {
                    log.debug("Connection {}: Target system inactive", c);
                }
            }
        }
        return con;
    }
}
