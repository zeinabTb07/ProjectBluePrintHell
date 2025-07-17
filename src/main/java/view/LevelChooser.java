package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LevelChooser extends JDialog {
    ArrayList<JButton> levels ;

    public LevelChooser(){
        super((JFrame)null, "Levels", true);
        levels = new ArrayList<>();
        levels.add(new JButton());
        levels.add(new JButton());
        levels.add(new JButton());
        levels.add(new JButton());



        this.add(new JLabel("Levels :"));
        this.setLayout(new FlowLayout());

        for (int i = 0 ; i < levels.size(); i++){
            JButton button =  levels.get(i);
            button.setText("Level " + (i+1) );
            button.addActionListener(e -> {
                FrameManager.goToGame();
            });
            button.addActionListener(e -> {
                this.setVisible(false);
            });
            this.add(button);
        }


        this.setSize(300, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setVisible(false);
    }
}
