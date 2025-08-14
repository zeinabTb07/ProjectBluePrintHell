package view.ui;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.constants.Constants;

import javax.swing.*;
import java.awt.*;


public class InfoBar extends JLabel {
    private JLabel coin;
    private JProgressBar wireRemain;
    private JProgressBar packetLoss;
    private JSlider temporalProgress;
    private final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 20);

    public InfoBar() {
        super();
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

        coin = new JLabel("Coins: 0");
        coin.setForeground(Color.lightGray);
        coin.setFont(DEFAULT_FONT);
        add(coin);
        add(Box.createHorizontalStrut(20));

        wireRemain = new JProgressBar(0, 100);
        wireRemain.setString("Wire Length");
        wireRemain.setValue(wireRemain.getMaximum());
        wireRemain.setStringPainted(true);
        wireRemain.setFont(DEFAULT_FONT);
        wireRemain.setBackground(Color.red.brighter());
        wireRemain.setForeground(Color.lightGray);
        add(wireRemain);
        add(Box.createHorizontalStrut(20));

        packetLoss = new JProgressBar(0, 100);
        packetLoss.setString("Packet Loss");
        packetLoss.setValue(100);
        packetLoss.setBackground(Color.red.brighter());
        packetLoss.setForeground(Color.lightGray);
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
                .withAction(e -> EventBus.publish(new GameEvents.StartGameEvent()))
                .build();
        EventBus.subscribe(GameEvents.CheckConnectivity.class , e->{run.setEnabled(e.b());});
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
    }

    public void addWire(int d) {
        wireRemain.setValue(wireRemain.getValue() - d);
    }

    public void removeWire(int d) {
        wireRemain.setValue(wireRemain.getValue() + d);
    }

    public void addCoin(int n) {
        String currentText = coin.getText();
        int currentCoins = Integer.parseInt(currentText.replace("Coins: ", ""));
        coin.setText("Coins: " + (currentCoins + n));
    }
    public void purchaseCoin(int n){
        addCoin(-n);
    }
}