package view;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class Setting extends JDialog {
    private    JSlider soundValume;

    public  JSlider getSoundSlider(){
        return soundValume;
    }

    public Setting(){
        super((JFrame)null, "Settings", true);

        soundValume = new JSlider(50 , 120);
        soundValume.setValue(90); // ToDo : change this 100 to pervius value saved in json ;

//        soundValume.addChangeListener(e -> {
//            int volume = soundValume.getValue();
//            FrameManager.musicPlayer.setVolume(volume);
//        });

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
