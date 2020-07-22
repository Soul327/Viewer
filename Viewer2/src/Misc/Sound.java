package Misc;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound extends Thread {
	boolean loop = false;
	Clip clip;
	float vol = 1f;
	long clipTime = 0;
	long sleep = 0;
	String location;
	public String group = "default";
	
	public Sound(String location,float vol) {
		this.location = location;
		this.vol = vol;
	}
	public Sound(String location,String group) {
		this.location = location;
		this.group = group;
	}
	public Sound(String location,long sleep) {
		this.location = location;
		this.sleep = sleep;
	}
	public void run() {
		try {
			if(sleep>0)
				Thread.sleep(sleep);
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(location).getAbsoluteFile()); 
			clip.open(inputStream);
			if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
			setVolume(vol*SoundManager.getVolume());
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pause() {
		if(clip.isRunning()) {
			clipTime= clip.getMicrosecondPosition();
			clip.stop();
		}
	}
	
	public void play() {
		if(!clip.isRunning()) {
			clip.setMicrosecondPosition(clipTime);
			clip.start();
		}
	}
	
	public float getVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}
	
	public void setVolume(float volume) {
		if (volume < 0f || volume > 1f)
			throw new IllegalArgumentException("Volume not valid: " + volume);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(20f * (float) Math.log10(volume));
	}
}