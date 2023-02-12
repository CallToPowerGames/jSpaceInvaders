/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

/**
 * MouseInputHandler
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIMouseInputMotionHandler implements MouseInputListener {
	private JSIGameController gc;
	private JSIKeyMouseController kmc;

	// Pixel Distance to Ship
	private int pixelDistanceToShip = 25;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 * 
	 * @param kmc
	 *            KeyMouseController
	 * @param gc
	 *            The GameController
	 */
	public JSIMouseInputMotionHandler(JSIKeyMouseController kmc,
			JSIGameController gc) {
		this.kmc = kmc;
		this.gc = gc;
	}

	/*********** Functions ***********/

	/**
	 * Makes the Action
	 * 
	 * @param e
	 *            MouseEvent
	 */
	private void action(MouseEvent e) {
		if (gc.gameRunning()) {
			if (e.getX() < (gc.getShip().getXCoordinate() - pixelDistanceToShip)) {
				kmc.setPressedLeft(true);
				kmc.setPressedRight(false);
			} else if (e.getX() > (gc.getShip().getXCoordinate() + pixelDistanceToShip)) {
				kmc.setPressedLeft(false);
				kmc.setPressedRight(true);
			} else {
				kmc.setPressedLeft(false);
				kmc.setPressedRight(false);
			}
		}
	}

	/**
	 * Mouse exited
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseExited(MouseEvent e) {
		kmc.setPressedLeft(false);
		kmc.setPressedRight(false);
	}

	/**
	 * Mouse dragged
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseDragged(MouseEvent e) {
		action(e);
	}

	/**
	 * Mouse moved
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseMoved(MouseEvent e) {
		action(e);
	}

	/**
	 * Mouse pressed
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mousePressed(MouseEvent e) {
		// Do nothing
	}

	/**
	 * Mouse released
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseReleased(MouseEvent e) {
		// Do nothing
	}

	/**
	 * Mouse clicked
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseClicked(MouseEvent e) {
		// Do nothing
	}

	/**
	 * Mouse entered
	 * 
	 * @param e
	 *            MouseEvent
	 */
	public void mouseEntered(MouseEvent e) {
		// Do nothing
	}
}
