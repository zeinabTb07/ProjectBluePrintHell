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
    private  LevelsDialog levelsDialog;
    private  Shop shop;
    private  MusicPlayer musicPlayer;
    public FrameManager(GameState gameState) {
        this.frame = new Frame();
        this.musicPlayer = new MusicPlayer();
        this.settingDialog = new SettingDialog();
        this.levelsDialog = new LevelsDialog(Constants.levels.size());
        this.gameState = gameState;
        this.shop = new Shop();
        setupEventListeners();
    }

    private void setupEventListeners() {
        EventBus.subscribe(OpenMenuEvent.class, e ->{
         goToMenu();
        });
        EventBus.subscribe(OpenGameEvent.class, e -> {
            goToGame();
            EventBus.publish(new GameEvents.PauseGameEvent(false));
        });
        EventBus.subscribe(OpenSettingsEvent.class, e -> {settingDialog.setVisible(true);});
        EventBus.subscribe(OpenLevelsEvent.class, e -> levelsDialog.setVisible(true)
        );
        EventBus.subscribe(OpenShopEvent.class, e -> {shop.setVisible(true);
            EventBus.publish(new GameEvents.PauseGameEvent(true));});

        EventBus.subscribe(GameEvents.CheckGameEndEvent.class, e -> {
            String[] options = {"Back to Menu"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    e.b() ? "You win!" : "You lost!",
                    "Game Over",
                    JOptionPane.DEFAULT_OPTION,
                    e.b() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                EventBus.publish(new OpenMenuEvent());
            }
        });
    }
    private void goToMenu() {
        if (menuPanel == null) {
            menuPanel = new MenuPanel();
        }
        switchPanel(menuPanel);
        musicPlayer.stopBackgroundMusic();
    }

    private void goToGame() {
        if (gamePanel == null) {
            gamePanel = new GamePanel(gameState);
        }
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