package view.ui;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.constants.Constants;

import javax.swing.*;
import java.awt.*;



import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.GameState;
import model.constants.Constants;

import javax.swing.*;
import java.awt.*;

public class InfoBar extends JLabel {
    private JLabel coin;
    private JProgressBar wireRemain;
    private JProgressBar packetLoss;
    private JSlider temporalProgress;
    private final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 20);
    private final GameState gameState;

    public InfoBar(GameState gameState) {
        super();
        this.gameState = gameState;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBounds(0, 0, Constants.FRAME_WIDTH, 60);
        setBackground(Color.white);
        setOpaque(false);

        add(Box.createHorizontalStrut(20));

        JButton back = new ButtonFactory.Builder()
                .withText("Back")
                .withFont(DEFAULT_FONT)
                .withSize(new Dimension(50, 30))
                .withAction(e -> EventBus.publish(new UIEvents.OpenMenuEvent()))
                .build();
        add(back);
        add(Box.createHorizontalStrut(20));

        coin = new JLabel("Coins: " + gameState.getCoin());
        coin.setForeground(Color.lightGray);
        coin.setFont(DEFAULT_FONT);
        add(coin);
        add(Box.createHorizontalStrut(20));

        wireRemain = new JProgressBar(0, 100);
        wireRemain.setString("Wire Length");
        wireRemain.setValue(calculateWireRemain());
        wireRemain.setStringPainted(true);
        wireRemain.setFont(DEFAULT_FONT);
        wireRemain.setBackground(Color.red.brighter());
        wireRemain.setForeground(Color.lightGray);
        add(wireRemain);
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

        temporalProgress = new JSlider(0, 100);
        temporalProgress.setFont(DEFAULT_FONT);
        temporalProgress.setEnabled(false);
        add(temporalProgress);
        add(Box.createHorizontalStrut(20));

        JButton run = new ButtonFactory.Builder()
                .withText("Run")
                .withFont(DEFAULT_FONT)
                .withSize(new Dimension(50, 30))
                .withAction(e -> {
                    EventBus.publish(new GameEvents.StartGameEvent());
                })
                .build();
        run.setEnabled(false);
        EventBus.subscribe(GameEvents.CheckConnectivity.class, e -> run.setEnabled(e.b()));
        run.addActionListener(e -> run.setEnabled(false));
        add(run);
        add(Box.createHorizontalStrut(20));

        JButton shop = new ButtonFactory.Builder()
                .withText("Shop")
                .withFont(DEFAULT_FONT)
                .withSize(new Dimension(50, 30))
                .withAction(e -> EventBus.publish(new UIEvents.OpenShopEvent()))
                .build();
        add(shop);
        add(Box.createHorizontalStrut(20));

        setupGameStateListeners();
    }

    private void setupGameStateListeners() {
        EventBus.subscribe(GameEvents.CoinGeneratedEvent.class, e -> updateCoinDisplay());
        EventBus.subscribe(GameEvents.PacketLostEvent.class, e -> updatePacketLoss());
        EventBus.subscribe(GameEvents.ConnectionEvent.class, e -> updateWireRemain());

    }

    private void updateCoinDisplay() {
        coin.setText("Coins: " + gameState.getCoin());
    }

    private void updateWireRemain() {
        wireRemain.setValue(calculateWireRemain());
    }

    private int calculateWireRemain() {
        double maxWireLength = gameState.getGameLevel().getWireLength();
        double usedLength = gameState.getCurrentLengthUsed();
        return (int) ((maxWireLength - usedLength) / maxWireLength * 100);
    }

    private void updatePacketLoss() {
        packetLoss.setValue((int) gameState.getPacketLossPercentage());
    }

    public void addWire(int d) {
        gameState.setCurrentLengthUsed(gameState.getCurrentLengthUsed() + d);
        updateWireRemain();
    }

    public void removeWire(int d) {
        gameState.setCurrentLengthUsed(gameState.getCurrentLengthUsed() - d);
        updateWireRemain();
    }

    public void addCoin(int n) {
        gameState.addCoin(n);
        updateCoinDisplay();
    }

    public void purchaseCoin(int n) {
        gameState.addCoin(-n);
        updateCoinDisplay();
    }
}