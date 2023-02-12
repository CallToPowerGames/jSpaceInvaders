/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package Helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * EnDeCrypt - Encryption and Decryption of the Highscore List Entries
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIEnDeCrypt {
	private KeyGenerator generator;
	private SecretKey key;

	// The used Algorithm for En- and Decryption
	private static String algorithm = "DES";

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public JSIEnDeCrypt() throws NoSuchAlgorithmException {
		generator = KeyGenerator.getInstance(algorithm);
		generator.init(new SecureRandom());

		generateKey();
	}

	/*********** Functions ***********/

	/**
	 * generateKey
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void generateKey() throws NoSuchAlgorithmException {
		key = generator.generateKey();
	}

	/**
	 * encrypt
	 * 
	 * @param message
	 * @return encrypted message
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 */
	public String encrypt(String message) throws IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		// Get the raw Bytes to encrypt,
		// UTF8 is needed for having a Standard Character Set
		byte[] stringBytes = message.getBytes("UTF8");

		// Encrypt using the Cipher
		byte[] raw = cipher.doFinal(stringBytes);

		// Convert to base64 (for easier Display)
		BASE64Encoder encoder = new BASE64Encoder();
		String base64 = encoder.encode(raw);

		return base64;
	}

	/**
	 * decrypt
	 * 
	 * @param encrypted
	 * @return decrypted message
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public String decrypt(String encrypted) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);

		// Decode the base64 coded Message
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] raw = decoder.decodeBuffer(encrypted);

		// Decode the Message
		byte[] stringBytes = cipher.doFinal(raw);

		// Convert the decoded Message to a String
		String clear = new String(stringBytes, "UTF8");
		return clear;
	}

	/*********** Getter ***********/

	/**
	 * Returns the Algorithm
	 * 
	 * @return the Algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Returns the encoded Key
	 * 
	 * @return the encoded Key
	 */
	public byte[] getEncodedKey() {
		return key.getEncoded();
	}

	/*********** Setter ***********/

	/**
	 * Sets the Key
	 * 
	 * @param byteKey
	 */
	public void setKey(byte[] byteKey) {
		if (!(byteKey.length == 0) && !(byteKey == null)) {
			setKey(new SecretKeySpec(byteKey, algorithm));
		}
	}

	/**
	 * Sets the Key
	 * 
	 * @param key
	 */
	public void setKey(SecretKey key) {
		if (key != null) {
			this.key = key;
		}
	}
}
