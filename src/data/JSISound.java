/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package data;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

/**
 * Sound
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSISound {
	private AudioFormat af;
	private AudioInputStream audioInputStream;
	private int size;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param audioInputStream
	 * @param af
	 * @param size
	 */
	public JSISound(AudioInputStream audioInputStream, AudioFormat af, int size) {
		this.audioInputStream = audioInputStream;
		this.af = af;
		this.size = size;
	}

	/*********** Functions ***********/

	/**
	 * Plays the Sound
	 */
	public void play() {
		new Thread() {
			public void run() {
				byte[] audio = new byte[size];
				DataLine.Info info = new DataLine.Info(Clip.class, af, size);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						audioInputStream);
				try {
					bufferedInputStream.read(audio, 0, size);
					Clip clip = (Clip) AudioSystem.getLine(info);
					clip.open(af, audio, 0, size);
					clip.start();
				} catch (IOException e) {
					// Do nothing
				} catch (LineUnavailableException e) {
					// Do nothing
				} catch (Exception e) {
					// Do nothing
				}
			}
		}.start();
	}
}
