package controller.mouse;

import controller.NetworkConnectivityChecker;
import events.EventBus;
import events.GameEvents;
import model.GameState;
import model.constants.Vector2D;
import model.objects.systems.NetworkSystem;
import model.objects.systems.RooterSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Optional;

/**
 * Handles system relocation when RELOCATE_SYSTEM power-up is active.
 */
public class RelocateSystemMode implements MouseMode {
    private static final Logger log = LoggerFactory.getLogger(RelocateSystemMode.class);

    private final GameState gameState;
    private NetworkConnectivityChecker connectivityChecker;
    private NetworkSystem draggingSystem;
    private Point dragStartPoint;

    public RelocateSystemMode(GameState gameState, NetworkConnectivityChecker connectivityChecker) {
        this.gameState = gameState;
        this.connectivityChecker = connectivityChecker;
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
            EventBus.publish(new GameEvents.CheckConnectivity(connectivityChecker.check()));
        }
    }

    @Override
    public void paintLine(Graphics2D g) {
        // No line to paint in relocate mode
    }

    @Override
    public void setConnectivityChecker(NetworkConnectivityChecker checker) {
        this.connectivityChecker = checker;
    }

    private Optional<NetworkSystem> findSystemAtPoint(Point point) {
        return gameState.getGameLevel().getSystems().stream()
                .filter(system -> system.getShape().contains(point))
                .findFirst();
    }
}