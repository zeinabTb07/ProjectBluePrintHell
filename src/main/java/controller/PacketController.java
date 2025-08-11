package controller;

import model.objects.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketController {
    private List<Packet> packets;
    public PacketController(ArrayList<Packet> packets){
        this.packets = packets;
    }
}
