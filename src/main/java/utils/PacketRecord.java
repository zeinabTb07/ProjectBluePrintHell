package utils;

import model.enums.PacketType;

import java.util.UUID;

public class PacketRecord {
    public record PacketProperties(int coin, int size) {}
    public record PacketMovement(int speed , int acceleration){ }
    public record ColossusPackets(UUID uuid , PacketType type){}
}
