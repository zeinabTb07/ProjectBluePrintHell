package view.ui;


import events.EventBus;
import events.UIEvents;
import model.Level;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LevelsDialog extends JDialog {
    public LevelsDialog(int levelsSize){
        super((JFrame)null, "Levels", true);
        this.add(new JLabel("Levels :"));
        this.setLayout(new FlowLayout());

        for (int i = 0; i < levelsSize; i++) {
            final int levelIndex = i;
            this.add(new ButtonFactory.Builder()
                    .withText("Level : " +(levelIndex+1))
                    .withAction(e -> {
                        EventBus.publish(new UIEvents.ChooseLevelEvent(levelIndex));
                    })
                    .withSize(new Dimension(70, 30))
                    .withFont(new Font("SansSerif", Font.PLAIN, 20))
                    .build()

            );
        }

        this.setSize(300, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setVisible(false);
    }
}
