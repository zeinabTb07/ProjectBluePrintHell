package view;

import controller.GameLoop;
import controller.GameState;
import model.Connection;
import model.G_System;
import model.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoBar extends JLabel {
    private static JLabel coin ;
    private static JProgressBar wireRemain;
    private static JProgressBar packetLoss ;
    private static JSlider temporalProgress ;
    private static JButton run ;
    private static Timer timer ;

        public InfoBar(){
            super();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBounds(0 , 0 , Frame.get_Length() , 60);
            setBackground(Color.white);
            setOpaque(true);
            add(Box.createHorizontalStrut(20));

            JButton back = new JButton();
            back.setIcon(new ImageIcon("src/main/resources/icons8-back-to-30.png"));
            back.setText("Back");
            back.setBackground(Color.white);
            back.addActionListener(e -> {
                FrameManager.goToMenu();
            });
            add(back);

            add(Box.createHorizontalStrut(20));


            coin = new JLabel(": " + 0); // TODO
            coin.setIcon(new ImageIcon("src/main/resources/icons8-coin-30.png"));
            add(coin);
            add(Box.createHorizontalStrut(20));

            wireRemain = new JProgressBar(0 , GameState.getInstance().getGameStage().getWireLength());
            wireRemain.setString("Wire Length");
            wireRemain.setValue(wireRemain.getMaximum());
            wireRemain.setStringPainted(true);
            wireRemain.setFont(new Font("SansSerif", Font.PLAIN, 20));
            wireRemain.setBackground(Color.red.brighter());
            wireRemain.setForeground(Color.LIGHT_GRAY);
            add(wireRemain);

            add(Box.createHorizontalStrut(20));

            packetLoss = new JProgressBar(0 , 100);
            packetLoss.setString("Packet Loss");
            packetLoss.setValue(100);
            packetLoss.setBackground(Color.GRAY);
            packetLoss.setForeground(Color.LIGHT_GRAY);
            packetLoss.setStringPainted(true);
            packetLoss.setFont(new Font("SansSerif", Font.PLAIN, 20));
            add(packetLoss);
            add(Box.createHorizontalStrut(20));

            temporalProgress = new JSlider(0 , 120);
            temporalProgress.setFont(new Font("SansSerif", Font.PLAIN, 20));
            temporalProgress.setEnabled(false);
            add(temporalProgress);

            add(Box.createHorizontalStrut(20));

            run = new JButton("Run!");
            run.setFont(new Font("SansSerif", Font.PLAIN, 25));
            run.setBackground(Color.white);
            run.addActionListener(e -> {
               Packet packet = GameState.getInstance().getPackets().get(0);
//              packet.setX(packet.getSourcePort().getX());
//               packet.setY(packet.getSourcePort().getY());
                //TODO: strat packet move ;
                timer = new Timer(20, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        packet.move();
                        System.out.println("timerCalled");
                        FrameManager.getGamePanel().repaint();
                    }
                });
                timer.start();
                repaint();
            });
            run.setEnabled(false);
            add(run);


            add(Box.createHorizontalStrut(20));
            JButton shop = new JButton();
            shop.setIcon(new ImageIcon("src/main/resources/icons8-shopping-cart-30.png"));
            shop.addActionListener(e -> {
                FrameManager.OpenShop();
            });
            shop.setBackground(Color.white);
            add(shop);
            add(Box.createHorizontalStrut(20));
        }
        public static void checkRunable(){

            for(G_System system : GameState.getInstance().getSystems()){
               if(!system.getInductor().checkConnections()){
                   run.setEnabled(false);
                   return;
               }
            }
            run.setEnabled(true);
        }

        public static void addWire(int d){
            if(wireRemain.getValue()-d >= 0){
                wireRemain.setValue(wireRemain.getValue()-d);
            } else {
                FrameManager.musicPlayer.playSoundEffect("src/main/resources/drop.wav");
            }
        }

        public static void removeWire(int d){
            wireRemain.setValue(wireRemain.getValue()+d);
        }

}
