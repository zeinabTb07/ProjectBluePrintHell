package view.ui;


import model.Level;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LevelsDialog extends JDialog {
    public LevelsDialog(ArrayList<Level> levels){
        super((JFrame)null, "Levels", true);
        this.add(new JLabel("Levels :"));
        this.setLayout(new FlowLayout());

        for (int i = 0 ; i < levels.size(); i++){
            this.add(new ButtonFactory.Builder()
                            .withText("Level :" )
                            .withAction(e->{
                               //  GameState.setLevel(levels.get(i));
                            })
                            .withSize(new Dimension(70 , 40))
                            .build()
                    );
        }

        this.setSize(300, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setVisible(false);
    }
}
