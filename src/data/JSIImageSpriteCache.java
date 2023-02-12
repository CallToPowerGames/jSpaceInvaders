/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package data;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import view.JSIMessage;

/**
 * Image and Sprite Cache
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIImageSpriteCache {
	private static JSIImageSpriteCache imgSpriteCache = new JSIImageSpriteCache();

	// HashMap for the Sprites and it's References
	private HashMap<String, JSISprite> sprites;

	// The Message to display if an Error occurs
	private JSIMessage message;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	private JSIImageSpriteCache() {
		message = new JSIMessage(null, "", "");
		message.setErrorType();

		sprites = new HashMap<String, JSISprite>();
	}

	/*********** Functions ***********/

	/**
	 * Pre-Caches the Sprites
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void preCache() throws IOException, Exception {
		getImgOrSprite(JSIExternalStrings.spriteAlien1);
		getImgOrSprite(JSIExternalStrings.spriteAlien2);
		getImgOrSprite(JSIExternalStrings.spriteAlien3);
		getImgOrSprite(JSIExternalStrings.spriteAlienBig);
		getImgOrSprite(JSIExternalStrings.spriteInvaderKilled);
		getImgOrSprite(JSIExternalStrings.spriteInvaderKilledBig);
		getImgOrSprite(JSIExternalStrings.spritePlayerKilled);
		getImgOrSprite(JSIExternalStrings.spriteShip);
		getImgOrSprite(JSIExternalStrings.spriteShot1);
		getImgOrSprite(JSIExternalStrings.spriteShot2);
		getImgOrSprite(JSIExternalStrings.spriteFunFuface);
		getImgOrSprite(JSIExternalStrings.spriteFunLolface);

		getImgOrSprite(JSIExternalStrings.imageBackground);
		getImgOrSprite(JSIExternalStrings.imageDockAndSplashscreen);
		getImgOrSprite(JSIExternalStrings.imageLogo);

		getImgOrSprite(JSIExternalStrings.image_cavePainting_image);
		getImgOrSprite(JSIExternalStrings.image_makingOfTetris_image);
		getImgOrSprite(JSIExternalStrings.image_steampunk_image);
		getImgOrSprite(JSIExternalStrings.image_masterplan_image);
	}

	/*********** Getter ***********/

	/**
	 * Returns the imgSpriteCache
	 * 
	 * @return the imgSpriteCache
	 */
	public static JSIImageSpriteCache getImageSpriteCache() {
		return imgSpriteCache;
	}

	/**
	 * Returns a Sprite at reference in the HashMap
	 * 
	 * @param reference
	 *            Reference in the HashMap
	 * @return a Sprite at reference in the HashMap
	 * @throws IOException
	 * @throws Exception
	 */
	public JSISprite getImgOrSprite(String reference) throws IOException {
		if (sprites.get(reference) != null) {
			return sprites.get(reference);
		}

		BufferedImage sourceImage = null;

		URL url = this.getClass().getClassLoader().getResource(reference);

		// ImageIO for reading the Image in
		sourceImage = ImageIO.read(url);

		// Create an accelerated Image of the right Size to store the Sprite in
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(),
				sourceImage.getHeight(), Transparency.BITMASK);

		// Draw the Source Image into the accelerated Image
		image.getGraphics().drawImage(sourceImage, 0, 0, null);

		// Create a Sprite, add it to the Cache and then return it
		JSISprite sprite = new JSISprite(image);
		sprites.put(reference, sprite);

		return sprite;
	}

	/**
	 * Returns an Image
	 * 
	 * @param name
	 * @return an Icon with the Name name, null if failed
	 */
	public Icon getIcon(String name) {
		URL imageURL = this.getClass().getClassLoader().getResource(name);
		if (!(imageURL == null)) {
			return new ImageIcon(imageURL);
		}
		return null;
	}
}
