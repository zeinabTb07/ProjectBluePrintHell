package client.controller;

import shared.events.UIEvents.*;
import shared.model.GameState;
import client.Constants;
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

    public void shopEndOptions(boolean b){
        String[] options ;
        if(b){
            options = new String[] {"Back to Menu" , "Go To Next Level"};
        } else options = new String[]{"Back to Menu", "Start Over"};
        int choice = JOptionPane.showOptionDialog(
                null,
                b ? "You win!" : "You lost!",
                "Game Finished",
                JOptionPane.DEFAULT_OPTION,
                b ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
           // EventBus.publish(new OpenMenu());
        }
        if(choice == 1){
            if(b){
                int n = gameState.getGameLevel().getNumber();
                n++;
                if(n<Constants.levels.size()){
                    //EventBus.publish(new GameEvents.GoToLevel(n));
                }  //else EventBus.publish(new OpenMenu());
            } //else  EventBus.publish(new UIEvents.Replay());
        }
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
}