/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package controller;

/**
 * KeyMouseController
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIKeyMouseController {
	private JSIGameController gc;

	private JSIKeyInputHandler kih;
	private JSIMouseInputHandler mih;
	private JSIMouseInputMotionHandler mihMotion;

	// True if waiting for a KeyPress for the GameStart
	private boolean waitingForKeyPress;
	// True if left Cursor pressed
	private boolean pressedLeft;
	// True if right Cursor pressed
	private boolean pressedRight;
	// True if firing Cursor pressed
	private boolean pressedFire;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 * 
	 * @param gc
	 *            GameController
	 */
	public JSIKeyMouseController(JSIGameController gc) {
		this.gc = gc;
		kih = new JSIKeyInputHandler(this);
		mih = new JSIMouseInputHandler(this);
		mihMotion = new JSIMouseInputMotionHandler(this, gc);
		resetKeys();
		waitingForKeyPress = true;
	}

	/*********** Functions ***********/

	/**
	 * Returns the KeyInputHandler
	 * 
	 * @return the KeyInputHandler
	 */
	public JSIKeyInputHandler getKeyInputHandler() {
		return kih;
	}

	/**
	 * Returns the MouseInputHandler
	 * 
	 * @return the MouseInputHandler
	 */
	public JSIMouseInputHandler getMouseInputHandler() {
		return mih;
	}

	/**
	 * Returns the MouseInputMotionHandler
	 * 
	 * @return the MouseInputMotionHandler
	 */
	public JSIMouseInputMotionHandler getMouseInputMotionHandler() {
		return mihMotion;
	}

	/**
	 * Returns if waiting for Key press
	 * 
	 * @return true if waiting for Key press, false else
	 */
	public boolean waitingForKeyMousePress() {
		return waitingForKeyPress;
	}

	/**
	 * Starts the Game
	 */
	public void startGame() {
		// If waiting for Gamestart
		if (waitingForKeyMousePress() && !gc.aboutOpen()
				&& !gc.textWindowOpen()) {
			waitingForKeyPress = false;
			pressedLeft = false;
			pressedRight = false;
			pressedFire = false;
			// Show ModeChooser
			gc.showModeChooser();
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns waitingForKeyPress
	 * 
	 * @return waitingForKeyPress
	 */
	public boolean getWaitingForKeyPress() {
		return waitingForKeyPress;
	}

	/**
	 * Returns pressedLeft
	 * 
	 * @return pressedLeft
	 */
	public boolean getPressedLeft() {
		return pressedLeft;
	}

	/**
	 * Returns pressedRight
	 * 
	 * @return pressedRight
	 */
	public boolean getPressedRight() {
		return pressedRight;
	}

	/**
	 * Returns pressedFire
	 * 
	 * @return pressedFire
	 */
	public boolean getPressedFire() {
		return pressedFire;
	}

	/*********** Setter ***********/

	/**
	 * Resets the Keys
	 */
	public void resetKeys() {
		waitingForKeyPress = false;
		pressedLeft = false;
		pressedRight = false;
		pressedFire = false;
	}

	/**
	 * Sets waitingForKeyPress
	 * 
	 * @param waitingForKeyPress
	 */
	public void setWaitingForKeyPress(boolean waitingForKeyPress) {
		this.waitingForKeyPress = waitingForKeyPress;
	}

	/**
	 * Sets pressedLeft
	 * 
	 * @param pressedLeft
	 */
	public void setPressedLeft(boolean pressedLeft) {
		this.pressedLeft = pressedLeft;
	}

	/**
	 * Sets pressedRight
	 * 
	 * @param pressedRight
	 */
	public void setPressedRight(boolean pressedRight) {
		this.pressedRight = pressedRight;
	}

	/**
	 * Sets pressedFire
	 * 
	 * @param pressedFire
	 */
	public void setPressedFire(boolean pressedFire) {
		this.pressedFire = pressedFire;
	}
}
