/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import data.JSIDataStorage;
import data.JSIExternalStrings;
import data.JSISoundCache;
import data.JSIImageSpriteCache;
import data.JSIDataStorage.Mode;

import view.JSIAboutWindow;
import view.JSIConsole;
import view.JSIField;
import view.JSIMessage;
import view.JSIModeChooser;
import view.JSITextWindow;

import model.JSIAlienEntity;
import model.JSIAlienType;
import model.JSIEntity;
import model.JSIShipEntity;
import model.JSIShipType;
import model.JSIShotEntity;
import model.JSIShotType;

/**
 * Game Controller
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIGameController {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	private static JSIImageSpriteCache sc = JSIImageSpriteCache
			.getImageSpriteCache();
	private static JSISoundCache soc = JSISoundCache.getSoundCache();
	private JSIHighscoreController hc;

	// Graphical Context
	private Graphics2D g;

	private JSIField field;
	private JSIAboutWindow about;
	private JSIConsole console;
	private JSIModeChooser mc;
	private JSITextWindow tw;

	private JSIMessage messageDialogue;
	private JSIKeyMouseController kmc;
	private WindowAdapter fieldWindowAdapter;

	// Player's Ship
	private JSIShipEntity ship;
	private JSIAlienEntity alienBig;

	// List of all Entities in the Game
	private ArrayList<JSIEntity> entities;
	// List of Speeds for Pause Mode
	private ArrayList<Double> entityDxs;
	private ArrayList<Double> entityDys;
	// List of Entities that need to be removed this Loop
	private ArrayList<JSIEntity> removeList;

	// For the Output
	private String _mode;
	private String _score;
	private String _lives;
	// Output Message
	private String message;
	// Press any Key Message
	private String pressAnyKey;
	// Output Message Cache
	private String message_cache;

	// Score
	private double score;
	// The Time at latest Shot has been fired by the Ship
	private double lastShipFire;
	// The Time at latest Shot has been fired by an Alien
	private double lastAlienFire;
	// The Time at latest Shot has been fired by the AlienBig
	private double lastAlienBigFire;

	// Number of Aliens on the Field
	private int alienCount;

	// For the spawn of the Invaders
	int movedDown;

	// Flag whether everything has been initialized successfully
	private boolean initializedFirst;
	private boolean initializedSecond;
	private boolean initializedThird;
	// True whether the Game Loop is currently running
	private boolean gameLoopRunning;
	// If Game Logic needs to be applied this Loop
	private boolean updateThisLoop;
	// Flag whether a new Game Dialogue is opened or not
	private boolean newGameDialogueOpen;
	// Flag whether About is opened or not
	private boolean aboutOpen;
	// Flag whether Console is opened or not
	private boolean consoleOpen;
	// Flag whether TextWindow is opened or not
	private boolean textWindowOpen;
	// Flag whether in PauseMode or not
	private boolean inPauseMode;
	// Flag whether displaying Start Screen or not
	private boolean displayingStartScreen;
	// Flag whether BigInvader has already been spawned
	private boolean bigInvaderSpawned;
	// Flag whether BigInvader is dead
	private boolean bigInvaderDead;
	// Flag whether the has been resetted
	private boolean resettedGame;

	/* Cheats */
	private JSIAlienEntity alienEntity;
	private boolean doubleFirePower;
	private boolean insaneFirePower;
	private boolean unbreakable;
	private boolean disguised;
	private ArrayList<Color> al;
	private int heightDifference;
	private int lolfumodeBackgroundColorCounter;
	private Color lolfumodeBackgroundColor;
	private boolean lolfumode;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 */
	public JSIGameController() {
		this(true);
	}

	/**
	 * Constructor
	 * 
	 * @param cacheSelf
	 *            if cashing itself or not
	 */
	public JSIGameController(boolean cacheSelf) {
		field = new JSIField(this);

		initializedFirst = false;
		initializedSecond = false;
		initializedThird = false;

		// Cashing
		if (cacheSelf) {
			initFirst();
			initSecond();
			initThird();
		}

		getMainFrame().setTitle(JSIExternalStrings.jSpaceInvadersMainTitle);
	}

	/*********** Initialization ***********/

	/**
	 * 1st Cache
	 */
	public void initFirst() {
		initLists();
		initVariables();
		initConstantObjects();
		initializedFirst = true;
	}

	/**
	 * 2nd Cache
	 */
	public void initSecond() {
		initAboutWindow();
		initTextWindow();
		initConsole();
		initializedSecond = true;
	}

	/**
	 * 3rd Cache
	 */
	public void initThird() {
		hc = new JSIHighscoreController(this);

		initListener();
		initMnemonics();
		initializedThird = true;
	}

	/**
	 * Initializes the KeyListener
	 */
	private void initListener() {
		/** Field */
		fieldWindowAdapter = new WindowAdapter() {

			/**
			 * If Field-Window closing
			 */
			public void windowClosing(WindowEvent e) {
				quit(true);
			}

			/**
			 * If Field-Window activated
			 */
			public void windowActivated(WindowEvent arg0) {
				message = message_cache;
				resumeGame();
			}

			/**
			 * If Field-Window deactivated
			 */
			public void windowDeactivated(WindowEvent arg0) {
				if (gameRunning()) {
					pauseGame(JSIExternalStrings.MESSAGE_WINDOW_DEACTIVATED);
				} else {
					pauseGame(message);
				}
				message_cache = message;
			}
		};
		setFieldWindowListener();

		// Add Field-KeyAdapter for the Console
		field.getFrame().addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent ke) {
				// On Mac: Meta+SHIFT
				if (ds.is("Mac")) {
					int onmask = KeyEvent.META_DOWN_MASK
							| KeyEvent.SHIFT_DOWN_MASK;
					if ((ke.getModifiersEx() & onmask) == onmask) {
						showConsole();
					}
					// On other OS': CTRL+SHIFT
				} else {
					int onmask = KeyEvent.CTRL_DOWN_MASK
							| KeyEvent.SHIFT_DOWN_MASK;
					if ((ke.getModifiersEx() & onmask) == onmask) {
						showConsole();
					}
				}
			}
		});

		// Add MenuBar Listeners
		if (!ds.is("Mac")) {
			// If Mouse hovers over the MenuBar: Pause
			field.getMenu().getMenuBar()
					.addMouseMotionListener(new MouseAdapter() {

						public void mouseMoved(MouseEvent arg0) {
							if (gameRunning()) {
								pauseGame(JSIExternalStrings.MESSAGE_WINDOW_DEACTIVATED);
							} else {
								pauseGame(message);
							}
							message_cache = message;
						}
					});

			// If Mouse hovers over the Field: Un-Pause
			field.addMouseMotionListener(new MouseAdapter() {

				public void mouseMoved(MouseEvent arg0) {
					if (field.isFocusOwner()) {
						message = message_cache;
						resumeGame();
					}
				}
			});
		}

		field.addKeyListener(kmc.getKeyInputHandler());
		field.addMouseListener(kmc.getMouseInputHandler());
		field.addMouseMotionListener(kmc.getMouseInputMotionHandler());

		// About
		field.getMenu().getAboutAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showAbout();
					}
				});

		// Exit
		field.getMenu().getExitAction().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				quit(true);
			}
		});

		// New Game
		field.getMenu().getNewGameAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						final String pause = JSIExternalStrings.startNewGame;
						final String pause2 = JSIExternalStrings.abortAndStartNewGame;
						if (!aboutOpen() && !textWindowOpen() && !consoleOpen()) {
							field.getFrame().removeWindowListener(
									fieldWindowAdapter);
							if (gameRunning()) {
								pauseGame(pause);
								if (JOptionPane.showConfirmDialog(
										field.getFrame(), pause2, pause,
										JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
									setLivesTo0();
									notifyDeath();
								}
								resumeGame();
							} else {
								showModeChooser();
							}
							field.getFrame().addWindowListener(
									fieldWindowAdapter);
						}
					}
				});

		// Console
		field.getMenu().getConsoleAction().setVisible(false);
		setMnemonic(field.getMenu().getConsoleAction(), 'c', false);
		field.getMenu().getConsoleAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showConsole();
					}
				});

		// Pause
		field.getMenu().getPauseAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						if (inPauseMode()) {
							resumeGame();
						} else {
							pauseGame(JSIExternalStrings.MESSAGE_PAUSE);
						}
					}
				});

		// Highscore
		field.getMenu().getHighscoreAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showHighscoreTable();
					}
				});

		// History
		field.getMenu().getSpaveInvadersHistoryAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showSpaceInvadersHistory();
					}
				});

		// Gameplay
		field.getMenu().getGameplayAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showGameplay();
					}
				});

		// CavePainting
		field.getMenu().getCavePaintingAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showCavePainting();
					}
				});

		// MakingOfTetris
		field.getMenu().getMakingOfTetrisAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showMakingOfTetris();
					}
				});

		// Steampunk
		field.getMenu().getSteampunkAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showSteampunk();
					}
				});

		// Masterplan
		field.getMenu().getMasterplanAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showMasterplan();
					}
				});

		// Controls
		field.getMenu().getControlsAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showControls();
					}
				});

		// Acknowledgements and Copyright
		field.getMenu().getAcknowledgementsAndCopyrightAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showAcknowledgementsAndCopyright();
					}
				});

		// Changelog
		field.getMenu().getChangeLogAction()
				.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						showChangeLog();
					}
				});

		/** ModeChooser */

		// Actionlistener
		ActionListener modeChooserActionListener = new ActionListener() {

			public void actionPerformed(final ActionEvent ae) {
				modeChooserAction(ae);
			}
		};
		// Keylistener
		KeyAdapter modeChooserKeyListener = new KeyAdapter() {

			public void keyPressed(final KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					modeChooserAction(ke);
				}
			}
		};
		// Set Action- and KeyListener for every Button
		mc.getButtonMode1().addActionListener(modeChooserActionListener);
		mc.getButtonMode1().addKeyListener(modeChooserKeyListener);
		mc.getButtonMode2().addActionListener(modeChooserActionListener);
		mc.getButtonMode2().addKeyListener(modeChooserKeyListener);
		mc.getButtonMode3().addActionListener(modeChooserActionListener);
		mc.getButtonMode3().addKeyListener(modeChooserKeyListener);
		mc.getButtonMode4().addActionListener(modeChooserActionListener);
		mc.getButtonMode4().addKeyListener(modeChooserKeyListener);
		mc.getButtonMode5().addActionListener(modeChooserActionListener);
		mc.getButtonMode5().addKeyListener(modeChooserKeyListener);
		mc.getButtonMode6().addActionListener(modeChooserActionListener);
		mc.getButtonMode6().addKeyListener(modeChooserKeyListener);
		mc.getButtonMode7().addActionListener(modeChooserActionListener);
		mc.getButtonMode7().addKeyListener(modeChooserKeyListener);

		mc.getDialog().addWindowListener(new WindowAdapter() {

			/**
			 * If Field-Window closing
			 */
			public void windowClosing(WindowEvent e) {
				mc.getDialog().setVisible(false);
				newGameDialogueOpen = false;
				setWaitingForKeyPress();
			}
		});

		/* AboutWindow */
		addNewAboutButtonOkListener();

		/* TextWindow */
		addNewTextWindowButtonOkListener();

		/* HighscoreTable */
		addNewHighscoreListener();
	}

	/**
	 * Initializes the Lists
	 */
	private void initLists() {
		entities = new ArrayList<JSIEntity>();
		removeList = new ArrayList<JSIEntity>();
		entityDxs = new ArrayList<Double>();
		entityDys = new ArrayList<Double>();
	}

	/**
	 * Initializes the Variables
	 */
	private void initVariables() {
		message = JSIExternalStrings.MESSAGE_WELCOME + " ";
		pressAnyKey = JSIExternalStrings.MESSAGE_PRESS_ANY_KEY + " ";
		message_cache = message;
		gameLoopRunning = false;
		updateThisLoop = false;
		inPauseMode = false;
		bigInvaderSpawned = false;
		bigInvaderDead = false;
		doubleFirePower = false;
		insaneFirePower = false;
		unbreakable = false;
		disguised = false;
		lolfumode = false;
		lolfumodeBackgroundColorCounter = 0;
		lolfumodeBackgroundColor = Color.BLACK;
		aboutOpen = false;
		textWindowOpen = false;
		resettedGame = true;
		score = 0.0;
		lastShipFire = 0.0;
		lastAlienFire = 0.0;
		lastAlienBigFire = 0.0;

		al = new ArrayList<Color>(9);
		al.add(Color.BLACK);
		al.add(Color.BLUE);
		al.add(Color.CYAN);
		al.add(Color.MAGENTA);
		al.add(Color.ORANGE);
		al.add(Color.PINK);
		al.add(Color.RED);
		al.add(Color.YELLOW);
		al.add(Color.GRAY);
	}

	/**
	 * Initializes the constant Objects
	 */
	private void initConstantObjects() {
		// The Player's Ship
		int height = ds.HEIGHT - 60;
		if (ds.bigScreen()) {
			height -= ds.WINDOW_BIGGER_FACTOR;
		}
		try {
			ship = new JSIShipEntity(this, new JSIShipType(), ds.WIDTH / 2,
					height);
		} catch (IOException e) {
			printErrorAndQuit(
					"IOException: JSIShipType in Function initConstantObjects.\n"
							+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		}
		try {
			alienEntity = new JSIAlienEntity(this, new JSIAlienType(3), 0, 0);
			alienBig = new JSIAlienEntity(this, new JSIAlienType(4), 0, 20);
		} catch (IOException e) {
			printErrorAndQuit(
					"IOException: JSIAlienType(3) in Function initEntities.\n"
							+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		} catch (Exception e) {
			printErrorAndQuit(
					"Exception: JSIAlienType(3) in Function initEntities.\n"
							+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		}

		// The Key and Mouse Controller
		kmc = new JSIKeyMouseController(this);

		// The Mode Chooser
		mc = new JSIModeChooser(this);
	}

	/**
	 * Initializes the TextWindow
	 */
	private void initTextWindow() {
		// The TextWindow
		tw = new JSITextWindow(this, ds.useTextArea, ds.resizable);
		tw.setButtonNextLabel(JSIExternalStrings.next);
		tw.setButtonPreviousLabel(JSIExternalStrings.previous);
		if (ds.useTextArea) {
			tw.setEnabled(false);
			tw.setBackgroundColor(Color.BLACK);
		}
	}

	/**
	 * Initializes the About-Window
	 */
	private void initAboutWindow() {
		String versionAndBuild = JSIExternalStrings.versionNumber + " ("
				+ JSIExternalStrings.build + " "
				+ JSIExternalStrings.buildNumber + ")";
		about = new JSIAboutWindow(this, JSIExternalStrings.jSpaceInvaders,
				versionAndBuild, JSIExternalStrings.author,
				JSIExternalStrings.denisMeyer);
		about.setIcon(sc.getIcon(JSIExternalStrings.imageLogo));
		about.setHead(JSIExternalStrings.aboutjSpaceInvaders);
		about.setTextOkButton(JSIExternalStrings.ok);
	}

	/**
	 * Initializes the Console
	 */
	private void initConsole() {
		console = new JSIConsole(this);
		addNewConsoleListener();
	}

	/*********** Show ***********/

	/**
	 * Shows the ModeChooser
	 */
	public void showModeChooser() {
		if (!newGameDialogueOpen() && !aboutOpen() && !textWindowOpen()
				&& !consoleOpen()) {
			setModeChooserFocus();
			mc.show();
			newGameDialogueOpen = true;
		}
	}

	/**
	 * Show the About-Dialogue
	 */
	public void showAbout() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			about.setVisible(true);
			aboutOpen = true;
		}
	}

	private void showConsole() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			console.setVisible(true);
			field.getMenu().getConsoleAction().setVisible(true);
			consoleOpen = true;
		}
	}

	/**
	 * Shows About Space Invaders
	 */
	private void showSpaceInvadersHistory() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.setIcon(null, true);
			tw.displayOtherButton(false);
			tw.setTitle(JSIExternalStrings.history);
			tw.setText(JSIExternalStrings.HISTORY);
			tw.setHyperlinkLabel(JSIExternalStrings.wikiHistory,
					JSIExternalStrings.wikiHistoryURL);
			tw.setDimension(580, 510);
			tw.setVisible(true);
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows the Gameplay
	 */
	private void showGameplay() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.setIcon(null, true);
			tw.displayOtherButton(false);
			tw.setTitle(JSIExternalStrings.gameplay);
			tw.setText(JSIExternalStrings.GAMEPLAY);
			tw.setHyperlinkLabel(JSIExternalStrings.wikiGameplay,
					JSIExternalStrings.wikiGameplayURL);
			tw.setDimension(620, 345);
			tw.setVisible(true);
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows the Controls
	 */
	private void showControls() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.setIcon(null, true);
			tw.displayOtherButton(false);
			tw.setTitle(JSIExternalStrings.controls);
			tw.setText(JSIExternalStrings.CONTROLS);
			tw.resetHyperlinkLabel();
			tw.setDimension(380, 210);
			tw.setVisible(true);
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows the Acknowledgments
	 */
	private void showAcknowledgementsAndCopyright() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.setIcon(null, true);
			tw.displayOtherButton(false);
			tw.setTitle(JSIExternalStrings.acknowledgementsAndCopyright);
			tw.setText(JSIExternalStrings.ACKNOWLEDGEMENTS_AND_COPYRIGHT);
			tw.resetHyperlinkLabel();
			tw.setDimension(740, 470);
			tw.setVisible(true);
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows the ChangeLog
	 */
	private void showChangeLog() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.setIcon(null, true);
			tw.displayOtherButton(false);
			tw.setTitle(JSIExternalStrings.changelog);
			tw.setText(JSIExternalStrings.CHANGELOG);
			tw.resetHyperlinkLabel();
			tw.setDimension(680, 450);
			tw.setVisible(true);
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows Cave Painting Image
	 */
	private void showCavePainting() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.displayOtherButton(true);
			tw.setIcon(sc.getIcon(JSIExternalStrings.image_cavePainting_image),
					false);
			tw.setTitle(JSIExternalStrings.image_cavePainting_name);
			tw.setText("");
			tw.resetHyperlinkLabel();
			tw.setDimension(580, 520);
			tw.setVisible(true);
			// Previous-Button
			tw.getButtonPrevious().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
					showMasterplan();
				}
			});
			// Next-Button
			tw.getButtonNext().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
					showMakingOfTetris();
				}
			});
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows Making of: Tetris Image
	 */
	private void showMakingOfTetris() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.displayOtherButton(true);
			tw.setIcon(
					sc.getIcon(JSIExternalStrings.image_makingOfTetris_image),
					false);
			tw.setTitle(JSIExternalStrings.image_makingOfTetris_name);
			tw.setText("");
			tw.resetHyperlinkLabel();
			tw.setDimension(580, 610);
			tw.setVisible(true);
			// Previous-Button
			tw.getButtonPrevious().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
					showCavePainting();
				}
			});
			// Next-Button
			tw.getButtonNext().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
					showSteampunk();
				}
			});
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows Steampunk Image
	 */
	private void showSteampunk() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.displayOtherButton(true);
			tw.setIcon(sc.getIcon(JSIExternalStrings.image_steampunk_image),
					false);
			tw.setTitle(JSIExternalStrings.image_steampunk_name);
			tw.setText("");
			tw.resetHyperlinkLabel();
			tw.setDimension(500, 550);
			tw.setVisible(true);
			// Previous-Button
			tw.getButtonPrevious().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
					showMakingOfTetris();
				}
			});
			// Next-Button
			tw.getButtonNext().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
					showMasterplan();
				}
			});
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows Making of: Masterplan Image
	 */
	private void showMasterplan() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			tw.displayOtherButton(true);
			tw.setIcon(sc.getIcon(JSIExternalStrings.image_masterplan_image),
					false);
			tw.setTitle(JSIExternalStrings.image_masterplan_name);
			tw.setText("");
			tw.resetHyperlinkLabel();
			tw.setDimension(650, 535);
			tw.setVisible(true);
			// Previous-Button
			tw.getButtonPrevious().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
					showSteampunk();
				}
			});
			// Next-Button
			tw.getButtonNext().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
					showCavePainting();
				}
			});
			setTextWindowOpen(true);
		}
	}

	/**
	 * Shows the HighscoreTable
	 */
	private void showHighscoreTable() {
		if (gameLoopRunning && !newGameDialogueOpen() && !aboutOpen()
				&& !textWindowOpen() && !consoleOpen()) {
			hc.getHighscoreTable().setVisible(true);
			setTextWindowOpen(true);
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns the ShipEntity
	 * 
	 * @return The Ship
	 */
	public JSIShipEntity getShip() {
		return ship;
	}

	/**
	 * Returns the Main Frame
	 * 
	 * @return the Main Frame if existing, null else
	 */
	public JFrame getMainFrame() {
		if (field != null) {
			return field.getFrame();
		}
		return null;
	}

	/**
	 * Returns the real Score
	 * 
	 * @return the real Score
	 */
	private double getRealScore() {
		return (ship.getLifes() > 0) ? (score * ship.getLifes()) : score;
	}

	/*********** Setter ***********/

	/**
	 * Sets the Lives to 0
	 */
	public void setLivesTo0() {
		ship.setLifesTo0();
	}

	/**
	 * Sets a new Message
	 * 
	 * @param message
	 *            The Message to display
	 */
	public void setMessage(String message) {
		this.message = message + " ";
		message_cache = message;
	}

	/**
	 * Notification from an (Alien) Entity that the the Game should be updated
	 * at the next Opportunity
	 */
	public void setUpdateThisLoop() {
		updateThisLoop = true;
	}

	/**
	 * Resets the Game
	 */
	public void resetGame() {
		score = 0.0;
		entities.clear();
		initEntities();
		kmc.resetKeys();
		newGameDialogueOpen = false;
		textWindowOpen = false;
		aboutOpen = false;
		consoleOpen = false;
		inPauseMode = false;
		field.setPauseActionText(JSIExternalStrings.pauseGame);
		displayingStartScreen = false;
		bigInvaderSpawned = false;
		bigInvaderDead = false;
		ship.resetLifes();
		resettedGame = true;
		lolfumodeBackgroundColorCounter = 0;
		lolfumodeBackgroundColor = Color.BLACK;
	}

	/**
	 * Displays the Start Screen
	 */
	public void setWaitingForKeyPress() {
		kmc.setWaitingForKeyPress(true);
		inPauseMode = true;
		displayingStartScreen = true;
		blankGraphics();
		displayPressAnyKey(displayingStartScreen);
		updateGraphics();
	}

	/**
	 * Sets TextWindow to open
	 * 
	 * @param open
	 *            Open or not
	 */
	private void setTextWindowOpen(boolean open) {
		textWindowOpen = open;
	}

	/**
	 * Sets the Mode Chooser Focus
	 */
	private void setModeChooserFocus() {
		// Set right Focus
		switch (ds.getMode()) {
		case WTF:
			mc.getButtonMode1().requestFocusInWindow();
			break;
		case NOOBY:
			mc.getButtonMode2().requestFocusInWindow();
			break;
		case EASY:
			mc.getButtonMode3().requestFocusInWindow();
			break;
		case NORMAL:
			mc.getButtonMode4().requestFocusInWindow();
			break;
		case HARD:
			mc.getButtonMode5().requestFocusInWindow();
			break;
		case INSANE:
			mc.getButtonMode6().requestFocusInWindow();
			break;
		default:
			mc.getButtonMode4().requestFocusInWindow();
			break;
		}
	}

	/**
	 * Sets the Field Window Listener
	 */
	private void setFieldWindowListener() {
		field.getFrame().addWindowListener(fieldWindowAdapter);
	}

	/**
	 * Sets the Mnemonic
	 * 
	 * @param jmi
	 * @param c
	 * @param inputMask
	 */
	private void setMnemonic(JMenuItem jmi, char c, boolean inputMask) {
		if (ds.is("Mac")) {
			if (inputMask) {
				jmi.setAccelerator(KeyStroke.getKeyStroke(c,
						InputEvent.META_DOWN_MASK));
			} else {
				jmi.setAccelerator(KeyStroke.getKeyStroke(c));
			}
		} else {
			if (inputMask) {
				jmi.setAccelerator(KeyStroke.getKeyStroke(c,
						InputEvent.CTRL_DOWN_MASK));
			} else {
				jmi.setAccelerator(KeyStroke.getKeyStroke(c));
			}
		}
	}

	/**
	 * Sets the Data to display
	 */
	private void setDisplayData() {
		_mode = JSIExternalStrings.mode + ": " + ds.getModeToString();
		_score = JSIExternalStrings.score + ": " + getRealScore();
		_lives = JSIExternalStrings.remainingLifes + ": " + ship.getLifes()
				+ " / " + ship.getInitialLifes();
	}

	/*********** Boolean ***********/

	/**
	 * Returns whether GameLoop is running
	 * 
	 * @return true if GameLoop is running, false else
	 */
	public boolean gameLoopRunning() {
		return gameLoopRunning;
	}

	/**
	 * Returns whether About is open or not
	 * 
	 * @return true if About is open, false else
	 */
	public boolean aboutOpen() {
		return aboutOpen;
	}

	/**
	 * Returns whether TextWindow is open or not
	 * 
	 * @return true if TextWindow is open, false else
	 */
	public boolean textWindowOpen() {
		return textWindowOpen;
	}

	/**
	 * Returns whether Console is open or not
	 * 
	 * @return true if Console is open, false else
	 */
	public boolean consoleOpen() {
		return consoleOpen;
	}

	/**
	 * Returns whether a Game is running
	 * 
	 * @return true if a Game is running, false else
	 */
	public boolean gameRunning() {
		return !kmc.waitingForKeyMousePress();
	}

	/**
	 * Returns whether in Pause Mode or not
	 * 
	 * @return true if in Pause Mode, false else
	 */
	public boolean inPauseMode() {
		return inPauseMode;
	}

	/**
	 * Checks whether entity is in the removeList
	 * 
	 * @param entity
	 * @return true if entity is in the removeList, false else
	 */
	public boolean isInRemoveList(JSIEntity entity) {
		return removeList.contains(entity);
	}

	/**
	 * Returns whether a new GameDialogue is open or not
	 * 
	 * @return true if a new GameDialogue is open, false else
	 */
	private boolean newGameDialogueOpen() {
		return newGameDialogueOpen;
	}

	/*********** Exit-Functions ***********/

	/**
	 * Prints an Error and quits
	 * 
	 * @param text
	 * @param title
	 */
	public void printErrorAndQuit(String text, String title) {
		if (getMainFrame() != null) {
			new JSIMessage(getMainFrame(), text, title).show();
		} else {
			new JSIMessage(null, text, title).show();
		}
		systemExit(0);
	}

	/**
	 * Quits the Game
	 * 
	 * @param confirm
	 *            If Confirmation Dialogue should be displayed or not
	 */
	public void quit(boolean confirm) {
		if (confirm) {
			if (!aboutOpen() && !textWindowOpen() && !newGameDialogueOpen()
					&& !consoleOpen()) {
				// Pause Game
				pauseGame(JSIExternalStrings.quitGame + "?");
				if (JOptionPane.showConfirmDialog(getMainFrame(),
						JSIExternalStrings.quitGameQuestion,
						JSIExternalStrings.quitGame + "?",
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					systemExit(0);
				}
				// Resume Game
				resumeGame();
			}
		} else {
			systemExit(0);
		}
	}

	/**
	 * Exits the Game
	 * 
	 * @param nr
	 */
	private void systemExit(int nr) {
		if (hc != null) {
			hc.save();
		}
		System.exit(nr);
	}

	/*********** Mode Chooser-Functions ***********/

	/**
	 * Sets the Dialog to invisible and resets the Game
	 */
	private void modeChooserAction() {
		mc.getDialog().setVisible(false);
		resetGame();
	}

	/**
	 * Sets the Mode, sets the Mode Chooser to invisible and resets the Game
	 * 
	 * @param ae
	 */
	private void modeChooserAction(ActionEvent ae) {
		if (mc.getButtonMode1().equals(ae.getSource())) {
			ds.setMode(Mode.WTF);
			modeChooserAction();
		} else if (mc.getButtonMode2().equals(ae.getSource())) {
			ds.setMode(Mode.NOOBY);
			modeChooserAction();
		} else if (mc.getButtonMode3().equals(ae.getSource())) {
			ds.setMode(Mode.EASY);
			modeChooserAction();
		} else if (mc.getButtonMode4().equals(ae.getSource())) {
			ds.setMode(Mode.NORMAL);
			modeChooserAction();
		} else if (mc.getButtonMode5().equals(ae.getSource())) {
			ds.setMode(Mode.HARD);
			modeChooserAction();
		} else if (mc.getButtonMode6().equals(ae.getSource())) {
			ds.setMode(Mode.INSANE);
			modeChooserAction();
		} else if (mc.getButtonMode7().equals(ae.getSource())) {
			mc.getDialog().setVisible(false);
			if (JOptionPane.showConfirmDialog(getMainFrame(),
					JSIExternalStrings.quitGameQuestion,
					JSIExternalStrings.quitGame + "?",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				systemExit(0);
			}
			mc.getDialog().setVisible(true);
		} else {
			ds.setMode(Mode.NORMAL);
			modeChooserAction();
		}
	}

	/**
	 * Sets the Mode, sets the Mode Chooser to invisible and resets the Game
	 * 
	 * @param ke
	 */
	private void modeChooserAction(KeyEvent ke) {
		if (mc.getButtonMode1().equals(ke.getSource())) {
			ds.setMode(Mode.WTF);
			modeChooserAction();
		} else if (mc.getButtonMode2().equals(ke.getSource())) {
			ds.setMode(Mode.NOOBY);
			modeChooserAction();
		} else if (mc.getButtonMode3().equals(ke.getSource())) {
			ds.setMode(Mode.EASY);
			modeChooserAction();
		} else if (mc.getButtonMode4().equals(ke.getSource())) {
			ds.setMode(Mode.NORMAL);
			modeChooserAction();
		} else if (mc.getButtonMode5().equals(ke.getSource())) {
			ds.setMode(Mode.HARD);
			modeChooserAction();
		} else if (mc.getButtonMode6().equals(ke.getSource())) {
			ds.setMode(Mode.INSANE);
			modeChooserAction();
		} else if (mc.getButtonMode7().equals(ke.getSource())) {
			mc.getDialog().setVisible(false);
			if (JOptionPane.showConfirmDialog(getMainFrame(),
					JSIExternalStrings.quitGameQuestion,
					JSIExternalStrings.quitGame + "?",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				systemExit(0);
			}
			mc.getDialog().setVisible(true);
		} else {
			ds.setMode(Mode.NORMAL);
			modeChooserAction();
		}
	}

	/*********** Text Window-Functions ***********/

	/**
	 * If clicked at close at the Highscore Window
	 */
	public void highscoreWindowAction() {
		hc.getHighscoreTable().setVisible(false);
		setTextWindowOpen(false);
		resumeGame();
	}

	/**
	 * Performs the About OK-Button Action
	 */
	private void textWindowAction() {
		tw.setVisible(false);
		setTextWindowOpen(false);
		resumeGame();
	}

	/**
	 * Performs the About OK-Button Action
	 */
	private void aboutAction() {
		about.setVisible(false);
		aboutOpen = false;
		resumeGame();
	}

	/**
	 * Performs the About OK-Button Action
	 */
	private void consoleAction() {
		console.setVisible(false);
		consoleOpen = false;
		resumeGame();
	}

	/*********** Listener-Functions ***********/

	/**
	 * Adds new Highscore Listener
	 */
	public void addNewHighscoreListener() {
		if (hc.getHighscoreTable() != null) {
			// Set Title and Text
			hc.getHighscoreTable().getMainFrame()
					.setTitle(JSIExternalStrings.highscore);
			hc.getHighscoreTable().getButtonOk().setText(JSIExternalStrings.ok);
			hc.getHighscoreTable().getButtonReset()
					.setText(JSIExternalStrings.resetHighscoreList);

			// Main Window
			hc.getHighscoreTable().getMainFrame()
					.addWindowListener(new WindowAdapter() {

						/**
						 * If Field-Window closing
						 */
						public void windowClosing(WindowEvent e) {
							highscoreWindowAction();
						}
					});
			// Button Ok
			hc.getHighscoreTable().getButtonOk()
					.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							highscoreWindowAction();
						}
					});
			hc.getHighscoreTable().getButtonOk()
					.addKeyListener(new KeyAdapter() {

						public void keyPressed(KeyEvent ke) {
							if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
								highscoreWindowAction();
							}
						}
					});
			// Button Reset
			hc.getHighscoreTable().getButtonReset()
					.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							highscoreResetAction();
						}
					});
		}
	}

	/**
	 * Adds new TextWindow Button-Ok-Listener
	 */
	public void addNewTextWindowButtonOkListener() {
		if (tw != null) {
			tw.getDialog().addWindowListener(new WindowAdapter() {

				/**
				 * If Field-Window closing
				 */
				public void windowClosing(WindowEvent e) {
					textWindowAction();
				}
			});
			// TextWindow ActionListener
			tw.getButtonOk().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					textWindowAction();
				}
			});

			// TextWindow KeyListener
			tw.getButtonOk().addKeyListener(new KeyAdapter() {

				public void keyPressed(KeyEvent ke) {
					if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
						textWindowAction();
					}
				}
			});
		}
	}

	/**
	 * Adds new About Button-Ok-Listener
	 */
	public void addNewAboutButtonOkListener() {
		if (about != null) {
			about.getDialog().addWindowListener(new WindowAdapter() {

				/**
				 * If Field-Window closing
				 */
				public void windowClosing(WindowEvent e) {
					aboutAction();
				}
			});
			// Button ActionListener
			about.getButtonOk().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					aboutAction();
				}
			});

			// Button KeyListener
			about.getButtonOk().addKeyListener(new KeyAdapter() {

				public void keyPressed(KeyEvent ke) {
					if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
						aboutAction();
					}
				}
			});
		}
	}

	/**
	 * Adds new About Button-Ok-Listener
	 */
	public void addNewConsoleListener() {
		if (console != null) {
			console.getDialog().addWindowListener(new WindowAdapter() {

				/**
				 * If Field-Window closing
				 */
				public void windowClosing(WindowEvent e) {
					consoleAction();
				}
			});
			// Button ActionListener
			console.getButtonOk().addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					checkForCheats();
					consoleAction();
				}
			});

			// Button KeyListener
			console.getButtonOk().addKeyListener(new KeyAdapter() {

				public void keyPressed(KeyEvent ke) {
					if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
						checkForCheats();
						consoleAction();
					}
				}
			});
		}
	}

	/*********** Start/Pause/Resume-Functions ***********/

	/**
	 * Starts the Game
	 */
	public void startGame() {
		// If necessary Data not initialized: do it here
		if (!initializedFirst) {
			initFirst();
		}
		if (!initializedSecond) {
			initSecond();
		}
		if (!initializedThird) {
			initThird();
		}
		if (!gameLoopRunning) {
			// Set normal Mode as default
			ds.setMode(Mode.NORMAL);
			gameLoopRunning = true;
			inPauseMode = true;
			displayingStartScreen = true;
			field.setVisible(true);
			gameLoop();
		}
	}

	/**
	 * Pauses the Game
	 * 
	 * @param message
	 *            The Message
	 */
	public void pauseGame(String message) {
		if (!inPauseMode() && gameRunning()) {
			entityDxs = new ArrayList<Double>(entities.size());
			entityDys = new ArrayList<Double>(entities.size());
			for (int i = 0; i < entities.size(); i++) {
				entityDxs.add(entities.get(i).getDx());
				entityDys.add(entities.get(i).getDy());
				entities.get(i).setDx(0.0);
				entities.get(i).setDy(0.0);
			}
			field.setPauseActionText(JSIExternalStrings.resumeGame);
			inPauseMode = true;
			this.message = message + " ";
		}
	}

	/**
	 * Resumes the Game
	 */
	public void resumeGame() {
		if (inPauseMode() && gameRunning() && !aboutOpen()
				&& !newGameDialogueOpen() && !textWindowOpen()
				&& !consoleOpen()) {
			if ((entities.size() == entityDxs.size())
					&& (entities.size() == entityDys.size())) {
				for (int i = 0; i < entities.size(); i++) {
					entities.get(i).setDx(entityDxs.get(i));
					entities.get(i).setDy(entityDys.get(i));
				}
			}
			field.setPauseActionText(JSIExternalStrings.pauseGame);
			inPauseMode = false;
		}
	}

	/*********** Fire-Functions ***********/

	/**
	 * Attempt to fire a Shot from any Alien
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 */
	private void alienFire(int x, int y) {
		// Check if waited long enough
		if ((System.currentTimeMillis() - lastAlienFire) < ds
				.getAlienFiringIntervall()) {
			return;
		}

		JSIShotEntity shot = null;
		// Create the Shot Entity and record the Time
		lastAlienFire = System.currentTimeMillis();
		try {
			shot = new JSIShotEntity(this, new JSIShotType(2), x - 10, y + 30);
		} catch (IOException e1) {
			printErrorAndQuit(
					"IOException: JSIShipType(2) in Function alienFire.\n"
							+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		} catch (Exception e1) {
			printErrorAndQuit(
					"Exception: JSIShipType(2) in Function alienFire.\n"
							+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		}
		try {
			soc.getShotInvaderSound().play();
		} catch (UnsupportedAudioFileException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		} catch (Exception e) {
			// Do nothing
		}
		if (shot != null) {
			entities.add(shot);
		}
	}

	/**
	 * Attempt to fire a Shot from the AlienBig
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 */
	private void alienBigFire(int x, int y) {
		// Check if waited long enough
		if ((System.currentTimeMillis() - lastAlienBigFire) < ds
				.getAlienFiringIntervall() / 2) {
			return;
		}

		JSIShotEntity shot = null;

		// Create the Shot Entity and record the Time
		lastAlienBigFire = System.currentTimeMillis();
		try {
			shot = new JSIShotEntity(this, new JSIShotType(2), x - 10, y + 30);
		} catch (IOException e1) {
			printErrorAndQuit(
					"IOException: JSIShipType(2) in Function alienBigFire.\n"
							+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		} catch (Exception e1) {
			printErrorAndQuit(
					"Exception: JSIShipType(2) in Function alienBigFire.\n"
							+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		}
		try {
			soc.getShotInvaderSound().play();
		} catch (UnsupportedAudioFileException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		} catch (Exception e) {
			// Do nothing
		}
		if (shot != null) {
			entities.add(shot);
		}
	}

	/**
	 * Attempt to fire a Shot from the Player
	 */
	private void fire() {
		// Check if waited long enough
		if ((System.currentTimeMillis() - lastShipFire) < ds
				.getShipFiringIntervall()) {
			return;
		}

		// Create the Shot Entity and record the Time
		lastShipFire = System.currentTimeMillis();

		// Add a new ShotEntity
		try {
			JSIShotType shotType = new JSIShotType(1);
			if ((doubleFirePower && !insaneFirePower)
					|| ((ds.getMode() == Mode.NOOBY) && !insaneFirePower)
					|| ((ds.getMode() == Mode.EASY) && !insaneFirePower)) {
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate(), ship.getYCoordinate() - 30));
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate() + ship.getSprite().getWidth() - 10,
						ship.getYCoordinate() - 30));
			} else if (insaneFirePower) {
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate() - 30, ship.getYCoordinate() - 30));
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate() - 15, ship.getYCoordinate() - 30));
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate(), ship.getYCoordinate() - 30));
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate() + 15, ship.getYCoordinate() - 30));
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate() + 30, ship.getYCoordinate() - 30));
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate() + 45, ship.getYCoordinate() - 30));
			} else {
				entities.add(new JSIShotEntity(this, shotType, ship
						.getXCoordinate() + 10, ship.getYCoordinate() - 30));
			}
		} catch (IOException e1) {
			printErrorAndQuit("IOException: JSIShipType in Function fire.\n"
					+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		} catch (Exception e1) {
			printErrorAndQuit("Exception: JSIShipType in Function fire.\n"
					+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		}
		// Play the Shot Sound
		try {
			soc.getShotPlayerSound().play();
		} catch (UnsupportedAudioFileException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		} catch (Exception e) {
			// Do nothing
		}
	}

	/*********** Functions ***********/

	private void shipKilled(boolean unbreak) {
		if (!unbreak) {
			int heightDifference = Math.abs((ship.getType()
					.getPlayerKilledSprite().getHeight() - ship.getType()
					.getSprite().getHeight()));
			// Set the Player killed-Sprite for this Moment
			if (!disguised) {
				if (lolfumode) {
					ship.setSprite(alienBig.getExplosionSprite());
				} else {
					ship.setSprite(ship.getType().getPlayerKilledSprite());
					ship.setYCoordinate(ship.getYCoordinate()
							- heightDifference);
				}
			} else {
				ship.setSprite(alienEntity.getExplosionSprite());
			}
			blankGraphics();
			moveEntities();
			updateGraphics();
			if (!disguised) {
				if (lolfumode) {
					try {
						ship.setSprite(sc
								.getImgOrSprite(JSIExternalStrings.spriteFunFuface));
						alienBig.setSprite(sc
								.getImgOrSprite(JSIExternalStrings.spriteFunLolface));
					} catch (IOException e) {
						printErrorAndQuit(
								"IOException: Ship in Function setSprite.\n"
										+ JSIExternalStrings.quitting + "...",
								JSIExternalStrings.error);
					}
				} else {
					ship.setSprite(ship.getType().getSprite());
					ship.setYCoordinate(ship.getYCoordinate()
							+ heightDifference);
				}
			} else {
				ship.setSprite(alienEntity.getSprite());
			}

			try {
				soc.getExplosionSound().play();
			} catch (UnsupportedAudioFileException e) {
				// Do nothing
			} catch (IOException e) {
				// Do nothing
			} catch (Exception e) {
				// Do nothing
			}
		}
	}

	/**
	 * Notifies the Death
	 */
	public void notifyDeath() {
		if (gameRunning()) {
			field.getFrame().removeWindowListener(fieldWindowAdapter);
			textWindowOpen = true;

			if (!(ship.getLifes() <= 0)) {
				shipKilled(unbreakable);
			}
			setDisplayData();
			if (ship.getLifes() <= 0) {
				shipKilled(false);
				message = JSIExternalStrings.MESSAGE_LOST + " ";
				message_cache = message;
				messageDialogue = new JSIMessage(getMainFrame(),
						"<html><head></head><body>" + message + "<br><br>"
								+ _mode + "<br>" + _score + "<br>" + _lives
								+ "</body></html>",
						JSIExternalStrings.MESSAGE_LOST);
				messageDialogue.setErrorType();
				messageDialogue.show();
				hc.getHighscoreTableData().insert(getRealScore(),
						ds.getModeToString(), false, ship.getLifes());
				field.setPauseActionText(JSIExternalStrings.pauseGame);
				setWaitingForKeyPress();
			} else {
				if (!unbreakable) {
					message = JSIExternalStrings.MESSAGE_LIFE_LOST + " ";
					message_cache = message;
					messageDialogue = new JSIMessage(getMainFrame(),
							"<html><head></head><body>" + message + "<br><br>"
									+ _mode + "<br>" + _score + "<br>" + _lives
									+ "</body></html>",
							JSIExternalStrings.MESSAGE_LIFE_LOST);
					messageDialogue.setErrorType();
					messageDialogue.show();
					kmc.resetKeys();
					field.setPauseActionText(JSIExternalStrings.pauseGame);
				}
			}
			textWindowOpen = false;
			field.getFrame().addWindowListener(fieldWindowAdapter);
		}
	}

	/**
	 * Removes the Entity entity
	 * 
	 * @param entity
	 *            Entity to remove
	 * @param givePoints
	 *            If Points should be given or not
	 */
	public void removeEntity(JSIEntity entity, boolean givePoints) {
		if (!isInRemoveList(entity)) {
			removeList.add(entity);
			if (entity instanceof JSIAlienEntity) {
				alienKilled((JSIAlienEntity) entity, givePoints);
			}
		}
	}

	/**
	 * If clicked at reset at the Highscore Window
	 */
	public void highscoreResetAction() {
		hc.getHighscoreTable().getMainFrame().setVisible(false);
		if (JOptionPane.showConfirmDialog(getMainFrame(),
				JSIExternalStrings.resetHighscoreQuestion,
				JSIExternalStrings.resetHighscoreList + "?",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			hc.getHighscoreTableData().clear();
			hc.save();
			hc.getHighscoreTable().setVisible(false);
			setTextWindowOpen(false);
			showHighscoreTable();
		} else {
			hc.getHighscoreTable().getMainFrame().setVisible(true);
		}
	}

	/**
	 * Returns if first == second
	 * 
	 * @param cheat
	 * @param comp
	 * @return true if first == second, false else
	 */
	private boolean equals(String first, String second) {
		return first.compareTo(second) == 0;
	}

	private void playCheatSound(boolean accepted) {
		try {
			if (accepted) {
				soc.getAcceptedSound().play();
			} else {
				soc.getRejectedSound().play();
			}
		} catch (UnsupportedAudioFileException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		} catch (Exception e) {
			// Do nothing
		}
	}

	/**
	 * Checks for Cheats from the Console
	 */
	private void checkForCheats() {
		boolean correct = true;
		String cheat = console.getText();
		if (equals(cheat, "deactivateallcheats")) {
			playCheatSound(true);
			correct = false;
			doubleFirePower = false;
			insaneFirePower = false;
			unbreakable = false;
			if (lolfumode) {
				ship.setYCoordinate(ship.getYCoordinate() + heightDifference);
				alienBig.setSprite(alienBig.getType().getSprite());
			}
			if (disguised || lolfumode) {
				ship.setSprite(ship.getType().getSprite());
			}
			disguised = false;
			lolfumode = false;
		} else if (equals(cheat, "doublefirepowerftw")) {
			playCheatSound(true);
			doubleFirePower = !doubleFirePower;
		} else if (equals(cheat, "insanefirepowerftw")) {
			playCheatSound(true);
			insaneFirePower = !insaneFirePower;
		} else if (equals(cheat, "iamunbreakable")) {
			playCheatSound(true);
			unbreakable = !unbreakable;
		} else if (equals(cheat, "disguiseme")) {
			if (!lolfumode) {
				playCheatSound(true);
				disguised = !disguised;
				if (disguised) {
					ship.setSprite(alienEntity.getSprite());
				} else {
					ship.setSprite(ship.getType().getSprite());
				}
			} else {
				playCheatSound(true);
				playCheatSound(false);
			}
		} else if (equals(cheat, "lolfumode")) {
			if (!disguised) {
				playCheatSound(true);
				lolfumode = !lolfumode;
				if (lolfumode) {
					try {
						ship.setSprite(sc
								.getImgOrSprite(JSIExternalStrings.spriteFunFuface));
						heightDifference = ship.getSprite().getHeight()
								- ship.getType().getSprite().getHeight();
						ship.setYCoordinate(ship.getYCoordinate()
								- heightDifference);
						alienBig.setSprite(sc
								.getImgOrSprite(JSIExternalStrings.spriteFunLolface));
					} catch (IOException e) {
						printErrorAndQuit(
								"IOException: Ship in Function setSprite.\n"
										+ JSIExternalStrings.quitting + "...",
								JSIExternalStrings.error);
					}
				} else {
					ship.setYCoordinate(ship.getYCoordinate()
							+ heightDifference);
					ship.setSprite(ship.getType().getSprite());
					alienBig.setSprite(alienBig.getType().getSprite());
				}
			} else {
				playCheatSound(true);
				playCheatSound(false);
			}
		} else if (equals(cheat, "rulethehighscore")) {
			playCheatSound(true);
			double d = ds.getRandomDoubleWithinLimits(8000.0, 20000.0);
			if (!hc.getHighscoreTableData().getSortedEntryList().isEmpty()) {
				d = hc.getHighscoreTableData().getSortedEntryList().get(0)
						.getScore()
						* ds.getRandomDoubleWithinLimits(1.1, 1.9);
			}
			hc.getHighscoreTableData().insert(d,
					ds.getModeToString(Mode.INSANE), true, 2);
		} else if (equals(cheat, "iamthemasteroftheuniverse")) {
			playCheatSound(true);
			insaneFirePower = !insaneFirePower;
			unbreakable = !unbreakable;
			disguised = !disguised;
			if (disguised) {
				ship.setSprite(alienEntity.getSprite());
			} else {
				ship.setSprite(ship.getType().getSprite());
			}
		} else {
			correct = false;
		}
		if (!correct) {
			playCheatSound(false);
		}
	}

	/**
	 * Initializes the Mnemonics
	 */
	private void initMnemonics() {
		setMnemonic(field.getMenu().getAboutAction(),
				JSIExternalStrings.mnemonicAbout, true);
		setMnemonic(field.getMenu().getExitAction(),
				JSIExternalStrings.mnemonicExit, true);
		setMnemonic(field.getMenu().getNewGameAction(),
				JSIExternalStrings.mnemonicNewGame, true);
		setMnemonic(field.getMenu().getPauseAction(),
				JSIExternalStrings.mnemonicPause, false);
		setMnemonic(field.getMenu().getHighscoreAction(),
				JSIExternalStrings.mnemonicHighscore, true);
		setMnemonic(field.getMenu().getSpaveInvadersHistoryAction(),
				JSIExternalStrings.mnemonicHistory, true);
		setMnemonic(field.getMenu().getGameplayAction(),
				JSIExternalStrings.mnemonicGameplay, true);
		setMnemonic(field.getMenu().getControlsAction(),
				JSIExternalStrings.mnemonicControls, true);
		setMnemonic(field.getMenu().getAcknowledgementsAndCopyrightAction(),
				JSIExternalStrings.mnemonicAcknowledgementsAndCopyright, true);
		setMnemonic(field.getMenu().getChangeLogAction(),
				JSIExternalStrings.mnemonicChangelog, true);
	}

	/**
	 * Notifies the Win
	 */
	private void notifyWin() {
		if (gameRunning()) {
			field.getFrame().removeWindowListener(fieldWindowAdapter);
			textWindowOpen = true;
			message = JSIExternalStrings.MESSAGE_WIN + " ";
			message_cache = message;
			setDisplayData();
			messageDialogue = new JSIMessage(getMainFrame(),
					"<html><head></head><body>" + message + "<br><br>" + _mode
							+ "<br>" + _score + "<br>" + _lives
							+ "</body></html>", JSIExternalStrings.MESSAGE_WIN);
			messageDialogue.setInformationType();
			messageDialogue.show();
			hc.getHighscoreTableData().insert(getRealScore(),
					ds.getModeToString(), true, ship.getLifes());
			setWaitingForKeyPress();
			textWindowOpen = false;
			field.getFrame().addWindowListener(fieldWindowAdapter);
		}
	}

	/**
	 * Calculates the new Score
	 */
	private double getCalculatedScore(JSIAlienType type) {
		switch (ds.getMode()) {
		case WTF:
			return Math.round(type.getPoints() * 10.0);
		case NOOBY:
			return Math.round(type.getPoints() / 3.6);
		case EASY:
			return Math.round(type.getPoints() / 2.0);
		case NORMAL:
			return Math.round(type.getPoints());
		case HARD:
			return Math.round(type.getPoints() * 2.0);
		case INSANE:
			return Math.round(type.getPoints() * 3.6);
		default:
			return Math.round(type.getPoints());
		}
	}

	/**
	 * Calculates the new Score
	 */
	private void calculateScore(JSIAlienType type) {
		score += getCalculatedScore(type);
		score = Math.round(score);
	}

	/**
	 * Speed up all other Aliens a bit
	 */
	private void speedUpAliens() {
		JSIEntity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);

			if (entity instanceof JSIAlienEntity) {
				entity.setDx(entity.getDx() * ds.getSpeedUpFactor());
			}
		}
	}

	/**
	 * Gets the Notification that an Alien has been killed
	 * 
	 * @param givePoints
	 */
	private void alienKilled(JSIAlienEntity entity, boolean givePoints) {
		// Decrement Alien Count
		alienCount--;
		// Display an Explosion
		entity.getExplosionSprite().draw(g, entity.getXCoordinate(),
				entity.getYCoordinate());

		// Play Sound
		try {
			soc.getInvaderKilledSound().play();
		} catch (UnsupportedAudioFileException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		} catch (Exception e) {
			// Do nothing
		}
		if (givePoints) {
			calculateScore(entity.getType());
		}

		// No more Aliens -> Player wins the Game
		if (alienCount == 0) {
			notifyWin();
		}

		// If Alien gave Points
		if (givePoints) {
			speedUpAliens();
		}
	}

	/**
	 * Initializes the Entities
	 */
	private void initEntities() {
		entities.add(ship);
		// Create a Block of Aliens
		alienCount = ds.getRows() * ds.getColumns();
		JSIAlienEntity alien = null;

		movedDown = 50;
		for (int row = 0; row < ds.getRows(); row++) {
			for (int x = 0; x < ds.getColumns(); x++) {
				// Use different Sprites (with Space between them)
				if ((row == 0) || (row == 1)) {
					try {
						alien = new JSIAlienEntity(this, new JSIAlienType(1),
								102 + (x * 50), 50 + row * 30 - movedDown);
					} catch (IOException e) {
						printErrorAndQuit(
								"IOException: JSIAlienType(1) in Function initEntities.\n"
										+ JSIExternalStrings.quitting + "...",
								JSIExternalStrings.error);
					} catch (Exception e) {
						printErrorAndQuit(
								"Exception: JSIAlienType(1) in Function initEntities.\n"
										+ JSIExternalStrings.quitting + "...",
								JSIExternalStrings.error);
					}
				} else if ((row == 2) || (row == 3)) {
					try {
						alien = new JSIAlienEntity(this, new JSIAlienType(2),
								101 + (x * 50), 50 + row * 30 - movedDown);
					} catch (IOException e) {
						printErrorAndQuit(
								"IOException: JSIAlienType(2) in Function initEntities.\n"
										+ JSIExternalStrings.quitting + "...",
								JSIExternalStrings.error);
					} catch (Exception e) {
						printErrorAndQuit(
								"Exception: JSIAlienType(2) in Function initEntities.\n"
										+ JSIExternalStrings.quitting + "...",
								JSIExternalStrings.error);
					}
				} else {
					try {
						alien = new JSIAlienEntity(this, new JSIAlienType(3),
								100 + (x * 50), 50 + row * 30 - movedDown);
					} catch (IOException e) {
						printErrorAndQuit(
								"IOException: JSIAlienType(3) in Function initEntities.\n"
										+ JSIExternalStrings.quitting + "...",
								JSIExternalStrings.error);
					} catch (Exception e) {
						printErrorAndQuit(
								"Exception: JSIAlienType(3) in Function initEntities.\n"
										+ JSIExternalStrings.quitting + "...",
								JSIExternalStrings.error);
					}
				}
				if (alien != null) {
					entities.add(alien);
				}
			}
		}
	}

	/**
	 * Pauses a short Time
	 */
	private void sleep() {
		try {
			if (!inPauseMode) {
				Thread.sleep(ds.THREAD_SLEEP);
			} else {
				Thread.sleep(ds.THREAD_SLEEP_PAUSE_MODE);
			}
		} catch (Exception e) {
			printErrorAndQuit("Exception: Thread.sleep in Function sleep.\n"
					+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		}
	}

	/*********** Main Game Loop-Functions ***********/

	/**
	 * Get hold of a Graphics Context for the accelerated Surface and blank it
	 * out
	 */
	private void blankGraphics() {
		g = (Graphics2D) field.getStrategy().getDrawGraphics();
		if (lolfumode) {
			if (lolfumodeBackgroundColorCounter >= 20) {
				lolfumodeBackgroundColorCounter = 0;
				lolfumodeBackgroundColor = al.get(ds.getRandomBetweenZeroAnd(al
						.size()));
				g.setColor(lolfumodeBackgroundColor);
			} else {
				lolfumodeBackgroundColorCounter++;
				g.setColor(lolfumodeBackgroundColor);
			}
		} else {
			g.setColor(Color.black);
		}
		g.fillRect(0, 0, ds.WIDTH, ds.HEIGHT);
		if (!lolfumode) {
			try {
				sc.getImgOrSprite(JSIExternalStrings.imageBackground).draw(g,
						0, 0);
			} catch (IOException e) {
				printErrorAndQuit(JSIExternalStrings.cantFindReferenceToSprite
						+ " \"" + JSIExternalStrings.imageBackground + "\".\n"
						+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error + ": "
								+ JSIExternalStrings.cantFindReferenceToSprite);
			} catch (Exception e) {
				printErrorAndQuit(JSIExternalStrings.cantFindReferenceToSprite
						+ " \"" + JSIExternalStrings.imageBackground + "\".\n"
						+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error + ": "
								+ JSIExternalStrings.cantFindReferenceToSprite);
			}
		}
	}

	/**
	 * Displays 'Press any Key' on the Screen
	 * 
	 * @param printAnyKey
	 *            If a Message for 'Press any Key' should be displayed
	 */
	private void displayPressAnyKey(boolean printPressAnyKey) {
		g.setColor(ds.COLOR_MARQUEE);
		// The Message
		if (ds.MARQUEE_ON) {
			message = message.substring(1) + message.charAt(0);
		}
		g.setFont(new Font(ds.FONT_NAME, ds.STYLE_MAIN, ds.SIZE_MAIN));
		g.drawString(message,
				(ds.WIDTH - g.getFontMetrics().stringWidth(message)) / 2,
				(ds.HEIGHT / 2) - 240);
		setDisplayData();
		g.setColor(ds.COLOR_OTHER);
		g.setFont(new Font(ds.FONT_NAME, ds.STYLE_OTHER, ds.SIZE_OTHER));
		// The Mode
		g.drawString(_mode,
				(ds.WIDTH - g.getFontMetrics().stringWidth(message)) / 2,
				(ds.HEIGHT / 2) - 190);
		// The Score
		g.drawString(_score, (ds.WIDTH - g.getFontMetrics()
				.stringWidth(message)) / 2, (ds.HEIGHT / 2) - 170);
		// The Amount of Lives
		g.drawString(_lives, (ds.WIDTH - g.getFontMetrics()
				.stringWidth(message)) / 2, (ds.HEIGHT / 2) - 150);
		if (printPressAnyKey) {
			JSIAlienEntity ae = null;
			// 1st Type of Alien
			try {
				ae = new JSIAlienEntity(this, new JSIAlienType(1),
						(ds.WIDTH / 2) - 100, (ds.HEIGHT / 2) - 100);
			} catch (IOException e) {
				printErrorAndQuit(
						"IOException: JSIAlienType(1) in Function displayPressAnyKey.\n"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			} catch (Exception e) {
				printErrorAndQuit(
						"Exception: JSIAlienType(1) in Function displayPressAnyKey.\n"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			}
			if (ae != null) {
				ae.draw(g);
			}
			g.drawString("= " + getCalculatedScore(ae.getType()) + " "
					+ JSIExternalStrings.PTS, (ds.WIDTH / 2),
					(ds.HEIGHT / 2) - 90);

			// 2nd Type of Alien
			try {
				ae = new JSIAlienEntity(this, new JSIAlienType(2),
						(ds.WIDTH / 2) - 100, (ds.HEIGHT / 2) - 70);
			} catch (IOException e) {
				printErrorAndQuit(
						"IOException: JSIAlienType(2) in Function displayPressAnyKey.\n"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			} catch (Exception e) {
				printErrorAndQuit(
						"IOException: JSIAlienType(2) in Function displayPressAnyKey.\n"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			}
			if (ae != null) {
				ae.draw(g);
			}
			g.drawString("= " + getCalculatedScore(ae.getType()) + " "
					+ JSIExternalStrings.PTS, (ds.WIDTH / 2),
					(ds.HEIGHT / 2) - 60);

			// 3rd Type of Alien
			try {
				ae = new JSIAlienEntity(this, new JSIAlienType(3),
						(ds.WIDTH / 2) - 100, (ds.HEIGHT / 2) - 40);
			} catch (IOException e) {
				printErrorAndQuit(
						"IOException: JSIAlienType(3) in Function displayPressAnyKey.\n"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			} catch (Exception e) {
				printErrorAndQuit(
						"IOException: JSIAlienType(3) in Function displayPressAnyKey.\n"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			}
			if (ae != null) {
				ae.draw(g);
			}
			g.drawString("= " + getCalculatedScore(ae.getType()) + " "
					+ JSIExternalStrings.PTS, (ds.WIDTH / 2),
					(ds.HEIGHT / 2) - 30);

			// 4th Type of Alien
			try {
				ae = new JSIAlienEntity(this, new JSIAlienType(4),
						(ds.WIDTH / 2) - 140, (ds.HEIGHT / 2) - 10);
			} catch (IOException e) {
				printErrorAndQuit(
						"IOException: JSIAlienType(4) in Function displayPressAnyKey.\n"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			} catch (Exception e) {
				printErrorAndQuit(
						"IOException: JSIAlienType(4) in Function displayPressAnyKey.\n"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			}
			ae.draw(g);
			g.drawString("= ??? " + JSIExternalStrings.PTS, (ds.WIDTH / 2),
					(ds.HEIGHT / 2) - 0);

			g.drawString(
					JSIExternalStrings.PTS_INFO,
					(ds.WIDTH / 2)
							- g.getFontMetrics().stringWidth(
									JSIExternalStrings.PTS_INFO) / 2,
					(ds.HEIGHT / 2) + 100);

			// Press any key...
			if (ds.MARQUEE_ON) {
				pressAnyKey = pressAnyKey.substring(1) + pressAnyKey.charAt(0);
			}
			g.setColor(ds.COLOR_MARQUEE);
			// Big Screen
			if (ds.bigScreen()) {
				g.drawString(
						pressAnyKey,
						(ds.WIDTH - g.getFontMetrics().stringWidth(pressAnyKey)) / 2,
						ds.HEIGHT
								- (int) (ds.WINDOW_BIGGER_FACTOR * ds.WINDOW_BIGGER_STRETCH_FACTOR));
			} else {
				g.drawString(pressAnyKey, (ds.WIDTH - g.getFontMetrics()
						.stringWidth(pressAnyKey)) / 2, ds.HEIGHT - 15);
			}
		}
	}

	/**
	 * Clear Graphics and flip Buffer
	 */
	private void updateGraphics() {
		g.dispose();
		field.getStrategy().show();
	}

	/**
	 * Moves each Entity
	 */
	private void moveEntities() {
		JSIEntity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);

			// Big Invaders are faster than other Entities
			if (entity.equals(alienBig)) {
				entity.move(ds.MOVE_FACTOR_BIG_INVADERS);
			} else {
				entity.move(ds.MOVE_FACTOR);
			}

			entity.draw(g);
		}
	}

	/**
	 * Checks for Collisions
	 */
	private void checkForCollisions() {
		JSIEntity entity, second;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			for (int j = 0; j < entities.size(); j++) {
				second = entities.get(j);
				if ((!entity.equals(second)) && (entity.collidesWith(second))) {
					// Check for ShotEntity first because of the "used"-Variable
					// of a Shot
					if (!(entity instanceof JSIShotEntity)) {
						if (unbreakable && (entity instanceof JSIShipEntity)) {
							((JSIShipEntity) entity).collidedWith(second,
									unbreakable);
						} else {
							entity.collidedWith(second);
						}
						if (unbreakable && (second instanceof JSIShipEntity)) {
							((JSIShipEntity) second).collidedWith(entity,
									unbreakable);
						} else {
							second.collidedWith(entity);
						}
					} else {
						if (unbreakable && (second instanceof JSIShipEntity)) {
							((JSIShipEntity) second).collidedWith(entity,
									unbreakable);
						} else {
							second.collidedWith(entity);
						}
						entity.collidedWith(second);
					}
				}
			}
		}
	}

	/**
	 * Tries to spawn the big Invader
	 */
	private void tryToSpawnBigInvader() {
		JSIEntity entity = entities.get(ds.getRandomBetweenZeroAnd(entities
				.size() - 1));
		if (!bigInvaderSpawned && (entity instanceof JSIAlienEntity)) {
			if (entity instanceof JSIAlienEntity) {
				if (((JSIAlienEntity) entity).getType().getType() == 1) {
					if ((entity.getYCoordinate() > 150)
							&& (ds.getRandomBetweenZeroAnd(100) > ds
									.getAlienBigSpawningProbability())) {
						alienCount++;
						if (alienBig != null) {
							entities.add(alienBig);
							alienBig.setDx(entity.getDx() / 2);
							try {
								soc.getBigInvaderSpawnedSound().play();
							} catch (UnsupportedAudioFileException e) {
								// Do nothing
							} catch (IOException e) {
								// Do nothing
							} catch (Exception e) {
								// Do nothing
							}
						}
						bigInvaderSpawned = true;
					}
				}
			}
		}
	}

	/**
	 * Removes marked Entities
	 */
	private void removeMarkedEntities() {
		if (!entities.isEmpty() && !removeList.isEmpty()) {
			entities.removeAll(removeList);

			// Check for the Big Invader
			if (bigInvaderSpawned && !bigInvaderDead) {
				bigInvaderDead = isInRemoveList(alienBig);
			}
			removeList.clear();
		}
	}

	/**
	 * Tries to fire an Alien Shot
	 */
	private void tryToFireAlienShot() {
		JSIEntity entity;
		int random = ds.getRandomBetweenZeroAnd(100);
		if (random > ds.getAlienShotProbability()) {
			entity = entities.get(ds.getRandomBetweenZeroAnd(entities.size()));
			if (entity instanceof JSIAlienEntity) {
				alienFire(entity.getXCoordinate(), entity.getYCoordinate());
			}
		} else if ((random > (ds.getAlienShotProbability() / 2))
				&& bigInvaderSpawned && !bigInvaderDead) {
			alienBigFire(alienBig.getXCoordinate(), alienBig.getYCoordinate());
		}
	}

	/**
	 * If a Game Event has indicated that Game Logic should be resolved
	 */
	private void updateAliens() {
		if (updateThisLoop) {
			JSIEntity entity;
			for (int i = 0; i < entities.size(); i++) {
				entity = entities.get(i);
				if (entity instanceof JSIAlienEntity) {
					if (((JSIAlienEntity) entity).getType().getType() != 4) {
						((JSIAlienEntity) entity).update();
					}
				}
			}
			try {
				soc.getAlienMovementSound().play();
			} catch (UnsupportedAudioFileException e) {
				// Do nothing
			} catch (IOException e) {
				// Do nothing
			} catch (Exception e) {
				// Do nothing
			}
			updateThisLoop = false;
		}
	}

	/**
	 * Sets Mode and Score
	 */
	private void setModeAndScore() {
		g.setColor(ds.COLOR_OTHER);
		g.setFont(new Font(ds.FONT_NAME, ds.STYLE_OTHER, ds.SIZE_OTHER));
		String modeAndScore = JSIExternalStrings.mode + ": "
				+ ds.getMode().toString() + ", " + JSIExternalStrings.score
				+ ": " + score;
		if (ds.bigScreen()) {
			g.drawString(
					modeAndScore,
					(ds.WIDTH - g.getFontMetrics().stringWidth(
							modeAndScore + 10)),
					ds.HEIGHT
							- (int) (ds.WINDOW_BIGGER_FACTOR * ds.WINDOW_BIGGER_STRETCH_FACTOR));
		} else {
			g.drawString(modeAndScore, (ds.WIDTH - g.getFontMetrics()
					.stringWidth(modeAndScore + 10)), ds.HEIGHT - 15);
		}
		if (ds.bigScreen()) {
			g.drawString(
					JSIExternalStrings.pressPtoPause,
					10,
					ds.HEIGHT
							- (int) (ds.WINDOW_BIGGER_FACTOR * ds.WINDOW_BIGGER_STRETCH_FACTOR));
		} else {
			g.drawString(JSIExternalStrings.pressPtoPause, 10, ds.HEIGHT - 15);
		}

		String lifes = JSIExternalStrings.remainingLifes + ":";
		for (int i = 0; i < ship.getLifes(); i++) {
			lifes += " |";
		}
		if (ds.bigScreen()) {
			g.drawString(
					lifes,
					(ds.WIDTH / 2) - lifes.length(),
					ds.HEIGHT
							- (int) (ds.WINDOW_BIGGER_FACTOR * ds.WINDOW_BIGGER_STRETCH_FACTOR));
		} else {
			g.drawString(lifes, (ds.WIDTH / 2) - lifes.length(), ds.HEIGHT - 15);
		}
	}

	/**
	 * Handles the Players Movements
	 */
	private void updatePlayer() {
		// Ship's Movement
		ship.setDx(0.0);
		// If pressed Left
		if ((kmc.getPressedLeft()) && (!kmc.getPressedRight())) {
			ship.setDx(-ds.getshipSpeed());
			// If pressed Right
		} else if ((kmc.getPressedRight()) && (!kmc.getPressedLeft())) {
			ship.setDx(ds.getshipSpeed());
		}
		// If pressed Fire
		if (kmc.getPressedFire()) {
			fire();
		}
	}

	/**
	 * Spawns the Invaders from above
	 */
	private void spawnInvaders() {
		boolean inPauseModeCache = inPauseMode;
		inPauseMode = false;
		while (movedDown >= 0) {
			movedDown--;
			blankGraphics();
			JSIEntity entity;
			for (int i = 0; i < entities.size(); i++) {
				entity = entities.get(i);
				if (entity instanceof JSIAlienEntity) {
					((JSIAlienEntity) entity).setYCoordinate(entity
							.getYCoordinate() + 1);
					entity.draw(g);
				}
			}
			updateGraphics();
			sleep();
		}
		inPauseMode = inPauseModeCache;
		resettedGame = false;
	}

	/*********** The "Heart" of JSpaceInvaders ***********/

	/**
	 * Main Game Loop
	 */
	private void gameLoop() {
		blankGraphics();
		displayPressAnyKey(displayingStartScreen);
		updateGraphics();

		while (gameLoopRunning) {
			blankGraphics();
			if (gameRunning() && !newGameDialogueOpen() && !aboutOpen()
					&& !textWindowOpen() && !inPauseMode) {
				if (resettedGame) {
					spawnInvaders();
				}
				moveEntities();
				checkForCollisions();
				tryToSpawnBigInvader();
				removeMarkedEntities();
				if (!disguised) {
					tryToFireAlienShot();
				}
				updateAliens();
				setModeAndScore();
				updatePlayer();
			} else {
				displayPressAnyKey(displayingStartScreen);
			}
			updateGraphics();
			sleep();
		}
	}
}
