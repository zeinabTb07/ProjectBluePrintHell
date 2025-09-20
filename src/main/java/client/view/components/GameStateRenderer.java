package client.view.components;

import shared.model.levels.Level;
import client.Constants;
import shared.model.objects.other.Collision;
import shared.model.GameState;
import shared.model.objects.other.Connection;
import shared.model.objects.packets.Packet;
import shared.model.objects.systems.NetworkSystem;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GameStateRenderer {
    private final ConnectionRenderer connectionRenderer;
    private final PacketRenderer packetRenderer;
    private final SystemRenderer systemRenderer;

    public GameStateRenderer() {
        this.connectionRenderer = new ConnectionRenderer();
        this.packetRenderer = new PacketRenderer();
        this.systemRenderer = new SystemRenderer();
    }
    public void render(Graphics2D g , GameState gameState){
        g.setColor(Color.white);
        g.setStroke(Constants.LINE_STROKE);
        g.setFont(new Font("Press Start 2P", Font.PLAIN, (int)(20*Constants.SCALE)));
        Level level = gameState.getGameLevel();
        g.drawString("Level : "+level.getNumber() +level.getMessage(), 15, 70);

        for(Collision collision : new ArrayList<>(gameState.getCollisions())){
            g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.1f));
            Point p = collision.getPoint();
            double radius = collision.getRadius();
            g.draw(new  Ellipse2D.Double(p.x - radius/2, p.y - radius/2, radius, radius));
        }
        for(NetworkSystem system :new ArrayList<>( gameState.getNetworkSystems())){
            systemRenderer.render(g , system);
        }
        for (Connection connection : new ArrayList<>(gameState.getConnections())){
            connectionRenderer.render(g , connection);
        }
        for (Packet packet : new ArrayList<>(gameState.getPackets())){
            packetRenderer.render(g ,packet);
        }
    }
}