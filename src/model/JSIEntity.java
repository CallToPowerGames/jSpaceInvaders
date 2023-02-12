/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

import java.awt.Graphics;
import java.awt.Rectangle;

import data.JSISprite;

/**
 * Entity
 * 
 * @author Denis Meyer (CallToPower)
 */
public abstract class JSIEntity {
	private JSISprite sprite;

	// The x-Coordinate
	private int x;
	// The y-Coordinate
	private int y;

	// The current horizontal and vertical Speeds (Pixels/Second)
	private double dx;
	private double dy;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param sprite
	 *            Sprite
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 */
	public JSIEntity(JSISprite sprite, int x, int y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

	/*********** Functions ***********/

	/**
	 * Updates the Location based on the horizontal and vertical Speed
	 * 
	 * @param factor
	 *            Factor
	 */
	public void move(double factor) {
		x += (factor * dx) / 1000.0;
		y += (factor * dy) / 1000.0;
	}

	/**
	 * Draw
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		sprite.draw(g, x, y);
	}

	/*********** Boolean ***********/

	/**
	 * Checks if this Entity collided with the given Entity
	 * 
	 * @param other
	 *            Entity to check with
	 * @return true if they collide, false else
	 */
	public boolean collidesWith(JSIEntity other) {
		Rectangle first = new Rectangle(getXCoordinate(), getYCoordinate(),
				getSprite().getWidth(), getSprite().getHeight());
		Rectangle second = new Rectangle(other.getXCoordinate(),
				other.getYCoordinate(), other.getSprite().getWidth(), other
						.getSprite().getHeight());

		return first.intersects(second);
	}

	/**
	 * Notification that this shot has collided with another entity
	 * 
	 * @param other
	 *            The entity which the ship has collided with
	 */
	public abstract void collidedWith(JSIEntity other);

	/*********** Getter ***********/

	/**
	 * Returns the x-Coordinate
	 * 
	 * @return the x-Coordinate
	 */
	public int getXCoordinate() {
		return x;
	}

	/**
	 * Returns the y-Coordinate
	 * 
	 * @return he y-Coordinate
	 */
	public int getYCoordinate() {
		return y;
	}

	/**
	 * Returns the current horizontal Speed (Pixels/Sec)
	 * 
	 * @return the current horizontal Speed (Pixels/Sec)
	 */
	public double getDx() {
		return dx;
	}

	/**
	 * Returns the current vertical Speed (Pixels/Sec)
	 * 
	 * @return he current vertical Speed (Pixels/Sec)
	 */
	public double getDy() {
		return dy;
	}

	/**
	 * Returns the Sprite
	 * 
	 * @return the Sprite
	 */
	public JSISprite getSprite() {
		return sprite;
	}

	/*********** Setter ***********/

	/**
	 * Sets the x-Coordinate
	 * 
	 * @param x
	 *            x-Coordinate
	 */
	public void setXCoordinate(int x) {
		if (x >= 0) {
			this.x = x;
		}
	}

	/**
	 * Sets the y-Coordinate
	 * 
	 * @param y
	 *            y-Coordinate
	 */
	public void setYCoordinate(int y) {
		if (y >= 0) {
			this.y = y;
		}
	}

	/**
	 * Sets the horizontal Speed (Pixels/Sec)
	 * 
	 * @param dx
	 *            horizontal Speed (Pixels/Sec)
	 */
	public void setDx(double dx) {
		this.dx = dx;
	}

	/**
	 * Sets the vertical Speed (Pixels/Sec)
	 * 
	 * @param dy
	 *            vertical Speed (Pixels/Sec)
	 */
	public void setDy(double dy) {
		this.dy = dy;
	}

	/**
	 * Sets the Speed
	 * 
	 * @param dx
	 *            horizontal Speed (Pixels/Sec)
	 * @param dy
	 *            vertical Speed (Pixels/Sec)
	 */
	public void setSpeed(double dx, double dy) {
		setDx(dx);
		setDy(dy);
	}

	/**
	 * Sets the Sprite
	 * 
	 * @param sprite
	 *            Sprite
	 */
	public void setSprite(JSISprite sprite) {
		this.sprite = sprite;
	}
}
