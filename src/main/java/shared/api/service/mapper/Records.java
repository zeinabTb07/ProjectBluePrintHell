package shared.api.service.mapper;


import shared.api.enums.PacketType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Records {
    public record PacketProperties(int coin, int size) {}
    public record PacketMovement(int speed , int acceleration){ }
    public record ColossusPackets(UUID uuid , PacketType type){}


    public record PlayerRecord(int score, String createdAt) implements Serializable {
        public PlayerRecord(int score) {
            this(score, LocalDateTime.now().toString());
        }
    }
}
