package events;

import java.awt.*;

public class GameEvents {
    public record StartGameEvent(){}
    public record CollisionDetectedEvent(Point p){}
    public record PauseGameEvent(){}
    public record PacketLostEvent(){}
    public record CheckConnectivity(boolean b){}

}
