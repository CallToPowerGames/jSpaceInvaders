/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import data.JSIDataStorage;
import data.JSIExternalStrings;

/**
 * MenuBar
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIMenuBar {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();

	// List of MenuItems
	private ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();
	// The Frame to add the MenuBar to
	private JFrame frame;

	private JMenuBar menuBar;
	private JMenu programMenu;
	private JMenuItem aboutAction;
	private JMenuItem exitAction;
	private JMenu optionsMenu;
	private JMenuItem newGameAction;
	private JMenuItem consoleAction;
	private JMenuItem pauseAction;
	private JMenuItem highscoreAction;
	private JMenu helpMenu;
	private JMenuItem spaceInvadersHistoryAction;
	private JMenuItem gameplayAction;
	private JMenuItem controlsAction;
	private JMenuItem acknowledgementsAndCopyrightAction;
	private JMenuItem changeLogAction;
	private JMenu moreMenu;
	private JMenuItem cavePaintingAction;
	private JMenuItem makingOfTetrisAction;
	private JMenuItem steampunkAction;
	private JMenuItem masterplanAction;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param frame
	 */
	public JSIMenuBar(JFrame frame) {
		setFrame(frame);
	}

	/*********** Functions ***********/

	/**
	 * Adds the MenuBar to the given Frame
	 */
	public void addMenuBar() {
		if (frame != null) {
			menuBar = new JMenuBar();

			// Programm-Menu
			if (ds.is("Mac")) {
				programMenu = new JMenu(JSIExternalStrings.menuHelp);
			} else {
				programMenu = new JMenu(JSIExternalStrings.menujSpaceInvaders);
			}
			// Programm-Menu - About
			aboutAction = new JMenuItem(JSIExternalStrings.menuAbout);
			// Programm-Menu - Exit
			exitAction = new JMenuItem(JSIExternalStrings.menuQuit);
			if (!ds.is("Mac")) {
				// Add to Program Menu
				programMenu.add(aboutAction);
				programMenu.addSeparator();
				programMenu.add(exitAction);
			}
			menuItems.add(aboutAction);
			menuItems.add(exitAction);

			// Options-Menu
			optionsMenu = new JMenu(JSIExternalStrings.menuOptions);
			// Options-Menu - New Game
			newGameAction = new JMenuItem(JSIExternalStrings.menuNewGame);
			// Options-Menu - Console
			consoleAction = new JMenuItem(JSIExternalStrings.menuConsole);
			// Options-Menu - Pause Game
			pauseAction = new JMenuItem(JSIExternalStrings.menuPauseGame);
			// Options-Menu - Highscore
			highscoreAction = new JMenuItem(JSIExternalStrings.menuHighscore);
			// Add to Options Menu
			optionsMenu.add(newGameAction);
			optionsMenu.add(consoleAction);
			optionsMenu.addSeparator();
			optionsMenu.add(pauseAction);
			optionsMenu.addSeparator();
			optionsMenu.add(highscoreAction);

			menuItems.add(newGameAction);
			menuItems.add(consoleAction);
			menuItems.add(pauseAction);
			menuItems.add(highscoreAction);

			// Programm-Menu
			helpMenu = new JMenu(JSIExternalStrings.menuHelp);
			// Programm-Menu - History
			spaceInvadersHistoryAction = new JMenuItem(
					JSIExternalStrings.menuHistory);
			// Programm-Menu - Gameplay
			gameplayAction = new JMenuItem(JSIExternalStrings.menuGameplay);
			// Programm-Menu - Controls
			controlsAction = new JMenuItem(JSIExternalStrings.menuControls);
			// Programm-Menu - Acknowledgements and Copyright
			acknowledgementsAndCopyrightAction = new JMenuItem(
					JSIExternalStrings.menuAcknowledgementsandCopyright);
			// Programm-Menu - Changelog
			changeLogAction = new JMenuItem(JSIExternalStrings.menuChangeLog);

			menuItems.add(spaceInvadersHistoryAction);
			menuItems.add(gameplayAction);
			menuItems.add(controlsAction);
			menuItems.add(acknowledgementsAndCopyrightAction);
			menuItems.add(changeLogAction);

			// More-Menu
			moreMenu = new JMenu(JSIExternalStrings.menuMore);
			// More-Menu - Cave Painting
			cavePaintingAction = new JMenuItem(
					JSIExternalStrings.image_cavePainting_name);
			// More-Menu - Making of Tetris
			makingOfTetrisAction = new JMenuItem(
					JSIExternalStrings.image_makingOfTetris_name);
			// More-Menu - Steampunk
			steampunkAction = new JMenuItem(
					JSIExternalStrings.image_steampunk_name);
			// More-Menu - Masterplan
			masterplanAction = new JMenuItem(
					JSIExternalStrings.image_masterplan_name);
			moreMenu.add(cavePaintingAction);
			moreMenu.add(makingOfTetrisAction);
			moreMenu.add(steampunkAction);
			moreMenu.add(masterplanAction);

			menuItems.add(cavePaintingAction);
			menuItems.add(makingOfTetrisAction);
			menuItems.add(steampunkAction);
			menuItems.add(masterplanAction);

			// Add to Help Menu
			helpMenu.add(controlsAction);
			helpMenu.addSeparator();
			helpMenu.add(spaceInvadersHistoryAction);
			helpMenu.add(gameplayAction);
			helpMenu.add(moreMenu);
			helpMenu.addSeparator();
			helpMenu.add(acknowledgementsAndCopyrightAction);
			helpMenu.addSeparator();
			helpMenu.add(changeLogAction);

			// Add to MenuBar
			if (ds.is("Mac")) {
				menuBar.add(optionsMenu);
			} else {
				menuBar.add(programMenu);
				menuBar.add(optionsMenu);
			}
			menuBar.add(helpMenu);

			// Set the MenuBar to the Frame
			frame.setJMenuBar(menuBar);
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns the JMenuBar
	 * 
	 * @return JMenuBar
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * Returns the MenuItems
	 * 
	 * @return MenuItems
	 */
	public ArrayList<JMenuItem> getMenuItems() {
		return menuItems;
	}

	/**
	 * Returns JMenuItem aboutAction
	 * 
	 * @return JMenuItem aboutAction
	 */
	public JMenuItem getAboutAction() {
		return aboutAction;
	}

	/**
	 * Returns JMenuItem exitAction
	 * 
	 * @return JMenuItem exitAction
	 */
	public JMenuItem getExitAction() {
		return exitAction;
	}

	/**
	 * Returns JMenuItem newGameAction
	 * 
	 * @return JMenuItem newGameAction
	 */
	public JMenuItem getNewGameAction() {
		return newGameAction;
	}

	/**
	 * Returns JMenuItem newGameAction
	 * 
	 * @return JMenuItem newGameAction
	 */
	public JMenuItem getConsoleAction() {
		return consoleAction;
	}

	/**
	 * Returns JMenuItem pauseAction
	 * 
	 * @return JMenuItem pauseAction
	 */
	public JMenuItem getPauseAction() {
		return pauseAction;
	}

	/**
	 * Returns JMenuItem highscoreAction
	 * 
	 * @return JMenuItem highscoreAction
	 */
	public JMenuItem getHighscoreAction() {
		return highscoreAction;
	}

	/**
	 * Returns JMenuItem spaceInvadersHistoryAction
	 * 
	 * @return JMenuItem spaceInvadersHistoryAction
	 */
	public JMenuItem getSpaveInvadersHistoryAction() {
		return spaceInvadersHistoryAction;
	}

	/**
	 * Returns JMenuItem gameplayAction
	 * 
	 * @return JMenuItem gameplayAction
	 */
	public JMenuItem getGameplayAction() {
		return gameplayAction;
	}

	/**
	 * Returns JMenuItem controlsAction
	 * 
	 * @return JMenuItem controlsAction
	 */
	public JMenuItem getControlsAction() {
		return controlsAction;
	}

	/**
	 * Returns JMenuItem acknowledgementsAndCopyrightAction
	 * 
	 * @return JMenuItem acknowledgementsAndCopyrightAction
	 */
	public JMenuItem getAcknowledgementsAndCopyrightAction() {
		return acknowledgementsAndCopyrightAction;
	}

	/**
	 * Returns JMenuItem changeLogAction
	 * 
	 * @return JMenuItem changeLogAction
	 */
	public JMenuItem getChangeLogAction() {
		return changeLogAction;
	}

	/**
	 * Returns JMenuItem cavePaintingAction
	 * 
	 * @return JMenuItem cavePaintingAction
	 */
	public JMenuItem getCavePaintingAction() {
		return cavePaintingAction;
	}

	/**
	 * Returns JMenuItem makingOfTetrisAction
	 * 
	 * @return JMenuItem makingOfTetrisAction
	 */
	public JMenuItem getMakingOfTetrisAction() {
		return makingOfTetrisAction;
	}

	/**
	 * Returns JMenuItem steampunkAction
	 * 
	 * @return JMenuItem steampunkAction
	 */
	public JMenuItem getSteampunkAction() {
		return steampunkAction;
	}

	/**
	 * Returns JMenuItem masterplanAction
	 * 
	 * @return JMenuItem masterplanAction
	 */
	public JMenuItem getMasterplanAction() {
		return masterplanAction;
	}

	/*********** Setter ***********/

	/**
	 * Sets the Frame
	 * 
	 * @param frame
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
