/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Admin
 */
public class Sound {
    Clip clip; 
    URL soundURL[] = new URL[30];
    
    public Sound() {
        soundURL[0] = getClass().getResource("/res/sound/BlueBoyAdventure.wav/");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav/");
        soundURL[2] = getClass().getResource("/res/sound/powerup.wav/");
        soundURL[3] = getClass().getResource("/res/sound/unlock.wav/");
        soundURL[4] = getClass().getResource("/res/sound/fanfare.wav/");
        soundURL[5] = getClass().getResource("/res/sound/toteti.wav/");
        soundURL[6] = getClass().getResource("/res/sound/hit.wav/");
        soundURL[7] = getClass().getResource("/res/sound/receivedamage.wav/");
        soundURL[8] = getClass().getResource("/res/sound/swing.wav/");
        soundURL[9] = getClass().getResource("/res/sound/dead.wav/");
        soundURL[10] = getClass().getResource("/res/sound/levelup.wav/");
        soundURL[11] = getClass().getResource("/res/sound/cursor.wav/");

    }
    public void setFile(int i){
        try{
            //mở tệp file âm thanh
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch(Exception e){
            
        }
        
    }
    public void play() { 
        clip.start(); 
    }
    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
