/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

import controller.JSIGameController;
import data.JSIDataStorage;
import data.JSISprite;

/**
 * AlienEntity
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIAlienEntity extends JSIEntity {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private JSIGameController gc;

	private JSIAlienType type;
	private int height;

	/*********** Constructors ***********/

	/**
	 * Alien Entity
	 * 
	 * @param gc
	 *            GameController
	 * @param type
	 *            The AlienType
	 * @param x
	 *            The x-Coordinate
	 * @param y
	 *            The y-Coordinate
	 */
	public JSIAlienEntity(JSIGameController gc, JSIAlienType type, int x, int y) {
		super(type.getSprite(), x, y);

		this.type = type;
		this.gc = gc;
		setDx(ds.getAlienSpeed());

		height = ds.HEIGHT - 50;
		// Big Screen
		if (ds.bigScreen()) {
			height -= ds.WINDOW_BIGGER_FACTOR;
		}
	}

	/*********** Functions ***********/

	/**
	 * Alien Movement
	 * 
	 * @param factor
	 *            Factor
	 */
	public void move(double factor) {
		// Too far at the left Side + Movement or
		// Too far at the right Side + Movement
		if (type.getType() != 4) {
			if (((getDx() < 0) && (getXCoordinate() < 20))
					|| ((getDx() > 0) && (getXCoordinate() > (ds.WIDTH - 40)))) {
				gc.setUpdateThisLoop();
			}
		} else {
			if ((getDx() < 0) && (getXCoordinate() < 20)) {
				setDx(-getDx());
			} else if ((getDx() > 0) && (getXCoordinate() > (ds.WIDTH - 100))) {
				if (ds.getRandomBetweenZeroAnd(100) > 50) {
					gc.removeEntity(this, false);
				} else {
					setDx(-getDx());
				}
			}
		}

		// Proceed with normal Movement
		super.setXCoordinate(super.getXCoordinate()
				+ (int) ((factor * super.getDx()) / 1000));
		if (type.getType() != 4) {
			super.setYCoordinate(super.getYCoordinate()
					+ (int) ((factor * super.getDy()) / 1000));
		}
	}

	/**
	 * Updates Alien Entity
	 */
	public void update() {
		// Change Course to the other Side
		setDx(-getDx());
		// Move a bit down
		setYCoordinate(getYCoordinate() + 10);

		// Reached the Bottom of the Screen
		if (getYCoordinate() > height) {
			gc.setLivesTo0();
			gc.notifyDeath();
		}
	}

	/**
	 * Notification that this shot has collided with another entity
	 * 
	 * @param other
	 *            The entity which the ship has collided with
	 */
	public void collidedWith(JSIEntity other) {
		if (!gc.isInRemoveList(other)) {
			if (other instanceof JSIShotEntity) {
				JSIShotEntity cashe = (JSIShotEntity) other;
				if ((cashe.getType().getType() == 1) && !cashe.used()) {
					gc.removeEntity(this, true);
				}
			} else if (other instanceof JSIShipEntity) {
				gc.removeEntity(this, false);
			}
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns the AlienType
	 * 
	 * @return the AlienType
	 */
	public JSIAlienType getType() {
		return type;
	}

	/**
	 * Returns the Sprite if an Invader has been killed
	 * 
	 * @return the Sprite if an Invader has been killed
	 */
	public JSISprite getExplosionSprite() {
		if (type.getType() == 4) {
			return type.getInvaderKilledBigSprite();
		}
		return type.getInvaderKilledSprite();
	}
}
