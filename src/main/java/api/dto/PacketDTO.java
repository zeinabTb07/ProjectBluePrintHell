package  api.dto;

import enums.PacketType;

import java.awt.*;
import java.util.UUID;

public class PacketDTO {
    private UUID id;
    private PacketType packetType;
    private Point position;

    public PacketDTO(PacketType packetType, int x, int y) {
        this.packetType = packetType;
        this.position=new Point(x,y);
    }

    // Getters & Setters
    public UUID getId() { return id; }
    public PacketType getPacketType() { return packetType; }
    public int getX() { return position.x; }
    public int getY() { return position.y; }
    public Point getPosition(){ return position; }
}
