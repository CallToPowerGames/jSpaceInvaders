/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

import java.io.IOException;

import data.JSIDataStorage;
import data.JSIExternalStrings;
import data.JSISprite;
import data.JSIImageSpriteCache;

/**
 * AlienType
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIAlienType extends JSIType {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private static JSIImageSpriteCache sc = JSIImageSpriteCache
			.getImageSpriteCache();

	// Alien1 Sprite
	private static JSISprite alien1Sprite;
	// Alien2 Sprite
	private static JSISprite alien2Sprite;
	// Alien3 Sprite
	private static JSISprite alien3Sprite;
	// Big Alien Sprite
	private static JSISprite alienBigSprite;
	// Explosion Sprite
	private static JSISprite invaderKilledSprite;
	// Explosion Big Alien Sprite
	private static JSISprite invaderKilledBigSprite;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param type
	 *            Type
	 * @throws IOException
	 * @throws Exception
	 */
	public JSIAlienType(int type) throws IOException, Exception {
		super(type);

		alien1Sprite = sc.getImgOrSprite(JSIExternalStrings.spriteAlien1);
		alien2Sprite = sc.getImgOrSprite(JSIExternalStrings.spriteAlien2);
		alien3Sprite = sc.getImgOrSprite(JSIExternalStrings.spriteAlien3);
		alienBigSprite = sc.getImgOrSprite(JSIExternalStrings.spriteAlienBig);
		invaderKilledSprite = sc
				.getImgOrSprite(JSIExternalStrings.spriteInvaderKilled);
		invaderKilledBigSprite = sc
				.getImgOrSprite(JSIExternalStrings.spriteInvaderKilledBig);
	}

	/*********** Getter ***********/

	/**
	 * Returns the Number of Points for killing this Type
	 * 
	 * @return the Number of Points for killing this Type
	 */
	public double getPoints() {
		switch (super.getType()) {
		case 1:
			return ds.ALIEN_TYPE_1_POINTS;
		case 2:
			return ds.ALIEN_TYPE_2_POINTS;
		case 3:
			return ds.ALIEN_TYPE_3_POINTS;
		case 4:
			return ds.ALIEN_TYPE_4_POINTS;
		default:
			return 1.0;
		}
	}

	/**
	 * Returns the AlienType Sprite
	 * 
	 * @return the AlienType Sprite
	 */
	public JSISprite getSprite() {
		switch (super.getType()) {
		case 1:
			return alien1Sprite;
		case 2:
			return alien2Sprite;
		case 3:
			return alien3Sprite;
		case 4:
			return alienBigSprite;
		default:
			return alien1Sprite;
		}
	}

	/**
	 * Returns the AlienType Explosion Sprite
	 * 
	 * @return the AlienType Explosion Sprite
	 */
	public JSISprite getInvaderKilledSprite() {
		return invaderKilledSprite;
	}

	/**
	 * Returns the AlienType Explosion Big Sprite
	 * 
	 * @return the AlienType Explosion Big Sprite
	 */
	public JSISprite getInvaderKilledBigSprite() {
		return invaderKilledBigSprite;
	}
}
