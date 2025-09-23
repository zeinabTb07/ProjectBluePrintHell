package client.controller.offline.mouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.events.GameEvents;
import shared.events.ShopEvents;
import shared.events.UIEvents;
import shared.model.GameState;
import shared.model.objects.other.Connection;
import shared.utils.math.GeometryUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.AbstractMap;
import java.util.Optional;

public class HelperPointMode implements MouseMode {
    private static final Logger log = LoggerFactory.getLogger(HelperPointMode.class);
    private static final double HELPER_CLICK_THRESHOLD = 5.0;
    private static final double LINE_CLICK_THRESHOLD =5.0;

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
                    if(conn.getHelperPoints().size()<3){
                        conn.addHelperPoint(clickPoint);
                        gameState.getPublisher().publish(new  GameEvents.SetPowerUpPoint(ShopEvents.PowerUpType.HELPER_POINT , clickPoint));
                    } else{
                        gameState.getPublisher().publish(new UIEvents.PlaySound("src/main/resources/error.wav"));
                        return;
                    }

                    log.info("Added helper point to connection {} at {}", conn.getId(), clickPoint);
                    draggingHelper = clickPoint;
                    helperConnection = conn;
                    dragStartPoint = clickPoint;
                    addHelperActive = false;

                } else {
                    log.debug("No connection found at point {}", clickPoint);
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

        addHelperActive = true;
    }

    @Override
    public void paintLine(Graphics2D g) {

    }

    private Optional<Connection> findConnectionLineAtPoint(Point point) {
        for (Connection conn : gameState.getConnections()) {
            Path2D path = (Path2D) GeometryUtils.getPath2d(conn);
            double[] coords = new double[6];
            Point2D prev = null;
            for (var it = path.getPathIterator(null, 0.1); !it.isDone(); it.next()) {
                it.currentSegment(coords);
                Point2D curr = new Point2D.Double(coords[0], coords[1]);
                if (prev != null) {

                    double distance = Line2D.ptSegDist(prev.getX(), prev.getY(), curr.getX(), curr.getY(), point.getX(), point.getY());
                    if (distance <= LINE_CLICK_THRESHOLD) {
                        return Optional.of(conn);
                    }
                }
                prev = curr;
            }
        }
        return Optional.empty();
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