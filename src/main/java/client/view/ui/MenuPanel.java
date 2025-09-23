package client.view.ui;


import shared.events.GameEvents;
import shared.events.Publisher;
import shared.events.UIEvents.*;
import client.Constants;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private Image backgroundImage;
    private Publisher publisher;

    public MenuPanel() {
        super();
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

        try {
            backgroundImage = new ImageIcon("src/main/resources/client/graphic/edited_background.jpg").getImage();
        } catch (Exception e) {
            System.out.println("Background image not found!");
        }
        repaint();

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBounds(
                (int)(100 * Constants.SCALE),
                (int)(100 * Constants.SCALE),
                (int)(800 * Constants.SCALE),
                (int)(150 * Constants.SCALE)
        );

        JLabel blue = new JLabel("Blue");
        blue.setFont(new Font("Chiller", Font.BOLD, (int)(120 * Constants.SCALE)));
        blue.setForeground(Color.BLUE.darker());
        blue.setOpaque(false);

        JLabel print = new JLabel("Print");
        print.setFont(new Font("Ravie", Font.BOLD, (int)(120 * Constants.SCALE)));
        print.setForeground(Color.GRAY);
        print.setOpaque(false);

        JLabel hell = new JLabel("Hell");
        hell.setFont(new Font("Blackadder ITC", Font.BOLD, (int)(120 * Constants.SCALE)));
        hell.setForeground(Color.RED.darker());
        hell.setOpaque(false);

        titlePanel.add(blue);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(print);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(hell);

        add(titlePanel);


        JButton replay = new ButtonBuilder.Builder()
                .withText("Play Offline")
                .atPosition(new Point(
                        (int)(600 * Constants.SCALE),
                        (int)(550 * Constants.SCALE)
                ))
                .withAction(e -> publisher.publish(new Replay()))
                .build();

        JButton play = new ButtonBuilder.Builder()
                .withText("Go Online")
                .atPosition(new Point(
                        (int)(350 * Constants.SCALE),
                        (int)(400 * Constants.SCALE)
                ))
                .withAction(e -> {{
                    publisher.publish(new GameEvents.GoOnline());

                }})
                .build();

        JButton settings = new ButtonBuilder.Builder()
                .withText("Setting")
                .atPosition(new Point(
                        (int)(100 * Constants.SCALE),
                        (int)(550 * Constants.SCALE)
                ))
                .withAction(e -> publisher.publish(new OpenSetting()))
                .build();

        add(replay);
        add(play);
        add(settings);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(backgroundImage, 0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, this);
        }
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
