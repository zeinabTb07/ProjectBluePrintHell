package client.controller.mouse;

import shared.events.EventBus;
import shared.events.ShopEvents;
import shared.events.UIEvents;
import shared.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMouseListener extends MouseAdapter {
    private static final Logger log = LoggerFactory.getLogger(GameMouseListener.class);
    private MouseMode currentMode;
    private final MouseMode defaultMode;
    private final MouseMode relocateMode;
    private final MouseMode helperPointMode;
    private final PointPickerMod pointPickerMod;

    public GameMouseListener(GameState gameState) {
        this.defaultMode = new DefaultConnectionMode(gameState);
        this.relocateMode = new RelocateSystemMode(gameState);
        this.helperPointMode = new HelperPointMode(gameState);
        this.pointPickerMod = new PointPickerMod();
        this.currentMode = defaultMode;


//        EventBus.subscribe(ShopEvents.PowerUpEvent.class, event -> {
//            ShopEvents.PowerUpType type = event.powerUpType();
//            if (type == ShopEvents.PowerUpType.RELOCATE_SYSTEM) {
//                currentMode = relocateMode;
//                log.info("Switched to RelocateSystemMode.");
//            } else if (type == ShopEvents.PowerUpType.HELPER_POINT) {
//                currentMode = helperPointMode;
//                log.info("Switched to HelperPointMode.");
//            } else if (type== ShopEvents.PowerUpType.ALIGN_CENTER||type== ShopEvents.PowerUpType.ZERO_ACCELERATION) {
//               pointPickerMod.event = type;
//                currentMode = pointPickerMod;
//                log.info("Switched to PointPickerMode.");
//            }
//        });
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentMode.mousePressed(e);
     //   EventBus.publish(new UIEvents.RepaintGamePanelEvent());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentMode.mouseDragged(e);
       // EventBus.publish(new UIEvents.RepaintGamePanelEvent());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentMode.mouseReleased(e);
        if (currentMode != defaultMode) {
            currentMode = defaultMode;
            log.info("Reverted to DefaultConnectionMode.");
        }
       // EventBus.publish(new UIEvents.RepaintGamePanelEvent());
    }

    public void paintLine(Graphics2D g) {
        currentMode.paintLine(g);
    }

}