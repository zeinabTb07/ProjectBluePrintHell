package events;

import model.objects.packets.Packet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    public record PacketLostEvent(Packet packet) {
        public PacketLostEvent {
            log.info("Packet lost: {}", packet);
        }
    }


    public record CheckConnectivity(boolean b) {
        public CheckConnectivity {
            log.info("Connectivity check result: {}", b ? "Connected" : "Disconnected");
        }
    }
    public record CoinGeneratedEvent(int n) {
        public CoinGeneratedEvent {
            log.info("Coins increased : {} " , n);
        }
    }
}