package client.controller.mouse;

import shared.events.EventBus;
import shared.events.GameEvents;
import shared.events.ShopEvents;
import shared.events.UIEvents;
import shared.model.GameState;
import client.Constants;
import shared.utils.math.Line;
import shared.model.objects.other.Connection;
import shared.model.objects.systems.addon.InputPort;
import shared.model.objects.systems.addon.OutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class DefaultConnectionMode implements MouseMode {
    private static final Logger log = LoggerFactory.getLogger(DefaultConnectionMode.class);

    private final GameState gameState;
    private Point dragStartPoint;
    private OutputPort sourcePort;
    private Line currentLine;

    private final ArrayList<Point> draggablePoints = new ArrayList<>();
    private Point selectedDraggablePoint = null;
    private Point dragOffset = null;

    public DefaultConnectionMode(GameState gameState) {
        this.gameState = gameState;
        EventBus.subscribe(GameEvents.SetPowerUpPoint.class , e->{if(e.type()== ShopEvents.PowerUpType.HELPER_POINT) draggablePoints.add(e.point());});
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Point clickPoint = e.getPoint();
            findSourcePort(clickPoint).ifPresent(port -> {
                dragStartPoint = port.getPoint();
                sourcePort = port;
                currentLine = new Line(dragStartPoint, clickPoint);
                log.info("Starting new connection from: {}", dragStartPoint);
            });

            if (sourcePort == null) {
                // Check for draggable points after port checking
                for (Point p : draggablePoints) {
                    if (isNear(clickPoint, p, 10)) { // Tolerance of 10 pixels
                        selectedDraggablePoint = p;
                        dragOffset = new Point(clickPoint.x - p.x, clickPoint.y - p.y);
                        log.info("Selected draggable point for moving: {}", p);
                        return;
                    }
                }
            }

        } else if (SwingUtilities.isRightMouseButton(e)) {
            removeConnectionAtPoint(e.getPoint());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedDraggablePoint != null) {
            Point newPos = e.getPoint();
            selectedDraggablePoint.x = newPos.x - dragOffset.x;
            selectedDraggablePoint.y = newPos.y - dragOffset.y;
            log.debug("Moving draggable point to: {}", selectedDraggablePoint);
        } else if (currentLine != null) {
            currentLine.setEnd(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedDraggablePoint != null) {
            selectedDraggablePoint = null;
            dragOffset = null;
            log.info("Finished moving draggable point.");
            return;
        }

        if (currentLine == null || sourcePort == null) {
            clearDragState();
            return;
        }

        Point releasePoint = currentLine.getEnd();
        findTargetPort(releasePoint).ifPresent(targetPort -> {
            if (!sourcePort.getParentSystem().equals(targetPort.getParentSystem()) &&
                    !targetPort.isConnected()) {
                Connection connection = new Connection(targetPort, sourcePort);
                gameState.addConnection(connection);
                log.info("Connection created: {}", connection.getId());
                EventBus.publish(new UIEvents.PlaySound("src/main/resources/connect.wav"));
            }
        });
        clearDragState();
    }

    @Override
    public void paintLine(Graphics2D g) {
        if (currentLine != null) {
            g.setColor(Constants.Colors.LINE);
            g.setStroke(Constants.LINE_STROKE);
            g.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y);
        }
    }

    private Optional<OutputPort> findSourcePort(Point point) {
        return gameState.getNetworkSystems().stream()
                .flatMap(system -> system.getOutputPorts().stream())
                .filter(port -> !port.isConnected())
                .filter(port -> isNearEnough(port.getPoint() , point))
                .findFirst();
    }

    private Optional<InputPort> findTargetPort(Point point) {
        return gameState.getNetworkSystems().stream()
                .flatMap(system -> system.getInputPorts().stream())
                .filter(port -> !port.isConnected())
                .filter(port -> isNearEnough(port.getPoint() , point))
                .findFirst();
    }

    private void removeConnectionAtPoint(Point point) {
        Iterator<Connection> iterator = gameState.getConnections().iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            if (isNearEnough(connection.getTarget().getPoint() , point)||
                    isNearEnough(connection.getSource().getPoint() , point)) {
                if(!connection.isFreeze()){
                    connection.disconnect();
                    gameState.removeConnection(connection);
                    EventBus.publish(new UIEvents.PlaySound("src/main/resources/disconnect.wav"));
                    log.info("Connection removed: {}", connection.getId());
                    break;
                }
            }
        }
    }

    private void clearDragState() {
        dragStartPoint = null;
        sourcePort = null;
        currentLine = null;
    }

    private boolean isNearEnough(Point p1 , Point p2){
        return p1.distance(p2)<Constants.PORT_SIZE;
    }

    private boolean isNear(Point a, Point b, int tolerance) {
        return Math.hypot(a.x - b.x, a.y - b.y) < tolerance;
    }
}