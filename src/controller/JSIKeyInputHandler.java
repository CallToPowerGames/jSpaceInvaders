/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyInputHandler
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIKeyInputHandler implements KeyListener {
	private JSIKeyMouseController kmc;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 * 
	 * @param kmc
	 *            KeyMouseController
	 */
	public JSIKeyInputHandler(JSIKeyMouseController kmc) {
		this.kmc = kmc;
	}

	/*********** Functions ***********/

	/**
	 * Notification that a Key has been pressed.
	 * 
	 * @param e
	 *            The Details of the Key that has been pressed
	 */
	public void keyPressed(KeyEvent e) {
		setKeyEvent(e, true);
	}

	/**
	 * Notification that a Key has been released
	 * 
	 * @param e
	 *            The Details of the Key that has been released
	 */
	public void keyReleased(KeyEvent e) {
		setKeyEvent(e, false);
	}

	/**
	 * Notification that a Key has been typed
	 * 
	 * @param e
	 *            The Details of the Key that has been typed
	 */
	public void keyTyped(KeyEvent e) {
		setKeyEvent(e, false);
	}

	/*********** Setter ***********/

	/**
	 * Sets a Key Event
	 * 
	 * @param e
	 *            KeyEvent
	 * @param set
	 */
	private void setKeyEvent(KeyEvent e, boolean set) {
		// If waiting for Gamestart
		if (kmc.waitingForKeyMousePress()) {
			return;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			kmc.getMouseInputMotionHandler().mouseExited(null);
			kmc.setPressedLeft(set);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			kmc.getMouseInputMotionHandler().mouseExited(null);
			kmc.setPressedRight(set);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			kmc.setPressedFire(set);
		}
	}

}