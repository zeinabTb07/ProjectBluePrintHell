package controller;

import model.GameState;
import model.constants.Constants;
import model.objects.packets.Connection;
import model.objects.systems.InputPort;
import model.objects.systems.OutputPort;
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
    private final JComponent drawingSurface;
    private final GameState gameState;
    private Line currentLine;

    public GameMouseListener(JComponent drawingSurface, GameState gameState) {
        this.drawingSurface = drawingSurface;
        this.gameState = gameState;
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
        drawingSurface.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentLine != null) {
            currentLine.setEnd(e.getPoint());
            drawingSurface.repaint();
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
                gameState.addConnection(connection);
                log.info("Connection created: " + connection.getId());
            }
        });

        clearDragState();
        drawingSurface.repaint();
    }

    public void paintConnections(Graphics2D g) {
        if (currentLine != null) {
            g.setColor(new Color(135, 206, 235));
            g.setStroke(new BasicStroke(3));
            g.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y);
        }
        boolean b = !gameState.getConnections().isEmpty();
        if(b){
            gameState.getConnections().get(0).update();
        }
    }

    // --------- Helper methods -----------

    private Optional<OutputPort> findSourcePort(Point point) {
        return gameState.getGameLevel().getSystems().stream()
                .flatMap(system -> system.getOutputPorts().values().stream())
                .flatMap(Collection::stream)
                .filter(port -> !port.isConnected())
                .filter(port -> port.getPoint().distance(point) < Constants.PORT_SIZE / 2)
                .findFirst();
    }

    private Optional<InputPort> findTargetPort(Point point) {
        return gameState.getGameLevel().getSystems().stream()
                .flatMap(system -> system.getInputPorts().values().stream())
                .flatMap(Collection::stream)
                .filter(port -> !port.isConnected())
                .filter(port -> port.getPoint().distance(point) < Constants.PORT_SIZE / 2)
                .findFirst();
    }


    private void removeConnectionAtPoint(Point point) {
        Iterator<Connection> iterator = gameState.getConnections().iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            if (connection.getSource().getPoint().distance(point) < Constants.PORT_SIZE / 2 ||
                    connection.getTarget().getPoint().distance(point) < Constants.PORT_SIZE / 2) {
                connection.disconnect();
                iterator.remove();
                log.info("Connection removed: " + connection.getId());
                break; // حذف یکی کافیه
            }
        }
    }

    private void clearDragState() {
        dragStartPoint = null;
        sourcePort = null;
        currentLine = null;
    }

    private static class Line {
        Point start, end;
        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }
        public void setEnd(Point end) {
            this.end = end;
        }
        public Point getEnd() {
            return end;
        }
    }
}
