package model.objects.systems;

import java.awt.*;
import java.io.Serializable;


import events.EventBus;
import events.GameEvents;
import model.enums.GameRecords;
import model.objects.packets.ColossusPacket;
import model.objects.packets.MassagerPacket;
import model.objects.packets.Packet;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MergeSystem extends NetworkSystem implements Serializable {
    private HashMap<GameRecords.ColossusPackets, ArrayList<MassagerPacket>> packetsMap;
    public MergeSystem(Point point) {
        super(point);
        packetsMap = new HashMap<>();
    }

    @Override
    public void process(){
        if (!storage.isEmpty()) trySendingPacket(storage.get(0));
        System.out.println("prossing merger ");

         for (GameRecords.ColossusPackets key : packetsMap.keySet()) {
            ArrayList<MassagerPacket> packets = packetsMap.get(key);
             ColossusPacket colossusPacket = new ColossusPacket(this , key.type());
            if(packets.size()>=8){
                colossusPacket.setId(key.uuid());
                for (MassagerPacket packet : packets) {
                    EventBus.publish(new GameEvents.SwapPacketEvent(packet , colossusPacket));
                }
                System.out.println("making collosos");
                storage.add(colossusPacket);
                packetsMap.remove(key);
            }
             System.out.println("key removed");
        }
    }


    @Override
    public void receivePacket(Packet p){
      super.receivePacket(p);
      if(p instanceof MassagerPacket){
          MassagerPacket packet = (MassagerPacket) p;
          if(packet.getParentColossusId()!=null){
              storage.remove(p);
              if(packetsMap.containsKey(packet.getParentColossusId())){
                  packetsMap.get(packet.getParentColossusId()).add(packet);
              } else {
                  ArrayList<MassagerPacket> arr = new ArrayList<>();
                  arr.add(packet);
                  packetsMap.put(packet.getParentColossusId() , arr);
              }
          }
      }
    }

}
