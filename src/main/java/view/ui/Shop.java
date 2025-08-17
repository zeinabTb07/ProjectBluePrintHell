package view.ui;

import events.EventBus;
import events.GameEvents;
import events.ShopEvents;
import events.UIEvents;
import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Shop extends JDialog {

    public Shop() {
        super((JFrame) null, "Power-Up Shop");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (ShopEvents.PowerUpType powerUp : ShopEvents.PowerUpType.values()) {
            addPowerUpItem(mainPanel, powerUp);
        }

        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventBus.publish(new GameEvents.PauseGameEvent(false));
            }
        });
    }

    private void addPowerUpItem(JPanel parent, ShopEvents.PowerUpType powerUp) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton button = new JButton(powerUp.getName());
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 30));
        button.addActionListener(e -> handlePurchase(powerUp));
        JLabel descLabel = new JLabel(powerUp.getDescription());
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        itemPanel.add(button, BorderLayout.WEST);
        itemPanel.add(descLabel, BorderLayout.CENTER);
        parent.add(itemPanel);
        parent.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void handlePurchase(ShopEvents.PowerUpType powerUp) {
        EventBus.publish(new ShopEvents.PowerUpEvent(powerUp));
        EventBus.publish(new UIEvents.PlaySoundEvent("src/main/resources/buyitem.wav"));
    }
}