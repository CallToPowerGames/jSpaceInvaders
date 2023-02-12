/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.JSIGameController;
import data.JSIDataStorage;
import data.JSIExternalStrings;

/**
 * AboutWindow
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIAboutWindow {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private JSIGameController gc;

	private JFrame mainFrame = null;
	private String title;
	private Icon icon = null;
	private String head = JSIExternalStrings.about;
	private Font headFont = new Font("Arial", Font.BOLD, 18);
	private Font labelHeaderFirstFont = new Font("Arial", Font.BOLD, 16);
	private Font labelHeaderFont = new Font("Arial", Font.BOLD, 14);
	private Font labelFont = new Font("Arial", Font.PLAIN, 12);
	private String labelOk = JSIExternalStrings.ok;
	private boolean alwaysOnTop = true;

	// Insets
	private int inset11 = 0;
	private int inset12 = 0;
	private int inset13 = 2;
	private int inset14 = 8;
	private int inset21 = 0;
	private int inset22 = 0;
	private int inset23 = 8;
	private int inset24 = 8;

	// Labels
	private JLabel jsi1, jsi2, author1, author2;

	// Flag if About-Dialoqur is open or not
	private boolean isOpen = false;

	// Flag if something has changed
	private boolean hasChanged = true;

	// Inside show-Function
	private JDialog dialog;
	private JPanel overPanel;
	private JLabel labelImage;
	private JLabel labelHead;
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
	 * @param jsi1
	 * @param jsi2
	 * @param author1
	 * @param author2
	 */
	public JSIAboutWindow(JSIGameController gc, String jsi1, String jsi2,
			String author1, String author2) {
		this.gc = gc;
		this.mainFrame = gc.getMainFrame();

		this.jsi1 = new JLabel(jsi1);
		this.jsi2 = new JLabel(jsi2);
		this.author1 = new JLabel(author1);
		this.author2 = new JLabel(author2);

		initGUI();
	}

	/*********** Functions ***********/

	/**
	 * Displays the About-Dialog
	 */
	private void initGUI() {
		dialog = new JDialog(mainFrame, title);
		overPanel = new JPanel();

		// Image
		labelImage = null;
		if (icon != null) {
			labelImage = new JLabel(icon);
		}

		// Head
		labelHead = new JLabel(head);
		labelHead.setFont(headFont);

		// Button OK
		buttonOk = new JButton(labelOk);

		// Initiate GridBagLayout
		headPanel = new JPanel();
		gbl = new GridBagLayout();
		mainPanel = new JPanel(gbl);
		int i = 0;

		// Add Components to the GridBagLayout
		// If Image loaded successfully: Display Image
		if (labelImage != null) {
			addComponentToGridBagLayout(mainPanel, gbl, labelImage, 0, i++, 3,
					1, 0.0, 0.0, inset21, inset22, inset23, inset24);
			// Display Text else
		} else {
			// Head
			addComponentToGridBagLayout(mainPanel, gbl, labelHead, 0, i++, 3,
					1, 0.0, 0.0, inset21, inset22, inset23, inset24);
		}

		// Add other JLabels

		// jSpaceInvaders
		// Insert Header-Label
		jsi1.setFont(labelHeaderFirstFont);
		addComponentToGridBagLayout(mainPanel, gbl, jsi1, 0, i++, 1, 1, 0.0,
				0.0, inset11, inset12, inset13, inset14);
		// Insert Text-Label
		jsi2.setFont(labelFont);
		addComponentToGridBagLayout(mainPanel, gbl, jsi2, 0, i++, 2, 1, 0.0,
				0.0, inset21, inset22, inset23, inset24);

		// Author
		// Insert Header-Label
		author1.setFont(labelHeaderFont);
		addComponentToGridBagLayout(mainPanel, gbl, author1, 0, i++, 1, 1, 0.0,
				0.0, inset11, inset12, inset13, inset14);
		// Insert Text-Label
		author2.setFont(labelFont);
		addComponentToGridBagLayout(mainPanel, gbl, author2, 0, i++, 2, 1, 0.0,
				0.0, inset21, inset22, inset23, inset24);

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
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setAlwaysOnTop(alwaysOnTop);

		// Inform the Game Controller to set new ButtonListener
		gc.addNewAboutButtonOkListener();
		hasChanged = true;
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

	/*********** Setter ***********/

	/**
	 * Sets an Icon
	 * 
	 * @param icon
	 *            Icon to set
	 */
	public void setIcon(Icon icon) {
		if (icon != null) {
			this.icon = icon;
			hasChanged = true;
		}
	}

	/**
	 * Sets a Header
	 * 
	 * @param title
	 *            Header to set
	 */
	public void setHead(String title) {
		if (!title.isEmpty() && (title != null)) {
			this.head = title;
			hasChanged = true;
		}
	}

	/**
	 * Sets a Text for the Ok-Button
	 * 
	 * @param text
	 *            Text for the Ok-Button
	 */
	public void setTextOkButton(String text) {
		if (!text.isEmpty() && (text != null)) {
			this.labelOk = text;
			hasChanged = true;
		}
	}

	/**
	 * Sets if the About-Window is always on Top or not
	 * 
	 * @param bool
	 */
	public void setAlwaysOnTop(boolean bool) {
		alwaysOnTop = bool;
		hasChanged = true;
	}

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
			// Initialise GUI if something has changed
			if (hasChanged) {
				initGUI();
				dialog.setVisible(true);
				// Set Dialog visible
			} else {
				dialog.setVisible(true);
			}
			isOpen = true;
		} else if (!setVisible && isOpen) {
			dialog.setVisible(false);
			isOpen = false;
		}
	}

}
