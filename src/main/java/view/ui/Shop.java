package view.ui;

import events.EventBus;
import events.GameEvents;
import model.objects.packets.Connection;
import model.objects.systems.InputPort;

import javax.swing.*;
import java.awt.*;

public class Shop extends JDialog {
    public Shop() {
        super((JFrame)null, "Power-Up Shop", true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        addPowerUpItem(mainPanel, "O' Atar",
                "Disables Impact waves for 10 seconds (Cost: 3 coins)");
        addPowerUpItem(mainPanel, "O' Airyaman",
                "Disables packet collisions in the network for 5 seconds (Cost: 4 coins)");
        addPowerUpItem(mainPanel, "O' Anahita",
                "Reduces noise of all network packets to zero (Cost: 5 coins)");

        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }

    private void addPowerUpItem(JPanel parent, String name, String description) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JButton button = new JButton(name);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 30));
        button.addActionListener(e -> handlePurchase(name));

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        itemPanel.add(button, BorderLayout.WEST);
        itemPanel.add(descLabel, BorderLayout.CENTER);

        parent.add(itemPanel);
        parent.add(Box.createRigidArea(new Dimension(0, 5)));

    }

    private void handlePurchase(String powerUpName) {
        System.out.println("Purchased: " + powerUpName);
    }
}