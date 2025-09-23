package client.view.ui;

import client.controller.offline.mouse.GameMouseListener;
import shared.model.GameState;
import client.Constants;
import client.view.components.GameStateRenderer;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    private final GameState gameState;
    private final GameMouseListener gameMouseListener;
    private final GameStateRenderer gameStateRenderer;
    private final InfoBar infoBar ;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setBackground(Color.black);
        gameStateRenderer.render(g2d, gameState);
        gameMouseListener.paintLine(g2d);
    }

    public GamePanel(GameState gameState){
        super();
        infoBar = new InfoBar(gameState);
        gameStateRenderer = new GameStateRenderer();
        add(infoBar);
        setBackground(Color. BLACK);
        this.gameState = gameState;
        gameMouseListener = new GameMouseListener(gameState);
        setLayout(null);
        setBounds(0 , 0 , Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        super.addMouseMotionListener(gameMouseListener);
        super.addMouseListener(gameMouseListener);
    }
    public void reset(){
        infoBar.reset();
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameMouseListener getGameMouseListener() {
        return gameMouseListener;
    }
}
