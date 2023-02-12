/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.JSIGameController;
import data.JSIDataStorage;
import data.JSIExternalStrings;

/**
 * Console
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIConsole {

	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private JSIGameController gc;

	private JFrame mainFrame = null;

	// Insets
	private int inset11 = 0;
	private int inset12 = 0;
	private int inset13 = 2;
	private int inset14 = 8;
	private int inset21 = 0;
	private int inset22 = 0;
	private int inset23 = 8;
	private int inset24 = 8;

	// Flag if About-Dialoqur is open or not
	private boolean isOpen = false;

	private String lastText = JSIExternalStrings.consoleInitialText;

	// Inside show-Function
	private JDialog dialog;
	private JPanel overPanel;
	private JTextField textField;
	private JButton buttonOk;
	private JPanel headPanel;
	private GridBagLayout gbl;
	private JPanel mainPanel;

	/*********** Constructor ***********/

	/**
	 * DefaultConstructor
	 * 
	 * @param gc
	 *            GameController
	 */
	public JSIConsole(JSIGameController gc) {
		this.gc = gc;
		this.mainFrame = gc.getMainFrame();

		initGUI();
	}

	/*********** Functions ***********/

	/**
	 * Displays the About-Dialog
	 */
	private void initGUI() {
		dialog = new JDialog(mainFrame, JSIExternalStrings.console);
		overPanel = new JPanel();

		// Head
		textField = new JTextField(lastText);
		textField.setBackground(Color.BLACK);
		textField.setForeground(Color.GREEN);

		// Button OK
		buttonOk = new JButton(JSIExternalStrings.ok);

		// Initiate GridBagLayout
		headPanel = new JPanel();
		gbl = new GridBagLayout();
		mainPanel = new JPanel(gbl);
		int i = 0;

		// Add Components to the GridBagLayout
		// Head
		addComponentToGridBagLayout(mainPanel, gbl, textField, 0, i++, 3, 1,
				0.0, 0.0, inset21, inset22, inset23, inset24);

		// Button
		addComponentToGridBagLayout(mainPanel, gbl, buttonOk, 0, i, 1, 1, 0.0,
				0.0, inset11, inset12, inset13, inset14);

		overPanel.add(headPanel, BorderLayout.NORTH);
		overPanel.add(mainPanel, BorderLayout.CENTER);

		// Set resizable
		if (ds.is("Mac") || ds.is("Windows")) {
			dialog.setResizable(false);
		} else {
			dialog.setResizable(true);
		}

		dialog.add(overPanel);
		dialog.setVisible(false);
		dialog.pack();
		dialog.setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width / 2)
						- (dialog.getWidth() / 2), (Toolkit.getDefaultToolkit()
						.getScreenSize().height / 2) - (dialog.getHeight() / 2));
		// dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setAlwaysOnTop(true);

		// Inform the Game Controller to set new ButtonListener
		gc.addNewConsoleListener();
	}

	/**
	 * Adds a Component to a GridBagLayout
	 * 
	 * @param cont
	 *            Container
	 * @param gbl
	 *            GridBagLayout
	 * @param c
	 *            Component
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param weightx
	 * @param weighty
	 * @param inset1
	 * @param inset2
	 * @param inset3
	 * @param inset4
	 */
	private void addComponentToGridBagLayout(Container cont, GridBagLayout gbl,
			Component c, int x, int y, int width, int height, double weightx,
			double weighty, int inset1, int inset2, int inset3, int inset4) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = new Insets(inset1, inset2, inset3, inset4);
		gbl.setConstraints(c, gbc);

		cont.add(c);
	}

	/*********** Getter ***********/

	/**
	 * Returns the Dialog
	 * 
	 * @return the Dialog
	 */
	public JDialog getDialog() {
		return dialog;
	}

	/**
	 * Returns the OK-Button
	 * 
	 * @return the OK-Button
	 */
	public JButton getButtonOk() {
		return buttonOk;
	}

	/**
	 * Returns the Text from the TextField
	 * 
	 * @return the Text from the TextField
	 */
	public String getText() {
		return textField.getText();
	}

	/*********** Setter ***********/

	/**
	 * Sets the About-Dialog (in-)visible
	 * 
	 * @param setVisible
	 *            true if set visible, false else
	 */
	public void setVisible(boolean setVisible) {
		if (setVisible && !isOpen) {
			buttonOk.requestFocus();
			gc.pauseGame("");
			initGUI();
			textField.requestFocus();
			// Set Dialog visible
			dialog.setVisible(true);
			isOpen = true;
		} else if (!setVisible && isOpen) {
			lastText = textField.getText();
			dialog.setVisible(false);
			isOpen = false;
		}
	}
}
