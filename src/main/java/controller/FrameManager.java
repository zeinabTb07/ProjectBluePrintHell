package controller;


import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import events.UIEvents.*;
import model.GameState;
import model.constants.Constants;
import view.ui.*;

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
        setupEventListeners();
    }

    private void setupEventListeners() {
        EventBus.subscribe(OpenSettingsEvent.class, e -> {settingDialog.setVisible(true);});
        EventBus.subscribe(OpenShopEvent.class, e -> {shop.setVisible(true);});

        EventBus.subscribe(GameEvents.CheckGameEndEvent.class, e -> {
            String[] options = {"Back to Menu" , "Go To Next Level"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    e.b() ? "You win!" : "You lost!",
                    "Game Finished",
                    JOptionPane.DEFAULT_OPTION,
                    e.b() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                EventBus.publish(new OpenMenuEvent());
            }
            if(choice == 1){
                int n = gameState.getGameLevel().getNumber();
                n++;
                if(n<Constants.levels.size()){
                    EventBus.publish(new GameEvents.GoToLevel(n));
                }  else EventBus.publish(new OpenMenuEvent());
            }

        });
    }
    public void goToMenu() {
        switchPanel(menuPanel);
        musicPlayer.stopBackgroundMusic();
    }

    public void goToGame() {
        switchPanel(gamePanel);
        musicPlayer.playBackgroundMusic();
    }

    private void switchPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }
    public GamePanel getGamePanel() {
        return gamePanel;
    }

}