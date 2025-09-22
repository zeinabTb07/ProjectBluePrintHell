package shared.model.objects.systems;

import shared.api.service.mapper.PacketDetails;
import shared.api.service.mapper.PacketRecord;
import shared.events.GameEvents;
import shared.model.objects.packets.ColossusPacket;
import shared.model.objects.packets.MessagerPacket;
import shared.model.objects.packets.Packet;

import java.io.Serializable;


import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MergeSystem extends NetworkSystem implements Serializable {
    private HashMap<PacketRecord.ColossusPackets, ArrayList<MessagerPacket>> packetsMap;
    private HashMap<UUID , Integer> colossusPacketLostMap;
    public MergeSystem(Point point) {
        super(point);
        packetsMap = new HashMap<>();
        colossusPacketLostMap = new HashMap<>();
    }


    @Override
    public void process(){
        if (!storage.isEmpty()) trySendingPacket(storage.get(0));

         for (PacketRecord.ColossusPackets key : new ArrayList<>(packetsMap.keySet())) {
             ArrayList<MessagerPacket> packets = packetsMap.get(key);
              int expectedSize = PacketDetails.getProperties(key.type()).size();
             int lostCount = colossusPacketLostMap.getOrDefault(key.uuid(), 0);

             if (packets.size() == expectedSize - lostCount) {
                 ColossusPacket colossusPacket = new ColossusPacket(this , key.type());

                 colossusPacket.setId(key.uuid());
                for (MessagerPacket packet : packets) {
                    publisher.publish(new GameEvents.SwapPacketEvent(packet , colossusPacket));
                }
                storage.add(colossusPacket);
                packetsMap.remove(key);
            }

        }
    }


    @Override
    public void receivePacket(Packet p){
      super.receivePacket(p);
      if(p instanceof MessagerPacket){
          MessagerPacket packet = (MessagerPacket) p;
          if(packet.getParentColossusId()!=null){
              storage.remove(p);
              if(packetsMap.containsKey(packet.getParentColossusId())){
                  packetsMap.get(packet.getParentColossusId()).add(packet);
              } else {
                  ArrayList<MessagerPacket> arr = new ArrayList<>();
                  arr.add(packet);
                  packetsMap.put(packet.getParentColossusId() , arr);
              }
          }
      }
    }

    @Override
    public void reset(){
      super.reset();
        packetsMap = new HashMap<>();
        colossusPacketLostMap = new HashMap<>();
    }

    public HashMap<PacketRecord.ColossusPackets, ArrayList<MessagerPacket>> getPacketsMap() {
        return packetsMap;
    }

    public void setPacketsMap(HashMap<PacketRecord.ColossusPackets, ArrayList<MessagerPacket>> packetsMap) {
        this.packetsMap = packetsMap;
    }

    public HashMap<UUID, Integer> getColossusPacketLostMap() {
        return colossusPacketLostMap;
    }

    public void setColossusPacketLostMap(HashMap<UUID, Integer> colossusPacketLostMap) {
        this.colossusPacketLostMap = colossusPacketLostMap;
    }
}
