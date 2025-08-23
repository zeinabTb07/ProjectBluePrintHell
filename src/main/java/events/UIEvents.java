package events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UIEvents {
    private static final Logger log = LoggerFactory.getLogger(UIEvents.class);

    public record OpenMenuEvent() {
        public OpenMenuEvent {
            EventBus.publish(new GameEvents.PauseGameEvent(true));
            log.info("Main menu opened by the user.");
        }
    }

    public record OpenGameEvent() {
        public OpenGameEvent {
            EventBus.publish(new GameEvents.PauseGameEvent(false));
            log.info("Game screen initialized and displayed.");
        }
    }

    public record OpenSettingsEvent() {
        public OpenSettingsEvent {
            log.info("User opened the settings panel.");
        }
    }


    public record OpenShopEvent() {
        public OpenShopEvent {
            EventBus.publish(new GameEvents.PauseGameEvent(true));
            log.info("Player accessed the in-game store.");
        }
    }

    public record VolumeChangeEvent(int volume) {
        public VolumeChangeEvent {
            log.info("Volume level changed to {}%.", volume);
        }
    }

    public record PlaySoundEvent(String path) {
        public PlaySoundEvent {
            log.info("Playing sound effect from: {}", path);
        }
    }

    public record ReplayEvent() {
        public ReplayEvent {
            EventBus.publish(new OpenGameEvent());
            log.info("Game will start over");
        }
    }
    public record RepaintGamePanelEvent() { }
}
