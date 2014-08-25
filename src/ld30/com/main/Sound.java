package ld30.com.main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;

public class Sound {
	public static String SND_PATH = "snd/";
	
	public static Sound HIT;
	public static Sound FIRE_GUN;
	public static Sound PICKUP;
	public static Sound START;
	public static Sound DEATH;
	public static Sound ENEMY_DEATH;
	
	public static void init()
	{
		HIT = loadSound(SND_PATH + "hit.wav");
		FIRE_GUN = loadSound(SND_PATH + "fire_gun.wav");
		PICKUP = loadSound(SND_PATH + "pickup.wav");
		START = loadSound(SND_PATH + "start.wav");
		DEATH = loadSound(SND_PATH + "death.wav");
		ENEMY_DEATH = loadSound(SND_PATH + "enemy_death.wav");
	}
	
	public static Sound loadSound(String file) 
	{
		Sound sound = new Sound();
		
		try {
			Line.Info linfo = new Line.Info(Clip.class);
		    Line line = AudioSystem.getLine(linfo);
		    sound.clip = (Clip) line;
		    AudioInputStream ais = AudioSystem.getAudioInputStream(new File(file));
		    sound.clip.open(ais);
		} catch (Exception e) {
			System.out.println("Couldn't init sound: " + e);
		}
		
		return sound;
	}

	private Clip clip;
	
	public void play() 
	{
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
