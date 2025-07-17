package view;


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
        setSize(new Dimension(Frame.get_Length() , Frame.get_Width())) ;

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


        JButton exit = FrameManager.buttonFactory.createButton(
                " Exit" ,
                "src/main/resources/icons8-exit-70.png" ,
                150 , 500 ,
                e -> {System.exit(0);});

        add(exit);


        JButton levels =  FrameManager.buttonFactory.createButton(
                " Levels" ,
                "src/main/resources/icons8-list-70.png" ,
                550 , 500 ,
                e -> {FrameManager.OpenLevels();});
        add(levels);

        JButton play =  FrameManager.buttonFactory.createButton(
                "Continue" ,
                "src/main/resources/icons8-disconnected-70.png" ,
                550 , 350 ,
                e -> {FrameManager.goToGame();});
        add(play);


        JButton setting =  FrameManager.buttonFactory.createButton(
                " Setting" ,
                "src/main/resources/icons8-setting-70.png" ,
                150 , 350 ,
                e -> {FrameManager.OpenSetting();} );
        add(setting);

    }
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g ;
        super.paintComponent(g2d);
        g2d.drawImage(backgroundImage, 0, 0, Frame.get_Length(), Frame.get_Width(), this);

    }
}