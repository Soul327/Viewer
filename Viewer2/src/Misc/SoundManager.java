package Misc;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {
	
	static float volume = 1f;
	public static ArrayList<Sound> sounds = new ArrayList<Sound>();
	
	public static void playSound(String location, float volume) {
		sounds.add(new Sound(location,volume));
		sounds.get(sounds.size()-1).start();
	}
	public static void playLoopingSound(String location, float volume) {
		Sound sound = new Sound(location,volume);
		sound.loop = true;
		sounds.add(sound);
		sound.start();
	}
	public static void playDelayedSound(String location, long sleep) {
		sounds.add(new Sound(location, sleep));
		sounds.get(sounds.size()-1).start();
	}
	public static void setVolume(float vol) {
		volume = vol;
		for(Sound s:sounds)
			s.setVolume(volume);
	}
	public static void pauseAll() {
		for(Sound s:sounds)
			s.pause();
	}
	public static void resumeAll() {
		for(Sound s:sounds)
			s.play();
	}
	public static void pause(String group) {
		for(Sound s:sounds)
			if(s.group.equals(group))
				s.pause();
	}
	public static void resume(String group) {
		for(Sound s:sounds)
			if(s.group.equals(group))
				s.play();
	}
	public static float getVolume() { return volume; };
}

