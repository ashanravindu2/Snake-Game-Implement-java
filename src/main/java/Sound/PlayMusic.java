package Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayMusic {

    private static Clip playingClip;


    public void playBackgroundMusic(String musicPath) {
        try {

            File soundFile = new File(musicPath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            playingClip = AudioSystem.getClip();
            playingClip.open(audioInputStream);

            FloatControl volumeControl = (FloatControl) playingClip.getControl(FloatControl.Type.MASTER_GAIN);
            float quarterVolume = volumeControl.getMaximum() / 4.0f;
            volumeControl.setValue(quarterVolume);

            playingClip.start();

            System.out.println("Background music started: " + musicPath);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.err.println("Error playing background music: " + e.getMessage());
            e.printStackTrace();
        }
    }

   public void stopAllBackgroundMusic(){
        this.playingClip.stop();
   }


}