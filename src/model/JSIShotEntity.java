/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

import controller.JSIGameController;
import data.JSIDataStorage;

/**
 * ShotEntity
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIShotEntity extends JSIEntity {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private JSIGameController gc;

	private JSIShotType type;

	// Flag if already used
	private boolean used;

	/*********** Constructor ***********/

	/**
	 * Shot Entity
	 * 
	 * @param gc
	 *            GameController
	 * @param type
	 *            The ShotType
	 * @param x
	 *            The x-Coordinate
	 * @param y
	 *            The y-Coordinate
	 */
	public JSIShotEntity(JSIGameController gc, JSIShotType type, int x, int y) {
		super(type.getSprite(), x, y);

		this.type = type;
		this.gc = gc;

		// Set Ship or Alien Shot Speed
		switch (type.getType()) {
		// Ship
		case 1:
			setDy(ds.getShipShotSpeed());
			break;
		// Alien
		case 2:
			setDy(ds.getAlienShotSpeed());
			break;
		default:
			setDy(ds.getShipShotSpeed());
			break;
		}

		used = false;
	}

	/*********** Functions ***********/

	/**
	 * Shot Movement
	 * 
	 * @param factor
	 *            Factor
	 */
	public void move(double factor) {
		if (used) {
			gc.removeEntity(this, false);
		} else {
			switch (type.getType()) {
			// Ship
			case 1:
				// Proceed with normal Movement
				super.move(factor);
				if (getYCoordinate() < -100) {
					gc.removeEntity(this, false);
				}
				break;
			// Alien
			case 2:
				setYCoordinate(getYCoordinate() + (int) getDy());
				if (getYCoordinate() > (ds.HEIGHT - 30)) {
					gc.removeEntity(this, false);
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Notification that this shot has collided with another entity
	 * 
	 * @param other
	 *            The entity which the ship has collided with
	 */
	public void collidedWith(JSIEntity other) {
		switch (type.getType()) {
		// Ship
		case 1:
			if (other instanceof JSIAlienEntity) {
				used = true;
			} else if (other instanceof JSIShotEntity) {
				if (((JSIShotEntity) other).getType().getType() == 2) {
					used = true;
				}
			}
			break;
		// Alien
		case 2:
			if (other instanceof JSIShipEntity) {
				used = true;
			} else if (other instanceof JSIShotEntity) {
				if (((JSIShotEntity) other).getType().getType() == 1) {
					used = true;
				}
			}
			break;
		default:
			break;
		}
		if (used) {
			gc.removeEntity(this, false);
		}
	}

	/*********** Boolean ***********/

	/**
	 * Returns if Shot has already been used
	 * 
	 * @return true if Shot has already been used, false else
	 */
	public boolean used() {
		return used;
	}

	/*********** Getter ***********/

	/**
	 * Returns the ShotType
	 * 
	 * @return the ShotType
	 */
	public JSIShotType getType() {
		return type;
	}
}
