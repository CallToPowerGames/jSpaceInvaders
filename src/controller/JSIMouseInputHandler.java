/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * MouseInputHandler
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIMouseInputHandler implements MouseListener {
	private JSIKeyMouseController kmc;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 * 
	 * @param kmc
	 *            KeyMouseController
	 */
	public JSIMouseInputHandler(JSIKeyMouseController kmc) {
		this.kmc = kmc;
	}

	/*********** Functions ***********/

	/**
	 * Notification that the Mouse has been clicked
	 * 
	 * @param e
	 *            The Details of the Button that has been typed
	 */
	public void mouseClicked(MouseEvent e) {
		kmc.startGame();
	}

	/**
	 * Notification the Mouse has been pressed.
	 * 
	 * @param e
	 *            The Details of the Button that has been pressed
	 */
	public void mousePressed(MouseEvent e) {
		setMouseEvent(e, true);
	}

	/**
	 * Notification the Mouse has been released
	 * 
	 * @param e
	 *            The Details of the Button that has been released
	 */
	public void mouseReleased(MouseEvent e) {
		setMouseEvent(e, false);
	}

	/**
	 * Not used
	 * 
	 * @param e
	 */
	public void mouseExited(MouseEvent e) {
		kmc.setPressedLeft(false);
		kmc.setPressedRight(false);
		setMouseEvent(e, false);
	}

	/**
	 * Not used
	 * 
	 * @param e
	 */
	public void mouseEntered(MouseEvent e) {
		// Do nothing
	}

	/*********** Setter ***********/

	/**
	 * Sets the Button Event
	 * 
	 * @param e
	 *            MouseEvent
	 * @param set
	 *            Set pressed fire or not
	 */
	private void setMouseEvent(MouseEvent e, boolean set) {
		// If waiting for Gamestart
		if (kmc.waitingForKeyMousePress()) {
			return;
		}

		// Only fire with the Mouse
		kmc.setPressedFire(set);
	}

}
