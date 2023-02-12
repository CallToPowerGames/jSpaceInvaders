/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.JSIGameController;
import data.JSIExternalStrings;

/**
 * TextWindow
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSITextWindow {
	private JSIGameController gc;

	private JFrame mainFrame;

	private String currentTitle;
	private String currentText;
	private Dimension size;

	private Icon icon = null;

	private JDialog dialog;
	private JPanel overPanel;
	private JLabel title;
	private JTextArea textArea;
	private JLabel label;
	private JScrollPane scrollPane;
	private GridBagLayout gbl;
	private JButton buttonOk;
	private JButton buttonNext;
	private JButton buttonPrevious;
	private JLabel labelImage;

	private JSIHyperlinkLabel hyperlink;
	private String buttonLabel = JSIExternalStrings.ok;
	private String buttonNextLabel = "";
	private String buttonPreviousLabel = "";
	private String standardFont = "Arial";
	private int standardSize = 12;
	private Color textColor = Color.BLACK;
	private Color bgColor = Color.WHITE;

	private boolean editable = true;
	private boolean enabled = true;
	private boolean alwaysOnTop = true;
	private boolean resizable = false;
	// Flag whether About-Dialog is open or not
	private boolean isOpen = false;
	// Flag whether display other Button or not
	private boolean displayOtherButton = false;
	// Flag whether displaying Text or not
	boolean setText = true;
	// Flag whether something has changed
	private boolean hasChanged = true;
	// Flag whether using a JTextArea or a JLabel
	private boolean useTextArea;

	/*********** Constructors ***********/

	/**
	 * Default-Constructor
	 * 
	 * @param gc
	 *            GameController
	 * @param useTextArea
	 * @param resizable
	 */
	public JSITextWindow(JSIGameController gc, boolean useTextArea,
			boolean resizable) {
		this(gc, "", "", 100, 100, useTextArea, resizable);
	}

	/**
	 * Constructor
	 * 
	 * @param gc
	 *            GameController
	 * @param title
	 *            Initial Window Title
	 * @param text
	 *            Initial Window Text
	 * @param useTextArea
	 * @param resizable
	 */
	public JSITextWindow(JSIGameController gc, String title, String text,
			boolean useTextArea, boolean resizable) {
		this(gc, title, text, 100, 100, useTextArea, resizable);
	}

	/**
	 * Constructor
	 * 
	 * @param gc
	 *            GameController
	 * @param title
	 *            Initial Window Title
	 * @param text
	 *            Initial Window Text
	 * @param preferredSize
	 *            Preferred Window Size
	 * @param useTextArea
	 * @param resizable
	 */
	public JSITextWindow(JSIGameController gc, String title, String text,
			Dimension preferredSize, boolean useTextArea, boolean resizable) {
		this(gc, title, text, preferredSize.width, preferredSize.height,
				useTextArea, resizable);
	}

	/**
	 * Constructor
	 * 
	 * @param gc
	 *            GameController
	 * @param title
	 *            Initial Window Title
	 * @param text
	 *            Initial Window Text
	 * @param width
	 * @param height
	 * @param useTextArea
	 * @param resizable
	 */
	public JSITextWindow(JSIGameController gc, String title, String text,
			int width, int height, boolean useTextArea, boolean resizable) {
		this.gc = gc;
		this.mainFrame = gc.getMainFrame();
		this.currentTitle = title;
		this.currentText = text;
		this.resizable = resizable;
		if ((width > 0) && (height > 0)) {
			this.size = new Dimension(width, height);
		} else {
			this.size = new Dimension(100, 100);
		}
		this.useTextArea = useTextArea;
		initGUI(standardFont, standardSize);
	}

	/*********** Functions ***********/

	/**
	 * Initializes the GUI
	 * 
	 * @param font
	 *            Font
	 * @param fontSize
	 *            Font Size
	 */
	private void initGUI(String font, int fontSize) {
		if ((font == null) || font.isEmpty() || (fontSize <= 0)) {
			font = standardFont;
		}

		// Image
		labelImage = null;
		if (icon != null) {
			labelImage = new JLabel(icon);
		}

		dialog = new JDialog(mainFrame, currentTitle);
		overPanel = new JPanel();
		title = new JLabel(currentTitle);
		title.setFont(new Font(font, Font.BOLD, 18));
		if (useTextArea) {
			textArea = new JTextArea();
			textArea.setText(currentText);
			textArea.setEnabled(enabled);
			textArea.setEditable(editable);
			textArea.setFont(new Font(font, Font.BOLD, fontSize));
			textArea.setForeground(textColor);
			textArea.setBackground(bgColor);
		} else {
			label = new JLabel();
			label.setText(currentText);
			label.setFont(new Font(font, Font.PLAIN, fontSize));
		}

		// Button OK
		buttonOk = new JButton(buttonLabel);
		// Button next
		buttonNext = new JButton(buttonNextLabel);
		// Button previous
		buttonPrevious = new JButton(buttonPreviousLabel);

		if (useTextArea) {
			scrollPane = new JScrollPane(textArea);
		} else {
			scrollPane = new JScrollPane(label);
		}

		// Initiate GridBagLayout
		gbl = new GridBagLayout();
		overPanel.setLayout(gbl);
		int i = 0;

		// Insets
		int inset1 = 3;
		int inset2 = 8;
		int inset3 = 3;
		int inset4 = 8;

		// Add Components to the GridBagLayout
		// Title
		addComponentToGridBagLayout(overPanel, gbl, title, 0, i++, 2, 1, 0.0,
				0.0, inset1, inset2, inset3, inset4);
		// If Image loaded successfully: Display Image
		if (labelImage != null) {
			addComponentToGridBagLayout(overPanel, gbl, labelImage, 0, i++, 4,
					1, 0.0, 0.0, inset1, inset2, inset3, inset4);
		}
		if (setText) {
			// Text
			addComponentToGridBagLayout(overPanel, gbl, scrollPane, 0, i++, 5,
					1, 1.0, 1.0, inset1, inset2, inset3, inset4);
			// HyperlinkLabel
			if (hyperlink != null) {
				addComponentToGridBagLayout(overPanel, gbl, hyperlink, 0, i++,
						2, 1, 0.0, 0.0, inset1, inset2, inset3, inset4);
			}
		}
		// Buttons
		addComponentToGridBagLayout(overPanel, gbl, buttonOk, 0, i, 1, 1, 0.0,
				0.0, inset1, inset2, inset3, inset4);
		if (displayOtherButton) {
			addComponentToGridBagLayout(overPanel, gbl, buttonPrevious, 1, i,
					1, 1, 0.0, 0.0, 0, 0, 0, 0);
			addComponentToGridBagLayout(overPanel, gbl, buttonNext, 2, i, 1, 1,
					0.0, 0.0, inset1, inset2, inset3, inset4);
		}

		if (useTextArea) {
			textArea.setCaretPosition(0);
		}

		dialog.add(overPanel);
		dialog.setVisible(false);
		dialog.setResizable(resizable);
		dialog.setPreferredSize(size);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setAlwaysOnTop(alwaysOnTop);
		dialog.pack();
		dialog.setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width / 2)
						- (dialog.getWidth() / 2), (Toolkit.getDefaultToolkit()
						.getScreenSize().height / 2) - (dialog.getHeight() / 2));
		buttonOk.requestFocus();

		// Inform the Game Controller to set new ButtonListener
		gc.addNewTextWindowButtonOkListener();
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
	 * Returns the current Title
	 * 
	 * @return the current Title
	 */
	public String getTitle() {
		return currentTitle;
	}

	/**
	 * Returns the current Text
	 * 
	 * @return the current Text
	 */
	public String getText() {
		return currentText;
	}

	/**
	 * Returns the dialog
	 * 
	 * @return the dialog
	 */
	public JDialog getDialog() {
		return dialog;
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
	 * Returns the Next-Button
	 * 
	 * @return the Next-Button
	 */
	public JButton getButtonNext() {
		return buttonNext;
	}

	/**
	 * Returns the Previous-Button
	 * 
	 * @return the Previous-Button
	 */
	public JButton getButtonPrevious() {
		return buttonPrevious;
	}

	/*********** Setter ***********/

	/**
	 * Sets an Icon
	 * 
	 * @param icon
	 *            Icon to set
	 * @param setText
	 */
	public void setIcon(Icon icon, boolean setText) {
		this.icon = icon;
		this.setText = setText;
		hasChanged = true;
	}

	/**
	 * Sets a Title
	 * 
	 * @param title
	 *            New Title
	 */
	public void setTitle(String title) {
		if (!(title == null)) {
			this.currentTitle = title;
			hasChanged = true;
		}
	}

	/**
	 * Appends the current Text
	 * 
	 * @param text
	 *            Text to append
	 */
	public void appendText(String text) {
		if (!(text == null)) {
			this.currentText += text;
			hasChanged = true;
		}
	}

	/**
	 * Sets a Text
	 * 
	 * @param text
	 *            New Text
	 */
	public void setText(String text) {
		if (!(text == null)) {
			this.currentText = text;
			hasChanged = true;
		}
	}

	/**
	 * Sets the Dimension
	 * 
	 * @param width
	 * @param height
	 */
	public void setDimension(int width, int height) {
		if ((width >= 0) && (height >= 0)) {
			this.size = new Dimension(width, height);
			hasChanged = true;
		}
	}

	/**
	 * @param dim
	 *            New Size
	 */
	public void setDimension(Dimension dim) {
		if (!(dim == null)) {
			this.size = dim;
			hasChanged = true;
		}
	}

	/**
	 * Sets a Text Color (only works if the TextArea is enabled)
	 * 
	 * @param color
	 *            the new Text Color
	 */
	public void setTextColor(Color color) {
		if (color != null) {
			textColor = color;
			hasChanged = true;
		}
	}

	/**
	 * Sets a Background Color (only works if the TextArea is enabled)
	 * 
	 * @param color
	 *            the new Background Color
	 */
	public void setBackgroundColor(Color color) {
		if (color != null) {
			bgColor = color;
			hasChanged = true;
		}
	}

	/**
	 * Sets the TextSize
	 * 
	 * @param size
	 *            TextSize
	 */
	public void setTextSize(int size) {
		if (size > 0) {
			standardSize = size;
			hasChanged = true;
		}
	}

	/**
	 * Creates the GUI
	 * 
	 * @param ok
	 *            Label of the Ok-Button
	 */
	public void setButtonLabel(String ok) {
		if (!(ok == null) && !ok.isEmpty()) {
			buttonLabel = ok;
			hasChanged = true;
		}
	}

	/**
	 * Sets Button Next's Label
	 * 
	 * @param ok
	 *            Label of the next-Button
	 */
	public void setButtonNextLabel(String ok) {
		if (!(ok == null) && !ok.isEmpty()) {
			buttonNextLabel = ok;
			hasChanged = true;
		}
	}

	/**
	 * Sets Button Previous' Label
	 * 
	 * @param ok
	 *            Label of the previous-Button
	 */
	public void setButtonPreviousLabel(String ok) {
		if (!(ok == null) && !ok.isEmpty()) {
			buttonPreviousLabel = ok;
			hasChanged = true;
		}
	}

	/**
	 * Displays the other Button or not
	 * 
	 * @param display
	 */
	public void displayOtherButton(boolean display) {
		displayOtherButton = display;
	}

	/**
	 * Sets the TextArea (not) editable
	 * 
	 * @param bool
	 */
	public void setEditable(boolean bool) {
		editable = bool;
		hasChanged = true;
	}

	/**
	 * Sets the TextArea (not) enabled
	 * 
	 * @param bool
	 */
	public void setEnabled(boolean bool) {
		enabled = bool;
		hasChanged = true;
	}

	/**
	 * The Window will (not) always be on Top
	 * 
	 * @param bool
	 */
	public void alwaysOnTop(boolean bool) {
		alwaysOnTop = bool;
		hasChanged = true;
	}

	/**
	 * Sets the GUI (in-)visible
	 * 
	 * @param setVisible
	 */
	public void setVisible(boolean setVisible) {
		if (setVisible && !isOpen) {
			buttonOk.requestFocus();
			gc.pauseGame("");
			// Initialise GUI if something has changed
			if (hasChanged) {
				initGUI(standardFont, standardSize);
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

	/**
	 * Creates the GUI
	 * 
	 * @param font
	 *            Font
	 * @param fontSize
	 *            Font Size
	 * @param buttonLabel
	 *            Label of the Ok-Button
	 */
	public void setUpNewGUI(String font, int fontSize, String buttonLabel) {
		setButtonLabel(buttonLabel);
		initGUI(font, fontSize);
	}

	/**
	 * Resets the HyperlinkLabel
	 */
	public void resetHyperlinkLabel() {
		hyperlink = null;
	}

	/**
	 * Activates the HyperlinkLabel
	 * 
	 * @param text
	 * @param uri
	 */
	public void setHyperlinkLabel(String text, String uri) {
		try {
			hyperlink = new JSIHyperlinkLabel(text, new URI(uri));
		} catch (URISyntaxException e) {
			hyperlink = (JSIHyperlinkLabel) new JLabel(uri);
		}
	}

}
