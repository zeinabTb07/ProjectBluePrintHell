package view.ui;


import model.Setting;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        super();
        insilize();
    }

    private void insilize() {
        setSize(Setting.FRAME_WIDTH,Setting.Frame_HEIGHT);
        setUndecorated(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
