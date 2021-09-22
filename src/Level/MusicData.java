package Level;

import java.applet.AudioClip;

import java.io.File;
import Game.Game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Engine.Config;

public class MusicData {
	
	public static File equalizer;
	public static float dB;
	public static Clip clip;
	public static Game game;
	public static synchronized void playMusic(String musicLocation, double vol) 
	{
		try 
		{
			File musicPath = new File(musicLocation);
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
			clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.getLevel();
			setVol(vol,clip);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	public static void setVol(double vol, Clip clip) 
	{
		FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		dB = (float)(Math.log(vol)/(Math.log(10)) * 20);
		gain.setValue(dB);
	} 
	public static void setVolCall(String volLevel) 
	{
		switch (volLevel)
		{
			case "Low":
				setVol(0.25, clip);
			break;
			case "Mid":
				setVol(0.5, clip);
			break;
			case "Full":
				setVol(1, clip);
			break;
		}
	}
}
