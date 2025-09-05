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
    private HashMap<UUID , Integer> colossusPacketLostMap;
    public MergeSystem(Point point) {
        super(point);
        packetsMap = new HashMap<>();
        colossusPacketLostMap = new HashMap<>();
        EventBus.subscribe(GameEvents.PacketLostEvent.class , e->{
            if(e.packet() instanceof  MassagerPacket){
                MassagerPacket p = (MassagerPacket) e.packet();
                if(p.getParentColossusId()!=null){
                    colossusPacketLostMap.merge(p.getParentColossusId().uuid(), 1, Integer::sum);
                }
            }
        });
    }

    @Override
    public void process(){
        if (!storage.isEmpty()) trySendingPacket(storage.get(0));

         for (GameRecords.ColossusPackets key : new ArrayList<>(packetsMap.keySet())) {
             ArrayList<MassagerPacket> packets = packetsMap.get(key);
              int expectedSize = key.type().getProperties().size();
             int lostCount = colossusPacketLostMap.getOrDefault(key.uuid(), 0);

             if (packets.size() == expectedSize - lostCount) {
                 ColossusPacket colossusPacket = new ColossusPacket(this , key.type());

                 colossusPacket.setId(key.uuid());
                for (MassagerPacket packet : packets) {
                    EventBus.publish(new GameEvents.SwapPacketEvent(packet , colossusPacket));
                }
                storage.add(colossusPacket);
                packetsMap.remove(key);
            }

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
