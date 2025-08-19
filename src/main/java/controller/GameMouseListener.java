package controller;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.GameState;
import model.constants.Constants;
import model.constants.Line;
import model.objects.other.Connection;
import model.objects.systems.addon.InputPort;
import model.objects.systems.addon.OutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Optional;
import java.util.Collection;

public class GameMouseListener extends MouseAdapter {
    private static final Logger log = LoggerFactory.getLogger(GameMouseListener.class);

    private Point dragStartPoint;
    private OutputPort sourcePort;
    private final GameState gameState;
    private Line currentLine;

    private NetworkConnectivityChecker connectivityChecker;

    public GameMouseListener(GameState gameState) {
        this.gameState = gameState;
        this.connectivityChecker=new NetworkConnectivityChecker(gameState.getGameLevel().getSystems());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            findSourcePort(e.getPoint()).ifPresent(port -> {
                dragStartPoint = port.getPoint();
                sourcePort = port;
                currentLine = new Line(dragStartPoint, e.getPoint());
                log.info("Starting new connection from: " + dragStartPoint);
            });
        } else if (SwingUtilities.isRightMouseButton(e)) {
            removeConnectionAtPoint(e.getPoint());
        }
        EventBus.publish(new UIEvents.RepaintGamePanelEvent());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentLine != null) {
            currentLine.setEnd(e.getPoint());
            EventBus.publish(new UIEvents.RepaintGamePanelEvent());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentLine == null || sourcePort == null) return;

        Point releasePoint = currentLine.getEnd();
        findTargetPort(releasePoint).ifPresent(targetPort -> {
            if (!sourcePort.getParentSystem().equals(targetPort.getParentSystem()) &&
                    !targetPort.isConnected()) {

                Connection connection = new Connection(targetPort, sourcePort);
                if(gameState.getCurrentLengthUsed()+connection.getLength()<= gameState.getGameLevel().getWireLength()){
                    gameState.addConnection(connection);
                    EventBus.publish(new GameEvents.ConnectionEvent(connection.getLength()));
                    log.info("Connection created: " + connection.getId());
                    EventBus.publish(new GameEvents.CheckConnectivity(connectivityChecker.check()));
                    EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/connect.wav"));
                } else { connection.disconnect();
                    EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/error.wav"));
                }
            }
        });

        clearDragState();
        EventBus.publish(new UIEvents.RepaintGamePanelEvent());
    }

    public void paintLine(Graphics2D g) {
        if (currentLine != null) {
            g.setColor(Constants.Colors.LINE);
            g.setStroke(Constants.LINE_STROKE);
            g.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y);
        }
    }



    private Optional<OutputPort> findSourcePort(Point point) {
        return gameState.getGameLevel().getSystems().stream()
                .flatMap(system -> system.getOutputPorts().stream())
                .filter(port -> !port.isConnected())
                .filter(port -> port.getShape().contains(point))
                .findFirst();
    }

    private Optional<InputPort> findTargetPort(Point point) {
        return gameState.getGameLevel().getSystems().stream()
                .flatMap(system -> system.getInputPorts().stream())
                .filter(port -> !port.isConnected())
                .filter(port ->  port.getShape().contains(point))
                .findFirst();
    }


    private void removeConnectionAtPoint(Point point) {
        Iterator<Connection> iterator = gameState.getConnections().iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            if ( connection.getSource().getShape().contains(point) ||
                    connection.getTarget().getShape().contains(point)) {
                connection.disconnect();
                gameState.removeConnection(connection);
                EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/disconnect.wav"));
                EventBus.publish(new GameEvents.ConnectionEvent(-connection.getLength()));
                log.info("Connection removed: " + connection.getId());
                break;
            }
        }
    }

    public void setConnectivityChecker(NetworkConnectivityChecker connectivityChecker) {
        this.connectivityChecker = connectivityChecker;
    }

    private void clearDragState() {
        dragStartPoint = null;
        sourcePort = null;
        currentLine = null;
    }
}
