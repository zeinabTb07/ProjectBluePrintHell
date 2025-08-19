package view.components;

import model.objects.other.Collision;
import model.GameState;
import model.objects.other.Connection;
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
}