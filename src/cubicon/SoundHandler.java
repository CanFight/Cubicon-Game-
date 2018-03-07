package cubicon;

import java.io.File;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.Player; //A java library that supports mp3 files. (to cut down project size)

public class SoundHandler {

    private Player player;
    private Thread musicThread;
    
    public SoundHandler() {
    }

   public void playMusicJL(String url) {
         try {
            FileInputStream input;
            input = new FileInputStream(url);
            player = new Player(input);
            musicThread = new Thread() {
                @Override
                public void run() {
                    try {
                        while(true){
                            player.play ();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            musicThread.start();
        } catch (Exception ex) {
            System.err.println("Failed playing music...");
            ex.printStackTrace();
        }
    }
    
    public void stopMusic(){
        try{
            musicThread.stop();
        }catch(Exception e){
            System.err.println("Failed to stop music.");
        }
    }

    public void playSound(final String url) {    //Effectively plays any sound on top of eachother
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(("./" + url)));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
