package view.ui;

import events.ShopEvents;
import utils.NetworkConnectivityChecker;
import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.constants.Constants;

import javax.swing.*;
import java.awt.*;


import model.GameState;


public class InfoBar extends JLabel {
    private  int framePassed;
    private JLabel coin;
    private JLabel time;
    private JProgressBar packetLoss;
    private final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 20);
    private final GameState gameState;
    private NetworkConnectivityChecker networkConnectivityChecker;

    public InfoBar(GameState gameState) {
        super();
        this.gameState = gameState;
        networkConnectivityChecker = new NetworkConnectivityChecker(gameState);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBounds(0, 0, Constants.FRAME_WIDTH, 60);
        setBackground(Color.white);
        setOpaque(false);

        add(Box.createHorizontalStrut(20));

        JButton back = new ButtonBuilder.Builder()
                .withText("Back")
                .withFont(DEFAULT_FONT)
                .withSize(new Dimension(50, 30))
                .withAction(e ->{EventBus.publish(new UIEvents.OpenMenu());})
                .build();
        add(back);
        add(Box.createHorizontalStrut(20));

        coin = new JLabel("Coins: " + gameState.getCoin());
        coin.setForeground(Color.lightGray);
        coin.setFont(DEFAULT_FONT);
        add(coin);
        add(Box.createHorizontalStrut(20));

        packetLoss = new JProgressBar(0, 100);
        packetLoss.setString("Packet Loss");
        packetLoss.setValue((int) gameState.getPacketLossPercentage());
        packetLoss.setBackground(Color.lightGray);
        packetLoss.setForeground(Color.RED);
        packetLoss.setStringPainted(true);
        packetLoss.setFont(DEFAULT_FONT);
        add(packetLoss);
        add(Box.createHorizontalStrut(20));

        time = new JLabel("Time Passed");
        time.setForeground(Color.lightGray);
        time.setFont(DEFAULT_FONT);
        add(time);
        add(Box.createHorizontalStrut(20));


        JButton run = new ButtonBuilder.Builder()
                .withText("Run")
                .withFont(DEFAULT_FONT)
                .withSize(new Dimension(50, 30))
                .withAction(e -> { if (networkConnectivityChecker.check()){
                    EventBus.publish(new GameEvents.StartGameEvent());
                } else EventBus.publish(new UIEvents.RepaintGamePanelEvent());
                })
                .build();
        add(run);
        add(Box.createHorizontalStrut(20));

        JButton shop = new ButtonBuilder.Builder()
                .withText("Shop")
                .withFont(DEFAULT_FONT)
                .withSize(new Dimension(50, 30))
                .withAction(e -> EventBus.publish(new UIEvents.OpenShop()))
                .build();
        add(shop);
        add(Box.createHorizontalStrut(20));

        setupGameStateListeners();
    }

    private void setupGameStateListeners() {
        EventBus.subscribe(GameEvents.CoinGeneratedEvent.class, e -> {
            gameState.setCoin(gameState.getCoin()+e.n());
            updateCoinDisplay();});
        EventBus.subscribe(GameEvents.PacketLostEvent.class, e -> updatePacketLoss());
        EventBus.subscribe(UIEvents.RepaintGamePanelEvent.class, e -> updateTime());
        EventBus.subscribe(ShopEvents.PowerUpEvent.class , e->{gameState.setCoin(gameState.getCoin()-e.powerUpType().getPrice());
            updateCoinDisplay();});
    }
    private void updateTime() {
        if(framePassed>5){
            time.setText("Time Passed "+ Math.round(gameState.getTimePassed()*10.0) / 10.0+" : "+ gameState.getGameLevel().getTime());
            framePassed = 0 ;
        } else framePassed++;
    }

     private void updateCoinDisplay() {
        coin.setText("Coins: " + gameState.getCoin());
    }

    public void reset(){
        updateCoinDisplay();
        updatePacketLoss();
        updateTime();
        packetLoss.setValue(0);
    }

    private void updatePacketLoss() {
        packetLoss.setValue((int) gameState.getPacketLossPercentage());
    }
}