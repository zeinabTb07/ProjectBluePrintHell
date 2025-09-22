package client.view.ui;


import client.Constants;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        super();
        insilize();
    }

    private void insilize() {
        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
