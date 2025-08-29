package model.enums;

import java.lang.reflect.Type;
import java.util.UUID;

public class GameRecords {
public record ColossusPackets(UUID uuid , PacketType type){}
}
