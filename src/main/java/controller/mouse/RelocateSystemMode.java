package controller.mouse;

import model.GameState;
import utils.Vector2D;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class RelocateSystemMode implements MouseMode {
    private static final Logger log = LoggerFactory.getLogger(RelocateSystemMode.class);

    private final GameState gameState;
    private NetworkSystem draggingSystem;
    private Point dragStartPoint;

    public RelocateSystemMode(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Optional<NetworkSystem> systemOpt = findSystemAtPoint(e.getPoint());
            if (systemOpt.isPresent() && !(systemOpt.get() instanceof RooterSystem)) {
                draggingSystem = systemOpt.get();
                dragStartPoint = e.getPoint();
                log.info("Starting relocate drag on system at {}", draggingSystem.getPoint());
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (draggingSystem != null) {
            int dx = e.getX() - dragStartPoint.x;
            int dy = e.getY() - dragStartPoint.y;
            draggingSystem.moveInduced(new Vector2D(dx, dy));
            dragStartPoint = e.getPoint();

            log.debug("Dragging system to new position: {}", draggingSystem.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (draggingSystem != null) {
            draggingSystem = null;
            log.info("Relocate completed.");
        }
    }

    @Override
    public void paintLine(Graphics2D g) {

    }

    private Optional<NetworkSystem> findSystemAtPoint(Point point) {
        return gameState.getNetworkSystems().stream()
                .filter(system -> system.getShape().contains(point))
                .findFirst();
    }
}