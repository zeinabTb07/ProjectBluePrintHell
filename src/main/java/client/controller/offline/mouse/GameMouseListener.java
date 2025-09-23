package client.controller.offline.mouse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.events.ShopEvents;
import shared.events.UIEvents;
import shared.model.GameState;

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
    private final GameState gameState ;
    public GameMouseListener(GameState gameState) {
       this.gameState = gameState;
        this.defaultMode = new DefaultConnectionMode(gameState);
        this.relocateMode = new RelocateSystemMode(gameState);
        this.helperPointMode = new HelperPointMode(gameState);
        this.pointPickerMod = new PointPickerMod(gameState.getPublisher());
        this.currentMode = defaultMode;
    }
    public void switchedPointPicker(ShopEvents.PowerUpType type){
        pointPickerMod.event = type;
        currentMode = pointPickerMod;
        log.info("Switched to PointPickerMode.");
    }
    public void switchToRelocateSystem(){
        currentMode = relocateMode;
        log.info("Switched to RelocateSystemMode.");
    }
    public void switchToHelperPoint(){
        currentMode = helperPointMode;
        log.info("Switched to HelperPointMode.");
    }
    public void addDraggablePoint(Point point){
        ((DefaultConnectionMode)defaultMode).addDraggablePoint(point);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        currentMode.mousePressed(e);
        gameState.getPublisher().publish(new UIEvents.RepaintGamePanelEvent());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentMode.mouseDragged(e);
        gameState.getPublisher().publish(new UIEvents.RepaintGamePanelEvent());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentMode.mouseReleased(e);
        if (currentMode != defaultMode) {
            currentMode = defaultMode;
            log.info("Reverted to DefaultConnectionMode.");
        }
        gameState.getPublisher().publish(new UIEvents.RepaintGamePanelEvent());
    }

    public void paintLine(Graphics2D g) {
        currentMode.paintLine(g);
    }

}