/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

import data.JSISprite;

/**
 * Type
 * 
 * @author Denis Meyer (CallToPower)
 */
public abstract class JSIType {
	private int type;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 */
	public JSIType() {
		this(0);
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            the Type
	 */
	public JSIType(int type) {
		this.type = type;
	}

	/*********** Getter ***********/

	/**
	 * Returns the Type
	 * 
	 * @return the Type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Returns the Sprite
	 * 
	 * @return the Sprite
	 */
	public abstract JSISprite getSprite();

	/*********** Setter ***********/

	/**
	 * Sets the Type
	 * 
	 * @param type
	 *            the Type
	 */
	public void setType(int type) {
		this.type = type;
	}
}
