package events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UIEvents {
    private static final Logger log = LoggerFactory.getLogger(UIEvents.class);

    public record OpenMenu() {
        public OpenMenu {
            EventBus.publish(new GameEvents.PauseGameEvent(true));
            log.info("Main menu opened by the user.");
        }
    }

    public record OpenGame() {
        public OpenGame {
            EventBus.publish(new GameEvents.PauseGameEvent(false));
            log.info("Game screen initialized and displayed.");
        }
    }

    public record SaveGame() { }

    public record OpenSetting() {
        public OpenSetting {
            log.info("User opened the settings panel.");
        }
    }


    public record OpenShop() {
        public OpenShop {
            EventBus.publish(new GameEvents.PauseGameEvent(true));
            log.info("Player accessed the in-game store.");
        }
    }

    public record VolumeChange(int volume) {
        public VolumeChange {
            log.info("Volume level changed to {}%.", volume);
        }
    }

    public record PlaySound(String path) {
        public PlaySound {
            log.info("Playing sound effect from: {}", path);
        }
    }

    public record Replay() {
        public Replay {
            EventBus.publish(new OpenGame());
            log.info("Game will start over");
        }
    }
    public record RepaintGamePanelEvent() { }
}
