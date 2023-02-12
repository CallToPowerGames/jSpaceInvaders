/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.JSIGameController;
import data.JSIDataStorage;
import data.JSIExternalStrings;
import data.JSIDataStorage.Mode;

/**
 * ModeChooser
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIModeChooser {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private JSIGameController gc;

	private JFrame mainFrame;

	private JDialog dialog;
	private JLabel labelChose;

	private JButton buttonMode1;
	private JButton buttonMode2;
	private JButton buttonMode3;
	private JButton buttonMode4;
	private JButton buttonMode5;
	private JButton buttonMode6;
	private JButton buttonMode7;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param gc
	 *            GameController
	 */
	public JSIModeChooser(JSIGameController gc) {
		this.gc = gc;
		this.mainFrame = gc.getMainFrame();

		initGUI();
	}

	/*********** Functions ***********/

	/**
	 * Initialises the GUI
	 */
	private void initGUI() {
		dialog = new JDialog(this.mainFrame, JSIExternalStrings.choseAMode);
		dialog.setLayout(new GridLayout(9, 1));

		labelChose = new JLabel(JSIExternalStrings.choseAMode + ":");
		buttonMode1 = new JButton(ds.getModeToString(Mode.WTF));
		buttonMode2 = new JButton(ds.getModeToString(Mode.NOOBY));
		buttonMode3 = new JButton(ds.getModeToString(Mode.EASY));
		buttonMode4 = new JButton(ds.getModeToString(Mode.NORMAL));
		buttonMode5 = new JButton(ds.getModeToString(Mode.HARD));
		buttonMode6 = new JButton(ds.getModeToString(Mode.INSANE));
		buttonMode7 = new JButton(JSIExternalStrings.quitGame);

		dialog.add(labelChose);
		dialog.add(buttonMode2); // Nooby-Mode
		dialog.add(buttonMode3); // Easy-Mode
		dialog.add(buttonMode4); // Normal-Mode
		dialog.add(buttonMode5); // Hard-Mode
		dialog.add(buttonMode6); // Insane-Mode
		dialog.add(buttonMode1); // WTF-Mode
		dialog.add(new JLabel(""));
		dialog.add(buttonMode7); // WTF-Mode

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setAlwaysOnTop(true);
		dialog.setResizable(false);
		dialog.pack();
		dialog.setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width / 2)
						- (dialog.getWidth() / 2), (Toolkit.getDefaultToolkit()
						.getScreenSize().height / 2) - (dialog.getHeight() / 2));
	}

	/**
	 * Makes the GUI visible
	 */
	public void show() {
		dialog.setVisible(true);
		dialog.setLocationRelativeTo(null);

		gc.setWaitingForKeyPress();
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
	 * Returns Button Mode 1
	 * 
	 * @return Button Mode 2
	 */
	public JButton getButtonMode1() {
		return buttonMode1;
	}

	/**
	 * Returns Button Mode 2
	 * 
	 * @return Button Mode 2
	 */
	public JButton getButtonMode2() {
		return buttonMode2;
	}

	/**
	 * Returns Button Mode 3
	 * 
	 * @return Button Mode 3
	 */
	public JButton getButtonMode3() {
		return buttonMode3;
	}

	/**
	 * Returns Button Mode 4
	 * 
	 * @return Button Mode 4
	 */
	public JButton getButtonMode4() {
		return buttonMode4;
	}

	/**
	 * Returns Button Mode 5
	 * 
	 * @return Button Mode 5
	 */
	public JButton getButtonMode5() {
		return buttonMode5;
	}

	/**
	 * Returns Button Mode 6
	 * 
	 * @return Button Mode 6
	 */
	public JButton getButtonMode6() {
		return buttonMode6;
	}

	/**
	 * Returns Button Mode 7
	 * 
	 * @return Button Mode 7
	 */
	public JButton getButtonMode7() {
		return buttonMode7;
	}

}
