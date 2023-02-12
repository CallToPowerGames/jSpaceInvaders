/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package data;

import java.io.IOException;
import java.net.URL; //import java.util.HashMap;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import view.JSIMessage;

// TODO Cache - doesn't work as in soundCache

/**
 * soundCache
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSISoundCache {
	private static JSISoundCache soundCache = new JSISoundCache();

	// HashMap for the Sounds and it's References
	private HashMap<String, URL> sounds;

	private int size;
	private URL url;
	private AudioInputStream audioInputStream;
	private AudioFormat af;

	// The Message to display if an Error occurs
	private JSIMessage message;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 */
	private JSISoundCache() {
		message = new JSIMessage(null, "", "");
		message.setErrorType();

		sounds = new HashMap<String, URL>();

		url = null;
		audioInputStream = null;
		af = null;
	}

	/*********** Functions ***********/

	/**
	 * Pre-Caches the Sounds
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void preCache() throws IOException, Exception {
		getSound(JSIExternalStrings.soundBigInvaderSpawned);
		getSound(JSIExternalStrings.soundExplosion);
		getSound(JSIExternalStrings.soundInvaderKilled);
		getSound(JSIExternalStrings.soundMovementInvader);
		getSound(JSIExternalStrings.soundShotPlayer);
		getSound(JSIExternalStrings.soundShowInvader);
		getSound(JSIExternalStrings.soundAccepted);
		getSound(JSIExternalStrings.soundRejected);

	}

	/*********** Getter ***********/

	/**
	 * Returns the SoundCache
	 * 
	 * @return the SoundCache
	 */
	public static JSISoundCache getSoundCache() {
		return soundCache;
	}

	/**
	 * Returns the BigInvaderSpawned Sound
	 * 
	 * @return the BigInvaderSpawned Sound
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISound getBigInvaderSpawnedSound()
			throws UnsupportedAudioFileException, IOException, Exception {
		return getSound(JSIExternalStrings.soundBigInvaderSpawned);
	}

	/**
	 * Returns the Aliens Movement Sound
	 * 
	 * @return the Aliens Movement Sound
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISound getAlienMovementSound()
			throws UnsupportedAudioFileException, IOException, Exception {
		return getSound(JSIExternalStrings.soundMovementInvader);
	}

	/**
	 * Returns the ShotPlayer Sound
	 * 
	 * @return the ShotPlayer Sound
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISound getShotPlayerSound() throws UnsupportedAudioFileException,
			IOException, Exception {
		return getSound(JSIExternalStrings.soundShotPlayer);
	}

	/**
	 * Returns the ShotInvader Sound
	 * 
	 * @return the ShotInvader Sound
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISound getShotInvaderSound() throws UnsupportedAudioFileException,
			IOException, Exception {
		return getSound(JSIExternalStrings.soundShowInvader);
	}

	/**
	 * Returns the Invader killed Sound
	 * 
	 * @return the Invader killed Sound
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISound getInvaderKilledSound()
			throws UnsupportedAudioFileException, IOException, Exception {
		return getSound(JSIExternalStrings.soundInvaderKilled);
	}

	/**
	 * Returns the Explosion Sound
	 * 
	 * @return the Explosion Sound
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISound getExplosionSound() throws UnsupportedAudioFileException,
			IOException, Exception {
		return getSound(JSIExternalStrings.soundExplosion);
	}

	/**
	 * Returns the Accepted Sound
	 * 
	 * @return the Accepted Sound
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISound getAcceptedSound() throws UnsupportedAudioFileException,
			IOException, Exception {
		return getSound(JSIExternalStrings.soundAccepted);
	}

	/**
	 * Returns the Rejected Sound
	 * 
	 * @return the Rejected Sound
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISound getRejectedSound() throws UnsupportedAudioFileException,
			IOException, Exception {
		return getSound(JSIExternalStrings.soundRejected);
	}

	/**
	 * Returns a Sound at reference in the HashMap
	 * 
	 * @param reference
	 *            Reference in the HashMap
	 * @return a Sound at reference in the HashMap
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws Exception
	 */
	private JSISound getSound(String reference)
			throws UnsupportedAudioFileException, IOException, Exception {
		if (sounds.get(reference) == null) {
			url = this.getClass().getClassLoader().getResource(reference);
			sounds.put(reference, url);
		} else {
			url = sounds.get(reference);
		}

		audioInputStream = AudioSystem.getAudioInputStream(url);
		af = audioInputStream.getFormat();
		size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());

		return new JSISound(audioInputStream, af, size);
	}
}
