/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.JSIGameController;
import data.JSIDataStorage;

/**
 * Field
 * 
 * @author Denis Meyer (CallToPower)
 */
@SuppressWarnings("serial")
public class JSIField extends Canvas {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private JSIGameController gc;

	// Strategy that allows to use accelerate Page flipping
	private BufferStrategy strategy;

	private JFrame frame;
	private JPanel panel;
	private JSIMenuBar menuBar;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param gc
	 *            GameController
	 */
	public JSIField(JSIGameController gc) {
		this.gc = gc;

		initGUI();
		addMenuBar();

		// Buffering Strategy: Allows AWT to manage accelerated Graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

	/*********** Functions ***********/

	/**
	 * Initialises the GUI
	 */
	private void initGUI() {
		frame = new JFrame();
		panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(ds.WIDTH, ds.HEIGHT));
		panel.setLayout(null);

		// Set up Canvas Size and put it into the Panel
		setBounds(0, 0, ds.WIDTH, ds.HEIGHT);
		panel.add(this);

		// No auto-repaint
		setIgnoreRepaint(true);

		// Pack and make visible
		frame.pack();
		frame.setResizable(false);
		frame.setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width / 2)
						- (frame.getWidth() / 2), (Toolkit.getDefaultToolkit()
						.getScreenSize().height / 2) - (frame.getHeight() / 2));
		frame.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	/**
	 * Adds a MenuBar
	 */
	private void addMenuBar() {
		menuBar = new JSIMenuBar(frame);
		menuBar.addMenuBar();
	}

	/*********** Getter ***********/

	/**
	 * Returns the Frame
	 * 
	 * @return the Frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Returns the MenuBar
	 * 
	 * @return the MenuBar
	 */
	public JSIMenuBar getMenu() {
		return menuBar;
	}

	/**
	 * Returns the Strategy
	 * 
	 * @return the Strategy
	 */
	public BufferStrategy getStrategy() {
		return strategy;
	}

	/*********** Setter ***********/

	/**
	 * Sets the Field (in-)visible
	 * 
	 * @param setVisible
	 */
	public void setVisible(boolean setVisible) {
		frame.setVisible(setVisible);
	}

	/**
	 * Sets the Pause Action Text
	 * 
	 * @param text
	 *            Test
	 */
	public void setPauseActionText(String text) {
		if (gc.gameLoopRunning()) {
			menuBar.getPauseAction().setText(text);
		}
	}

}
