package view.components;

import model.Collision;
import model.GameState;
import model.constants.Constants;
import model.objects.packets.Connection;
import model.objects.packets.Packet;
import model.objects.systems.NetworkSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GameStateRenderer {
    private static final Logger log = LoggerFactory.getLogger(GameStateRenderer.class);

    private final ConnectionRenderer connectionRenderer;
    private final PacketRenderer packetRenderer;
    private final SystemRenderer systemRenderer;

    public GameStateRenderer() {
        this.connectionRenderer = new ConnectionRenderer();
        this.packetRenderer = new PacketRenderer();
        this.systemRenderer = new SystemRenderer();

        log.debug("GameStateRenderer initialized");
    }
    public void render(Graphics2D g , GameState gameState){
        for(Collision collision : gameState.getCollisions()){
            g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.1f));
            g.setStroke(new BasicStroke(2));
            double r = collision.getRadius();
            Point p = collision.getPoint();
            g.draw(new Ellipse2D.Double(p.x - r/2, p.y - r/2, r, r));
        }
        for(NetworkSystem system : gameState.getGameLevel().getSystems()){
            systemRenderer.render(g , system);
        }
        for (Connection connection : gameState.getConnections()){
            connectionRenderer.render(g , connection);
        }
        for (Packet packet : gameState.getPackets()){
            packetRenderer.render(g ,packet);
        }
    }
//
//    public void render(Graphics2D g2d, GameState gameState) {
//        if (gameState == null) {
//            log.error("GameState is null!");
//        }
//        if (g2d == null) {
//            log.error("Graphics2D is null!");
//            return;
//        }
//
//        try {
//            renderSystems(g2d, gameState);
//            renderConnections(g2d, gameState);
//            renderPackets(g2d, gameState);
//        } catch (Exception e) {
//            log.error("Unexpected error in render method", e);
//        }
//    }
//
//    private void renderSystems(Graphics2D g2d, GameState gameState) {
//        if (gameState.getGameLevel() == null) {
//            log.warn("GameLevel is null in GameState!");
//            return;
//        }
//
//        for (NetworkSystem system : gameState.getGameLevel().getSystems()) {
//            if (system == null) {
//                log.warn("Found null NetworkSystem in GameLevel!");
//                continue;
//            }
//            try {
//                systemRenderer.render(g2d, system);
//            } catch (Exception e) {
//                log.error("Failed to render system: {}", system.getId(), e);
//            }
//        }
//    }
//
//    private void renderConnections(Graphics2D g2d, GameState gameState) {
//        if (gameState.getConnections() == null) {
//            log.warn("Connections list is null in GameState!");
//            return;
//        }
//
//        for (Connection connection : gameState.getConnections()) {
//            if (connection == null) {
//                log.warn("Found null Connection in GameState!");
//                continue;
//            }
//            try {
//                connectionRenderer.render(g2d, connection);
//            } catch (Exception e) {
//                log.error("Failed to render connection: {}", connection.getId(), e);
//            }
//        }
//    }
//
//    private void renderPackets(Graphics2D g2d, GameState gameState) {
//        if (gameState.getPackets() == null) {
//            log.warn("Packets list is null in GameState!");
//            return;
//        }
//
//        for (Packet packet : gameState.getPackets()) {
//            if (packet == null) {
//                log.warn("Found null Packet in GameState!");
//                continue;
//            }
//            try {
//                packetRenderer.render(g2d, packet);
//            } catch (Exception e) {
//                log.error("Failed to render packet: {}", packet.getId(), e);
//            }
//        }
//    }
}