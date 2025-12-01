package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	int URL_max = 30;

	Clip clip;
	URL soundURL[] = new URL[URL_max];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/imperius_stay.wav");
		soundURL[1] = getClass().getResource("/sound/imperius_enter.wav");
		soundURL[2] = getClass().getResource("/sound/coin.wav");
		soundURL[3] = getClass().getResource("/sound/powerup.wav");
		soundURL[4] = getClass().getResource("/sound/unlock.wav");
		soundURL[5] = getClass().getResource("/sound/fanfare.wav");
		soundURL[6] = getClass().getResource("/sound/Scroll_Postal.wav");
	}
	
	public void setFile(int x) {
		
		try{
			
			AudioInputStream inStr = AudioSystem.getAudioInputStream(soundURL[x]);
			clip = AudioSystem.getClip();
			clip.open(inStr);
			
		} catch (Exception e) {
			
		}
		
	}
	
	public void play() {
		
		clip.start();
	}
	
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		
		clip.stop();
	}
}
