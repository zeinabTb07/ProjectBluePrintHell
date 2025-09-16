package model.objects.systems;

import events.EventBus;
import events.GameEvents;
import model.enums.GameShape;
import model.objects.other.Connection;
import model.objects.packets.ColossusPacket;
import model.objects.packets.MessagerPacket;
import model.objects.packets.Packet;
import model.objects.packets.ProtectedPacket;
import model.objects.systems.addon.OutputPort;

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
        GameShape packetPortType = p.getType().getShape();
        Connection con = null;
        for (OutputPort output : outputPorts){
            Connection c = output.getConnection();
            if (c!= null && !c.isBusy() && c.getTarget().getParentSystem().isActive()) {
                con = output.getConnection();
                if(p instanceof MessagerPacket){
                    if(output.getPortType().getShape()!=packetPortType){
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
