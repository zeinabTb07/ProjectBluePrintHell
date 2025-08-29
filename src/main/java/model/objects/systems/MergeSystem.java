package model.objects.systems;

import java.awt.*;
import java.io.Serializable;


import events.EventBus;
import events.GameEvents;
import model.objects.packets.ColossusPacket;
import model.objects.packets.MassagerPacket;
import model.objects.packets.Packet;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MergeSystem extends NetworkSystem implements Serializable {
    private HashMap<UUID , ArrayList<MassagerPacket>> packetsMap;
    public MergeSystem(Point point) {
        super(point);
        packetsMap = new HashMap<>();
    }

    @Override
    public void receivePacket(Packet p){
      super.receivePacket(p);
      if(p instanceof MassagerPacket){
          MassagerPacket packet = (MassagerPacket) p;
          if(packet.getParentColossusId()!=null){
              storage.remove(p);
              if(packetsMap.containsKey(packet.getParentColossusId())){
                  packetsMap.get((packet.getParentColossusId())).add(packet);
              }
          }
      }
    }

}
