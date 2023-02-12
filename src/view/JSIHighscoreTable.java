/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.JSIGameController;
import data.JSIExternalStrings;

/**
 * HighscoreTable View
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIHighscoreTable {
	private JSIGameController gc;

	private JFrame mainFrame;
	private JPanel overPanel;

	// The ScrollPane for the Table and the Table
	private JScrollPane scrollPane;
	private JTable table;

	private GridBagLayout gbl;

	private JButton buttonOk;
	private JButton buttonReset;

	private String[][] data;
	private String[] columnNames;

	private boolean resizable;
	// Flag if About-Dialog is open or not
	private boolean isOpen;
	// Flag if Data has changed
	private boolean hasChanged;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param data
	 *            Data
	 * @param gc
	 *            The GameController
	 */
	public JSIHighscoreTable(JSIGameController gc, String[][] data) {
		this.gc = gc;

		resizable = false;
		isOpen = false;

		columnNames = new String[5];
		columnNames[0] = JSIExternalStrings.place;
		columnNames[1] = JSIExternalStrings.name;
		columnNames[2] = JSIExternalStrings.mode;
		columnNames[3] = JSIExternalStrings.status;
		columnNames[4] = JSIExternalStrings.score;

		setNewData(data);
		initGUI();
	}

	/*********** Functions ***********/

	/**
	 * Initialises the GUI
	 */
	private void initGUI() {
		if (mainFrame == null) {
			mainFrame = new JFrame();
		}
		if (!mainFrame.isVisible()) {
			mainFrame = new JFrame();
			mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			buttonOk = new JButton("");
			buttonReset = new JButton("");

			if (data == null) {
				table = new JTable(new String[0][], columnNames);
			} else {
				table = new JTable(data, columnNames);
			}
			scrollPane = new JScrollPane(table);

			// Initiate GridBagLayout
			gbl = new GridBagLayout();
			overPanel = new JPanel(gbl);

			// Insets
			int inset1 = 3;
			int inset2 = 8;
			int inset3 = 3;
			int inset4 = 8;

			// Table
			addComponentToGridBagLayout(overPanel, gbl, scrollPane, 0, 0, 3, 5,
					0.0, 0.0, inset1, inset2, inset3, inset4);
			// Button Ok
			addComponentToGridBagLayout(overPanel, gbl, buttonOk, 0, 6, 1, 1,
					0.0, 0.0, inset1, inset2, inset3, inset4);
			// Button Reset
			addComponentToGridBagLayout(overPanel, gbl, buttonReset, 1, 6, 1,
					1, 0.0, 0.0, inset1, inset2, inset3, inset4);

			mainFrame.add(overPanel);
			mainFrame.setResizable(resizable);
			mainFrame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			mainFrame.setAlwaysOnTop(true);
			mainFrame.setPreferredSize(new Dimension(500, 520));
			mainFrame.pack();
			mainFrame.setLocation(
					(Toolkit.getDefaultToolkit().getScreenSize().width / 2)
							- (mainFrame.getWidth() / 2), (Toolkit
							.getDefaultToolkit().getScreenSize().height / 2)
							- (mainFrame.getHeight() / 2));
			hasChanged = false;
		}
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
	 * Returns the main Frame
	 * 
	 * @return the main Frame
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Returns the Ok-Button
	 * 
	 * @return the Ok-Button
	 */
	public JButton getButtonOk() {
		return buttonOk;
	}

	/**
	 * Returns the Reset-Button
	 * 
	 * @return the Reset-Button
	 */
	public JButton getButtonReset() {
		return buttonReset;
	}

	/*********** Setter ***********/

	/**
	 * Sets new Data
	 * 
	 * @param data
	 */
	public void setNewData(String[][] data) {
		this.data = data;
		hasChanged = true;
	}

	/**
	 * Sets the HighscoreTable visible
	 * 
	 * @param setVisible
	 *            Set visible or not
	 */
	public void setVisible(boolean setVisible) {
		if (hasChanged) {
			initGUI();
			if (setVisible) {
				gc.addNewHighscoreListener();
			}
		}
		if (setVisible && !isOpen) {
			mainFrame.setVisible(true);
			isOpen = true;
		} else if (!setVisible && isOpen) {
			mainFrame.setVisible(false);
			mainFrame.dispose();
			isOpen = false;
		}
		buttonOk.requestFocus();
	}

}
