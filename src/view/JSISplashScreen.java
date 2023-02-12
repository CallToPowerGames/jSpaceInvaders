/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * Splashscreen
 * 
 * @author Denis Meyer (CallToPower)
 */
@SuppressWarnings("serial")
public class JSISplashScreen extends JWindow {
	private JLabel imageLabel;
	private JPanel southPanel;

	private JProgressBar progressBar;

	private ImageIcon imageIcon;

	private BorderLayout borderLayout;
	private FlowLayout flowLayout;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param imageIcon
	 */
	public JSISplashScreen(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;

		borderLayout = new BorderLayout();
		imageLabel = new JLabel();
		southPanel = new JPanel();
		flowLayout = new FlowLayout();
		progressBar = new JProgressBar();

		try {
			initLayout();
		} catch (Exception ex) {
			// Do nothing
		}
	}

	/*********** Functions ***********/

	/**
	 * Initializes the Layout
	 */
	private void initLayout() {
		// Set the (upper) Image
		if (imageIcon != null) {
			imageLabel.setIcon(imageIcon);
		}

		// South Panel
		southPanel.setLayout(flowLayout);
		southPanel.setBackground(Color.BLACK);
		southPanel.add(progressBar, null);

		// This
		setLayout(borderLayout);
		add(imageLabel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		// Pack
		pack();
	}

	/*********** Getter ***********/

	/**
	 * Returns the Progress Value
	 * 
	 * @return the Progress Value
	 */
	public int getProgress() {
		return progressBar.getValue();
	}

	/*********** Setter ***********/

	/**
	 * Sets the Progress Maximum
	 * 
	 * @param maxProgress
	 */
	public void setProgressMax(int maxProgress) {
		progressBar.setMaximum(maxProgress);
	}

	/**
	 * Sets the ProgressBar Value
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		final int theProgress = progress;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progressBar.setValue(theProgress);
			}
		});
	}

	/**
	 * Sets the ProgressBar Message + Value
	 * 
	 * @param message
	 * @param progress
	 */
	public void setProgress(String message, int progress) {
		final int theProgress = progress;
		final String theMessage = message;
		setProgress(progress);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				progressBar.setValue(theProgress);
				setMessage(theMessage);
			}
		});
	}

	/**
	 * Sets the SplashScreen (in-)visible
	 * 
	 * @param b
	 */
	public void setScreenVisible(boolean b) {
		final boolean bool = b;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(bool);
			}
		});
	}

	/**
	 * Sets a new Message
	 * 
	 * @param message
	 */
	private void setMessage(String message) {
		if (message == null) {
			message = "";
			progressBar.setStringPainted(false);
		} else {
			progressBar.setStringPainted(true);
		}
		progressBar.setString(message);
	}

}
