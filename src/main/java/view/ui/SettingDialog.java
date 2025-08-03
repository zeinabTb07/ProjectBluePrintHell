package view.ui;


import events.EventBus;
import events.UIEvents;

import javax.swing.*;
import java.awt.*;

public class SettingDialog extends JDialog {
    private JSlider soundValume;

    public  JSlider getSoundSlider(){
        return soundValume;
    }

    public SettingDialog(){
        super((JFrame)null, "Settings", true);

        soundValume = new JSlider(50 , 120);
        soundValume.setValue(90);

        soundValume.addChangeListener(e -> {
            int volume = soundValume.getValue();
            EventBus.publish(new UIEvents.VolumeChangeEvent(volume));
        });

        this.add(new JLabel("Sound Volume :"));


        this.setLayout(new FlowLayout());
        this.add(soundValume);

        this.setSize(300, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setVisible(false);
    }

    public int getSoundValume() {
        return soundValume.getValue();
    }

    public  void setSoundValume(int val ) {
        soundValume.setValue(val);
    }

}
