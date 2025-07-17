package view;


import javax.swing.*;
import java.util.logging.Level;

public class FrameManager {
    private static GamePanel gamePanel ;
    private static MenuPanel menuPanel ;
    private static Setting settingPanel = new Setting();
    private static LevelChooser levelChooser = new LevelChooser();
    private static Shop shop = new Shop();
    private static final Frame frame = new Frame() ;
    public static final ButtonFactory buttonFactory = new ButtonFactory();
    public static final Music musicPlayer = new Music();

    public static MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public static void goToMenu(){
        if(menuPanel==null) menuPanel = new MenuPanel();
        musicPlayer.stopBackgroundMusic();
        go(menuPanel);
    }

    public static void OpenShop() {
        shop.setVisible(true);
    }

    public static void OpenLevels(){
        levelChooser.setVisible(true);
    }

    public static void goToGame(){
        if(gamePanel == null ) gamePanel = new GamePanel();
        else {
            musicPlayer.setVolume(settingPanel.getSoundValume());
        }
        musicPlayer.playBackgroundMusic();
        // TODO : add game state and thing like this
        go(gamePanel);
    }

    public static void OpenSetting(){
        settingPanel.setVisible(true);
    }
    private static void go(JPanel panel){
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static JFrame getFrame () {
        return frame;
    }

    public static JPanel getGamePanel(){
        return gamePanel;
    }

}
