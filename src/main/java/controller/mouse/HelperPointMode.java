package controller.mouse;

import controller.NetworkConnectivityChecker;
import events.EventBus;
import events.UIEvents;
import model.GameState;
import model.constants.GeometryUtils;
import model.objects.other.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.AbstractMap;
import java.util.Optional;

public class HelperPointMode implements MouseMode {
    private static final Logger log = LoggerFactory.getLogger(HelperPointMode.class);
    private static final double HELPER_CLICK_THRESHOLD = 5.0;

    private final GameState gameState;
    private Connection helperConnection;
    private Point2D draggingHelper;
    private Point dragStartPoint;
    private boolean addHelperActive;

    public HelperPointMode(GameState gameState) {
        this.gameState = gameState;
        this.addHelperActive = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Point clickPoint = e.getPoint();

            Optional<AbstractMap.SimpleEntry<Connection, Point2D>> helperOpt = findHelperAtPoint(clickPoint);
            if (helperOpt.isPresent()) {
                AbstractMap.SimpleEntry<Connection, Point2D> helperPair = helperOpt.get();
                helperConnection = helperPair.getKey();
                draggingHelper = helperPair.getValue();
                dragStartPoint = clickPoint;
                log.info("Starting drag on helper point at {}", draggingHelper);
                return;
            }


            if (addHelperActive) {
                Optional<Connection> connOpt = findConnectionLineAtPoint(clickPoint);
                if (connOpt.isPresent()) {
                    Connection conn = connOpt.get();
                    double newLength = conn.getLength() + GeometryUtils.calcPathLength((Path2D) conn.getShape());
                    if (gameState.getCurrentLengthUsed() + newLength <= gameState.getGameLevel().getWireLength()) {
                        conn.addHelperPoint(clickPoint);
                        gameState.setCurrentLengthUsed(gameState.getCurrentLengthUsed() + newLength);
                        addHelperActive = false; // Consume power-up
                        log.info("Added helper point to connection {} at {}", conn.getId(), clickPoint);
                    } else {
                        EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/error.wav"));
                        log.warn("Cannot add helper point: exceeds wire length limit.");
                    }
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (draggingHelper != null) {
            int dx = e.getX() - dragStartPoint.x;
            int dy = e.getY() - dragStartPoint.y;
            double newX = draggingHelper.getX() + dx;
            double newY = draggingHelper.getY() + dy;
            draggingHelper.setLocation(newX, newY);
            double oldLength = helperConnection.getLength();
            helperConnection.update();
            double newLength = helperConnection.getLength();
            gameState.setCurrentLengthUsed(gameState.getCurrentLengthUsed() + (newLength - oldLength));
            dragStartPoint = e.getPoint();
            log.debug("Dragging helper point to: ({}, {})", newX, newY);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (draggingHelper != null) {
            draggingHelper = null;
            helperConnection = null;
            log.info("Helper point drag completed.");
        }
    }

    @Override
    public void paintLine(Graphics2D g) {
        // No line to paint in helper point mode
    }

    private Optional<Connection> findConnectionLineAtPoint(Point point) {
        return gameState.getConnections().stream()
                .filter(conn -> conn.getShape().contains(point))
                .findFirst();
    }

    private Optional<AbstractMap.SimpleEntry<Connection, Point2D>> findHelperAtPoint(Point point) {
        for (Connection conn : gameState.getConnections()) {
            for (Point2D helper : conn.getHelperPoints()) {
                if (point.distance(helper.getX(), helper.getY()) <= HELPER_CLICK_THRESHOLD) {
                    return Optional.of(new AbstractMap.SimpleEntry<>(conn, helper));
                }
            }
        }
        return Optional.empty();
    }
}