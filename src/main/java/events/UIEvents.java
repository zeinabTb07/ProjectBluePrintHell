package events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UIEvents {
    private static final Logger log = LoggerFactory.getLogger(UIEvents.class);

    public record OpenMenuEvent() {
        public OpenMenuEvent {
            log.info("Main menu opened by the user.");
        }
    }

    public record OpenGameEvent() {
        public OpenGameEvent {
            log.info("Game screen initialized and displayed.");
        }
    }

    public record OpenSettingsEvent() {
        public OpenSettingsEvent {
            log.info("User opened the settings panel.");
        }
    }

    public record OpenLevelsEvent() {
        public OpenLevelsEvent {
            log.info("Level selection screen is now visible.");
        }
    }

    public record OpenShopEvent() {
        public OpenShopEvent {
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

    public record ChooseLevelEvent(int n) {
        public ChooseLevelEvent {
            log.info("Level reset to: {}" ,n);
        }
    }
    public record RepaintGamePanelEvent() { }
}
