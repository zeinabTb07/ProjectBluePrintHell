package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class ButtonFactory {
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 80;

    public JButton createButton(String text, String iconPath, int x, int y , ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Monospaced", Font.BOLD, 40));
        button.setIcon(new ImageIcon(iconPath));
        button.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setBackground(Color.WHITE);
        button.addActionListener(action);
        return button;
    }
}