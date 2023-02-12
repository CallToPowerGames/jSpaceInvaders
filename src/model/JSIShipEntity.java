/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

import controller.JSIGameController;
import data.JSIDataStorage;

/**
 * ShipEntity
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIShipEntity extends JSIEntity {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private JSIGameController gc;

	private JSIShipType type;
	private int lifes;

	/*********** Constructor ***********/

	/**
	 * Ship Entity
	 * 
	 * @param gc
	 *            GameController
	 * @param type
	 *            The ShipType
	 * @param x
	 *            The x-Coordinate
	 * @param y
	 *            The y-Coordinate
	 */
	public JSIShipEntity(JSIGameController gc, JSIShipType type, int x, int y) {
		super(type.getSprite(), x, y);

		this.type = type;
		this.gc = gc;
		resetLifes();
	}

	/*********** Functions ***********/

	/**
	 * Ship Movement
	 * 
	 * @param factor
	 *            Factor
	 */
	public void move(double factor) {
		// Too far at the left Side + Movement or
		// Too far at the right Side + Movement
		if (((getDx() < 0) && (getXCoordinate() < 20))
				|| ((getDx() > 0) && (getXCoordinate() > (ds.WIDTH - 50)))) {
			return;
		}

		// Proceed with normal Movement
		super.move(factor);
	}

	/**
	 * Notification that this shot has collided with another entity
	 * 
	 * @param other
	 *            The entity which the ship has collided with
	 */
	public void collidedWith(JSIEntity other) {
		if (!gc.isInRemoveList(other)) {
			// If collided with an Alien: Player's dead
			if (other instanceof JSIAlienEntity) {
				lifes = 0;
				gc.removeEntity(this, false);
				gc.notifyDeath();
			} else if (other instanceof JSIShotEntity) {
				JSIShotEntity cashe = (JSIShotEntity) other;
				if ((cashe.getType().getType() == 2) && !cashe.used()) {
					if (lifes > 0) {
						lifes--;
					} else {
						gc.removeEntity(this, false);
					}
					gc.notifyDeath();
				}
			}
		}
	}

	/**
	 * Notification that this shot has collided with another entity
	 * 
	 * @param other
	 *            The entity which the ship has collided with
	 * @param unbreakable
	 *            if the Ship is unbreakable
	 */
	public void collidedWith(JSIEntity other, boolean unbreakable) {
		if (!gc.isInRemoveList(other)) {
			// If collided with an Alien: Player's dead
			if (other instanceof JSIAlienEntity) {
				lifes = 0;
				gc.removeEntity(this, false);
				gc.notifyDeath();
			} else if (other instanceof JSIShotEntity) {
				JSIShotEntity cashe = (JSIShotEntity) other;
				if ((cashe.getType().getType() == 2) && !cashe.used()) {
					if (lifes > 0) {
						if (!unbreakable) {
							lifes--;
						}
					} else {
						gc.removeEntity(this, false);
					}
					gc.notifyDeath();
				}
			}
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns the ShipType
	 * 
	 * @return the ShipType
	 */
	public JSIShipType getType() {
		return type;
	}

	/**
	 * Returns the Number of remaining Lifes
	 * 
	 * @return the Number of remaining Lifes
	 */
	public int getLifes() {
		return lifes;
	}

	/**
	 * Returns the initial Lifes
	 * 
	 * @return Initial Lifes
	 */
	public int getInitialLifes() {
		switch (ds.getMode()) {
		case WTF:
			return ds.LIFES_WTF;
		case NOOBY:
			return ds.LIFES_NOOBY;
		case EASY:
			return ds.LIFES_EASY;
		case NORMAL:
			return ds.LIFES_NORMAL;
		case HARD:
			return ds.LIFES_HARD;
		case INSANE:
			return ds.LIFES_INSANE;
		default:
			return ds.LIFES_NORMAL;
		}
	}

	/*********** Setter ***********/

	/**
	 * Sets the Lifes to 0
	 */
	public void setLifesTo0() {
		lifes = 0;
	}

	/**
	 * Resets the Number of Lifes
	 */
	public void resetLifes() {
		switch (ds.getMode()) {
		case WTF:
			lifes = ds.LIFES_WTF;
			break;
		case NOOBY:
			lifes = ds.LIFES_NOOBY;
			break;
		case EASY:
			lifes = ds.LIFES_EASY;
			break;
		case NORMAL:
			lifes = ds.LIFES_NORMAL;
			break;
		case HARD:
			lifes = ds.LIFES_HARD;
			break;
		case INSANE:
			lifes = ds.LIFES_INSANE;
			break;
		default:
			lifes = ds.LIFES_NORMAL;
			break;
		}
	}
}
