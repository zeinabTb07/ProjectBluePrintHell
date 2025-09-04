package view.ui;

import controller.mouse.GameMouseListener;
import events.EventBus;
import events.UIEvents;
import model.GameState;
import model.constants.Constants;
import view.components.GameStateRenderer;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    private GameState gameState;
    private GameMouseListener gameMouseListener;
    private GameStateRenderer gameStateRenderer;
    private InfoBar infoBar ;

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
        EventBus.subscribe(UIEvents.RepaintGamePanelEvent.class , e->{repaint();});
    }
    public void reset(){
        infoBar.reset();
    }

    public GameState getGameState() {
        return gameState;
    }
}
