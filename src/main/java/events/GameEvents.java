package events;

import model.objects.packets.Packet;

import java.awt.*;

public class GameEvents {
    public record StartGameEvent(){}
    public record CollisionDetectedEvent(Point p){}
    public record PauseGameEvent(){}
    public record PacketLostEvent(Packet packet){}
    public record PacketReachedEnd(Packet packet){}
    public record CheckConnectivity(boolean b){}

}
