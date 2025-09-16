package controller.mouse;

import events.EventBus;
import events.UIEvents;
import model.GameState;
import model.constants.Constants;
import utils.Line;
import model.objects.other.Connection;
import model.objects.systems.addon.InputPort;
import model.objects.systems.addon.OutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Optional;

public class DefaultConnectionMode implements MouseMode {
    private static final Logger log = LoggerFactory.getLogger(DefaultConnectionMode.class);

    private final GameState gameState;
    private Point dragStartPoint;
    private OutputPort sourcePort;
    private Line currentLine;

    public DefaultConnectionMode(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            findSourcePort(e.getPoint()).ifPresent(port -> {
                dragStartPoint = port.getPoint();
                sourcePort = port;
                currentLine = new Line(dragStartPoint, e.getPoint());
                log.info("Starting new connection from: {}", dragStartPoint);
            });

        } else if (SwingUtilities.isRightMouseButton(e)) {
            removeConnectionAtPoint(e.getPoint());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentLine != null) {
            currentLine.setEnd(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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
                .filter(port -> port.getShape().contains(point))
                .findFirst();
    }

    private Optional<InputPort> findTargetPort(Point point) {
        return gameState.getNetworkSystems().stream()
                .flatMap(system -> system.getInputPorts().stream())
                .filter(port -> !port.isConnected())
                .filter(port -> port.getShape().contains(point))
                .findFirst();
    }

    private void removeConnectionAtPoint(Point point) {
        Iterator<Connection> iterator = gameState.getConnections().iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            if (connection.getSource().getShape().contains(point) ||
                    connection.getTarget().getShape().contains(point)) {
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
}