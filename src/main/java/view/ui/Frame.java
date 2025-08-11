package view.ui;


import model.constants.Constants;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        super();
        insilize();
    }

    private void insilize() {
        setSize(Constants.FRAME_WIDTH, Constants.Frame_HEIGHT);
        setUndecorated(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
