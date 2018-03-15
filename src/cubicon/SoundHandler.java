package cubicon;

import java.io.File;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.Player; //A java library that supports mp3 files. (to cut down project size)
/*
    (if this class couses any problems please just comment out the import for javazoom.jl.player.Player and the content of the playMusicJL class.)

    This class might not be used in the final submission due to problems with copy right on alot of music and sounds.
    However i will still keep it in here as it is a a part of the program. The program itself is also made so it can use it easly.

    To play mp3 sounds (not supported by default in java we use JLayer wich is an OPEN SOURCE library mp3 player.
    Their official website:
    http://www.javazoom.net/javalayer/javalayer.html

*/

public class SoundHandler {

    private Player player;
    private Thread musicThread;
    
    public SoundHandler() {
    }

   public void playMusicJL(String url) { //uses the JLayer Library, needs to run on its on thread else the program will wait for it to finish the current audio clip.
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
    
    public void stopMusic(){ //stops the music by stoping its thread (ugly sulotion but didnt get it to work otherwise)
        try{
            musicThread.stop();
        }catch(Exception e){
            System.err.println("Failed to stop music.");
        }
    }

    public void playSound(final String url) {    //Effectively plays any sound on top of eachother (wav). Used mainly for effects.
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
