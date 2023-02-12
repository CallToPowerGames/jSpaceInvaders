/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

import java.io.IOException;

import data.JSIExternalStrings;
import data.JSISprite;
import data.JSIImageSpriteCache;

/**
 * ShotType
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIShotType extends JSIType {
	private static JSIImageSpriteCache sc = JSIImageSpriteCache
			.getImageSpriteCache();

	// Shot1 Sprite == PlayerShot Sprite
	private static JSISprite shot1Sprite;
	// Shot2 Sprite == InvaderShot Sprite
	private static JSISprite shot2Sprite;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param type
	 *            Type: 1 == ShipShot, 2 == AlienShot
	 * @throws IOException
	 * @throws Exception
	 */
	public JSIShotType(int type) throws IOException, Exception {
		super(type);

		shot1Sprite = sc.getImgOrSprite(JSIExternalStrings.spriteShot1);
		shot2Sprite = sc.getImgOrSprite(JSIExternalStrings.spriteShot2);
	}

	/*********** Getter ***********/

	/**
	 * Returns the ShotType Sprite
	 * 
	 * @return the ShotType Sprite
	 */
	public JSISprite getSprite() {
		switch (super.getType()) {
		case 1:
			return shot1Sprite;
		case 2:
			return shot2Sprite;
		default:
			return shot1Sprite;
		}
	}
}
