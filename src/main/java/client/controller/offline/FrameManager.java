package client.controller.offline;

import shared.model.GameState;
import client.view.ui.*;

import javax.swing.*;

public class FrameManager {
    private final Frame frame;
    private GamePanel gamePanel;
    private GameState gameState;
    private MenuPanel menuPanel;
    private  SettingDialog settingDialog;
    private  Shop shop;
    private  MusicPlayer musicPlayer;
    public FrameManager(GameState gameState) {
        this.frame = new Frame();
        this.musicPlayer = new MusicPlayer();
        this.settingDialog = new SettingDialog();
        this.gameState = gameState;
        this.gamePanel = new GamePanel(gameState);
        this.menuPanel = new MenuPanel();
        this.shop = new Shop();
        switchPanel(menuPanel);
    }

    public void goToMenu() {
        switchPanel(menuPanel);
        musicPlayer.stopBackgroundMusic();
    }

    public void goToGame() {
        switchPanel(gamePanel);
        musicPlayer.playBackgroundMusic();
    }

    public void openSetting(){
        settingDialog.setVisible(true);
    }

    public void openShop(){
        shop.setVisible(true);
    }

    public void playSound(String path){
        musicPlayer.playSoundEffect(path);
    }

    private void switchPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void reset(){
        gamePanel.reset();
        gamePanel.repaint();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public SettingDialog getSettingDialog() {
        return settingDialog;
    }

    public Shop getShop() {
        return shop;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}