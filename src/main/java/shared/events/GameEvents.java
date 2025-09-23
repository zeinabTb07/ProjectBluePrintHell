package shared.events;

import shared.model.objects.other.Connection;
import shared.model.objects.packets.Packet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;


public class GameEvents {
    private static final Logger log = LoggerFactory.getLogger(GameEvents.class);

    public record StartGameEvent() {
        public StartGameEvent {
            log.info("Game started.");
        }
    }

    public record GoOnline(){
        public GoOnline{
            log.info("Player try to connect");
        }
    }

    public record CheckGameEndEvent(boolean b){
        public CheckGameEndEvent {
            log.info("Game ends : {}", b ? "win" :"lost");
        }
    }

    public record GoToLevel(int n){
        public GoToLevel{
            log.info("Game level goes to : {}", n);
        }
    }


    public record ConnectionDestroyEvent(Connection connection){
        public ConnectionDestroyEvent{
            log.info("Connection Destroyed : {}" , connection.getId());
        }
    }

    public record PauseGameEvent(boolean b) {
        public PauseGameEvent {
            log.info("Game {}", b ? "Paused" : "Running");
        }
    }

    public record SwapPacketEvent(Packet from , Packet to) {
        public SwapPacketEvent {
            log.info("Packet : {} changed to {}",from , to );
        }
    }

    public record PacketLostEvent(Packet packet) {
        public PacketLostEvent {
            log.info("Packet lost: {}", packet);
        }
    }


    public record CoinGeneratedEvent(int n) {
        public CoinGeneratedEvent {
            log.info("Coins increased : {} " , n);
        }
    }

    public record SetPowerUpPoint(ShopEvents.PowerUpType type , Point point){
        public SetPowerUpPoint {
            log.info("PowerUp : {} will happen at {} " , type , point);
        }
    }
}