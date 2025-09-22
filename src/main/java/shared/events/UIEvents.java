package shared.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UIEvents {
    private static final Logger log = LoggerFactory.getLogger(UIEvents.class);

    public record OpenMenu() {
        public OpenMenu {
            log.info("Main menu opened by the user.");
        }
    }

    public record OpenGame() {
        public OpenGame {
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
            log.info("Game will start over");
        }
    }
    public record RepaintGamePanelEvent() { }
}
