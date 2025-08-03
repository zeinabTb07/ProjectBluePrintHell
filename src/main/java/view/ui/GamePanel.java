package view.ui;

import controller.GameMouseLisntener;
import model.GameState;
import model.Setting;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    private JLayeredPane layeredPane ;
    private GameState gameState;
    private GameMouseLisntener gameMouseListener;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setBackground(Color.black);
//        gameStateRender.updateGame(g2d);
//        gameMouseListener.paintConnections(g2d);
    }

    public GamePanel(){
        super();
        JLabel infoBar = new InfoBar();
        add(infoBar);
        setBackground(Color. BLACK);
        this.gameState = gameState;
        // gameMouseListener = new GameMouseListener(this);
        setLayout(null);
        setBounds(0 , 0 , Setting.FRAME_WIDTH, Setting.Frame_HEIGHT);
        super.addMouseListener(gameMouseListener);
   //     super.addMouseMotionListener(gameMouseListener);



    }

    public GameState getGameState() {
        return gameState;
    }
}
