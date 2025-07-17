package view;

import controller.GameMouseListener;
import controller.GameState;
import controller.Stage1;
import model.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private JLayeredPane layeredPane ;
    private GameStateRender gameStateRender ;
    private Connection connection;
    private GameState gameState;
    private final GameMouseListener gameMouseListener;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        drawBackGround(g2d);

        gameStateRender.updateGame(g2d);
        gameMouseListener.paintConnections(g2d);
    }
    private void drawBackGround(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Frame.get_Length(), Frame.get_Width());
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(new Color(255, 255, 255, 30));
        int cellSize = 50 ;
        for (int y = 0; y <= Frame.get_Width(); y += cellSize) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        for (int x = 0; x <= Frame.get_Length() ; x += cellSize) {
            g2d.drawLine(x, 0, x, getHeight());
        }
    }

    public GamePanel(){
        super();
        JLabel infoBar = new InfoBar();
        add(infoBar);
        gameState = GameState.getInstance();

        gameStateRender = new GameStateRender(gameState);
        gameMouseListener = new GameMouseListener(this);
        setLayout(null);
        setBounds(0 , 0 , Frame.get_Length() , Frame.get_Width());
        addMouseListener(gameMouseListener);
        addMouseMotionListener(gameMouseListener);



    }

    private void addMouseListener(GameMouseListener gameMouseListener) {
        super.addMouseListener(gameMouseListener);
        super.addMouseMotionListener(gameMouseListener);
    }

    public GameState getGameState() {
        return gameState;
    }
}
