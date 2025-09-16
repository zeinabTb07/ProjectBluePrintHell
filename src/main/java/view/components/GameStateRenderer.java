package view.components;

import model.Level;
import model.constants.Constants;
import model.objects.other.Collision;
import model.GameState;
import model.objects.other.Connection;
import model.objects.packets.Packet;
import model.objects.systems.NetworkSystem;

import java.awt.*;
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
            g.draw(collision.getShape());
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