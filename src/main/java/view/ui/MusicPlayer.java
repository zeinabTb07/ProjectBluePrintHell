package view.ui;


import events.EventBus;
import events.UIEvents;

import javax.sound.sampled.*;
import java.io.File;

public class MusicPlayer {
    private Clip backgroundMusic;
    private FloatControl bgVolumeControl;
    private int currentVolume = 90;

    public MusicPlayer (){
        EventBus.subscribe(UIEvents.VolumeChangeEvent.class, e -> setVolume(e.volume()));
        EventBus.subscribe(UIEvents.PlaySoundEvent.class, e-> playSoundEffect(e.path()));
    }
    public void playBackgroundMusic() {
        try {
            if (backgroundMusic != null && backgroundMusic.isRunning()) {
                backgroundMusic.stop();
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("src/main/resources/SoundTrack.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            bgVolumeControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);

            setVolume(currentVolume);

            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundEffect(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File(filePath));
            Clip soundEffect = AudioSystem.getClip();
            soundEffect.open(audioInputStream);

            FloatControl effectControl = (FloatControl) soundEffect.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(currentVolume, effectControl);

            soundEffect.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    soundEffect.close();
                }
            });

            soundEffect.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(int volumePercent) {
        currentVolume = volumePercent;
        if (bgVolumeControl != null) {
            setVolume(volumePercent, bgVolumeControl);
        }
    }

    private void setVolume(int volumePercent, FloatControl control) {
        volumePercent = Math.min(100, Math.max(0, volumePercent));

        float min = control.getMinimum();
        float max = control.getMaximum();
        float range = max - min;

        float volume = volumePercent / 100f;
        float dB = min + (range * (float)Math.pow(volume, 3));

        control.setValue(dB);
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }
}
