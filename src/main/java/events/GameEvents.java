package events;

import model.objects.packets.Packet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.ChangeEvent;
import java.util.ArrayList;


public class GameEvents {
    private static final Logger log = LoggerFactory.getLogger(GameEvents.class);

    public record StartGameEvent() {
        public StartGameEvent {
            log.info("Game started.");
        }
    }

    public record CheckGameEndEvent(boolean b){
        public CheckGameEndEvent {
            log.info("Game ends : {}", b ? "win" :"lost");
            if(b){
                EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/won.wav"));
            } else {
                EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/lost.wav"));
            }
        }
    }

    public record GoToLevel(int n){
        public GoToLevel{
            log.info("Game level goes to : {}", n);
        }
    }


    public record ConnectionEvent(double lengthChange){
        public ConnectionEvent{
            log.info("Connection Length Change : {}" , lengthChange);

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
}