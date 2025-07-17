package view;

import javax.swing.*;

public class Frame extends JFrame {
    private static final int length = 1000 ;
    private  static final int width = 700 ;


    public  static int get_Length() {
        return length;
    }

    public static int get_Width() {
        return width;
    }

    public Frame () {
        super();
        insilize();

    }

    private void insilize() {
        setSize(  length , width);
        setUndecorated(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
