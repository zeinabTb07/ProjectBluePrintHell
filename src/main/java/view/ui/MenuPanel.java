package view.ui;



import events.EventBus;
import events.UIEvents.*;
import model.constants.Constants;

import javax.swing.*;
import java.awt.*;


public class MenuPanel extends JPanel {
    private Image backgroundImage;

    public MenuPanel() {
        super();
        insilize();
    }
    private void insilize() {
        setLayout(null);
        setSize(Constants.FRAME_WIDTH, Constants.Frame_HEIGHT);

        try {
            backgroundImage = new ImageIcon("src/main/resources/edited_background.jpg").getImage();
        } catch (Exception e) {
        }
        repaint();

        JLabel tittle = new JLabel();
        tittle.setLayout(new FlowLayout());
        tittle.setOpaque(false);
        tittle.setBounds(100 , 100 , 800 , 150);

        JLabel blue = new JLabel();
        blue.setText("Blue");
        blue.setFont(new Font("Chiller", Font.BOLD, 120));
        blue.setForeground(Color.BLUE.darker());
        blue.setOpaque(false);
        tittle.add(blue , Component.LEFT_ALIGNMENT);

        JLabel print = new JLabel();
        print.setText("Print");
        print.setFont(new Font("Ravie", Font.BOLD, 100));
        print.setForeground(Color.GRAY);
        print.setOpaque(false);
        tittle.add(print , Component.CENTER_ALIGNMENT);

        JLabel hell = new JLabel();
        hell.setText("Hell");
        hell.setFont(new Font("Blackadder ITC", Font.BOLD, 120));
        hell.setForeground(Color.RED.darker());
        hell.setOpaque(false);
        tittle.add(hell , Component.RIGHT_ALIGNMENT);

        add(tittle);


        JButton exit = new  ButtonFactory.Builder()
                .withText("Exit")
                .atPosition(new Point(150 , 500))
                .withAction(e -> {System.exit(0);})
                .build();

        add(exit);


        JButton levels =  new  ButtonFactory.Builder()
                .withText("Replay")
                .atPosition(new Point(550 , 500))
                .withAction( e -> {
                    EventBus.publish(new ReplayEvent());
                })
                .build();

        add(levels);

        JButton play  =  new  ButtonFactory.Builder()
                .withText("Continue")
                .atPosition(new Point(550 , 350))
                .withAction(  e -> {
                    EventBus.publish(new OpenGameEvent());
                })
                .build();
        add(play);


        JButton setting =  new  ButtonFactory.Builder()
                .withText("Setting")
                .atPosition(new Point(150 , 350))
                .withAction(  e -> {
                    EventBus.publish(new OpenSettingsEvent());})
                .build();

        add(setting);

    }
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g ;
        super.paintComponent(g2d);
        g2d.drawImage(backgroundImage, 0, 0, Constants.FRAME_WIDTH, Constants.Frame_HEIGHT, this);

    }
}
