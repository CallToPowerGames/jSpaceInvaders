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
 * ShipType
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIShipType extends JSIType {
	private static JSIImageSpriteCache sc = JSIImageSpriteCache
			.getImageSpriteCache();

	private static JSISprite shipSprite;
	private static JSISprite playerKilledSprite;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public JSIShipType() throws IOException {
		super(); // No need for a Type here

		shipSprite = sc.getImgOrSprite(JSIExternalStrings.spriteShip);
		playerKilledSprite = sc
				.getImgOrSprite(JSIExternalStrings.spritePlayerKilled);
	}

	/*********** Getter ***********/

	/**
	 * Returns the ShipType Sprite
	 * 
	 * @return the ShipType Sprite
	 */
	public JSISprite getSprite() {
		return shipSprite;
	}

	/**
	 * Returns the Player killed Sprite
	 * 
	 * @return the Player killed Sprite
	 */
	public JSISprite getPlayerKilledSprite() {
		return playerKilledSprite;
	}
}
