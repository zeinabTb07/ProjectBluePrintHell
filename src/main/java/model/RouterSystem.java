package model;

import java.awt.*;
import java.util.ArrayList;

public class RouterSystem extends NetworkSystem {
    protected ArrayList<Packet> initialPackets;

    public RouterSystem(Point point , ArrayList<Packet> initialPackets) {
        super(point);
        if(initialPackets!=null){
            this.initialPackets = initialPackets;
        }
    }
    @Override
    public void process(Packet packet) {
       //packet.end;
    }

    public void sentInitialPackets(){
        for(Packet packet : initialPackets){
            super.process(packet);
            break;
        }
    }
}
