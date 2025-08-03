package events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UIEvents {
    private static final Logger log = LoggerFactory.getLogger(UIEvents.class);
    public record OpenMenuEvent() {
        public OpenMenuEvent {
            log.info("UI Event: [MenuPanel] Action=Open");
        }
    }
    public record OpenGameEvent() {
        public OpenGameEvent {
            log.info("UI Event: [GamePanel] Action=Open");
        }
    }
    public record OpenSettingsEvent() {
        public OpenSettingsEvent {
            log.info("UI Event: [SettingDialog] Action=Open");
        }
    }
    public record OpenLevelsEvent() {
        public OpenLevelsEvent {
            log.info("UI Event: [LevelsDialog] Action=Open");
        }
    }
    public record OpenShopEvent() {
        public OpenShopEvent {
            log.info("UI Event: [Shop] Action=Open");
        }
    }
    public record VolumeChangeEvent(int volume) {
        public VolumeChangeEvent {
        log.info("UI Event: [Volume] Action=Change");
    }}
}
