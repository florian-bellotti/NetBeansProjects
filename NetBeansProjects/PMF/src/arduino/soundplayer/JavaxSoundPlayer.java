/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino.soundplayer;

import arduino.data.control.Const;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public final class JavaxSoundPlayer {
    String alarm = "";
    
   // Nested class for specifying volume
   public static enum Volume { MUTE, LOW, MEDIUM, HIGH }
   
   public static Volume volume = Volume.HIGH;
   
   // Each sound effect has its own clip, loaded with its own sound file.
   private static Clip clip;
   private static Boolean played;
   //private final static Chronometer chrono = new Chronometer();
   private static File file;
           
   // Constructor to construct each element of the enum with its own sound file.
   // only wav supported
    public JavaxSoundPlayer(String soundFileName) throws Exception{
        played = Boolean.FALSE;
        try {
           // Use URL (instead of File) to read from disk and JAR.
           file = new File(soundFileName);
           if(!file.exists()) return;
           
           // Get a clip resource.
           clip = AudioSystem.getClip();
 
           clip.open(AudioSystem.getAudioInputStream(file));
           
        } catch (IOException e) {
           throw e;
        } catch (LineUnavailableException e) {
           throw e;
        }
   }
   
    /**
     * Reset chrono
     */
   /*private static void reset(){
        if(!chrono.Reset()){
            chrono.stop();
            if(chrono.getSeconds() >= Const._SOUND_SEP){
                played = Boolean.FALSE;
                chrono.reset();
            }
        }
   }*/
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public synchronized void play() {
       if(clip == null || !file.exists()) return;
       
        //reset();
        //if(chrono.Reset())
          //  chrono.start();
        
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();   // Stop the player if it is still running
            
            new Thread(() -> {
                clip.start();
                played = Boolean.TRUE;
            }).start();            
        }
   }
}