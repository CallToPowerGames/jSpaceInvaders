/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package data;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Sprite
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSISprite {
	private Image image;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 * 
	 * @param image
	 */
	public JSISprite(Image image) {
		this.image = image;
	}

	/*********** Functions ***********/

	/**
	 * Draw onto graphical Context
	 * 
	 * @param g
	 *            The graphical Context
	 * @param x
	 *            The x-Coordinate
	 * @param y
	 *            The y-Coordinate
	 */
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x, y, null);
	}

	/*********** Getter ***********/

	/**
	 * Returns the Image
	 * 
	 * @return the Image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Returns the Width of the Image
	 * 
	 * @return the Width of the Image
	 */
	public int getWidth() {
		return image.getWidth(null);
	}

	/**
	 * Returns the Height of the Image
	 * 
	 * @return the Height of the Image
	 */
	public int getHeight() {
		return image.getHeight(null);
	}
}
