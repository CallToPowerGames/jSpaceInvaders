/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

/**
 * Cashes external Strings
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIExternalStrings {
	private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	public static JSIExternalStrings et = new JSIExternalStrings();
	private final static ResourceBundle bundle = ResourceBundle
			.getBundle("resources.language");

	/*********** Constants ***********/

	public final static String versionNumber = "v1.0.0";
	// Format: ddMMyy-hhMMss
	public final static String buildNumber = getBuild("301113-144037");
	public final static String buildNumberNumberOfBuild = "1";

	// Highscore Databse and Key File Names
	public final static String highscoreDatabaseKey = ".key.jsi";
	public final static String highscoreDatabase = ".db.jsi";

	// Mnemonics
	public final static char mnemonicAbout = bundle.getString("mnemonicAbout")
			.toCharArray()[0];
	public final static char mnemonicExit = bundle.getString("mnemonicExit")
			.toCharArray()[0];
	public final static char mnemonicNewGame = bundle.getString(
			"mnemonicNewGame").toCharArray()[0];
	public final static char mnemonicPause = bundle.getString("mnemonicPause")
			.toCharArray()[0];
	public final static char mnemonicHighscore = bundle.getString(
			"mnemonicHighscore").toCharArray()[0];
	public final static char mnemonicHistory = bundle.getString(
			"mnemonicHistory").toCharArray()[0];
	public final static char mnemonicGameplay = bundle.getString(
			"mnemonicGameplay").toCharArray()[0];
	public final static char mnemonicControls = bundle.getString(
			"mnemonicControls").toCharArray()[0];
	public final static char mnemonicAcknowledgementsAndCopyright = bundle
			.getString("mnemonicAcknowledgementsAndCopyright").toCharArray()[0];
	public final static char mnemonicChangelog = bundle.getString(
			"mnemonicChangelog").toCharArray()[0];

	// Modes
	public final static String modeWTF = bundle.getString("modeWTF");
	public final static String modeNooby = bundle.getString("modeNooby");
	public final static String modeEasy = bundle.getString("modeEasy");
	public final static String modeNormal = bundle.getString("modeNormal");
	public final static String modeHard = bundle.getString("modeHard");
	public final static String modeInsane = bundle.getString("modeInsane");

	// HighscoreTable
	public final static String unknownName = bundle.getString("unknownName");
	public final static String enterYourName = bundle
			.getString("enterYourName");
	public final static String highscore = bundle.getString("highscore");
	public final static String resetHighscoreList = bundle
			.getString("resetHighscoreList");
	public final static String died = bundle.getString("died");
	public final static String survived = bundle.getString("survived");
	public final static String name = bundle.getString("name");
	public final static String status = bundle.getString("status");
	public final static String place = bundle.getString("place");

	// Images
	public final static String imageLogo = "images/logo.png";
	public final static String imageBackground = "images/background.png";
	public final static String imageDockAndSplashscreen = "images/space_invaders.png";
	// Sprites
	public final static String spriteAlien1 = "sprites/alien1.gif";
	public final static String spriteAlien2 = "sprites/alien2.gif";
	public final static String spriteAlien3 = "sprites/alien3.gif";
	public final static String spriteAlienBig = "sprites/alienBig.png";
	public final static String spriteInvaderKilled = "sprites/invaderKilled.gif";
	public final static String spriteInvaderKilledBig = "sprites/invaderKilledBig.gif";
	public final static String spritePlayerKilled = "sprites/playerKilled.gif";
	public final static String spriteShip = "sprites/ship.gif";
	public final static String spriteShot1 = "sprites/shot1.gif";
	public final static String spriteShot2 = "sprites/shot2.gif";
	public final static String spriteFunFuface = "sprites/fun/fuface.png";
	public final static String spriteFunLolface = "sprites/fun/lolface.png";
	// Sounds
	public final static String soundBigInvaderSpawned = "sounds/bigInvaderSpawned.wav";
	public final static String soundMovementInvader = "sounds/movementInvader.wav";
	public final static String soundShotPlayer = "sounds/shotPlayer.wav";
	public final static String soundShowInvader = "sounds/shotInvader.wav";
	public final static String soundInvaderKilled = "sounds/invaderKilled.wav";
	public final static String soundExplosion = "sounds/explosion.wav";
	public final static String soundAccepted = "sounds/accepted.wav";
	public final static String soundRejected = "sounds/rejected.wav";
	// Documents
	public final static String documentHistory = "documents/history";
	public final static String documentGameplay = "documents/gameplay";
	public final static String documentControls = "documents/controls";
	public final static String documentAcknowledgementsAndCopyright = "documents/acknowledgementsAndCopyright";
	public final static String documentChangelog = "documents/changelog";
	// Links
	public final static String wikiHistory = bundle.getString("wikiHistory");
	public final static String wikiGameplay = bundle.getString("wikiGameplay");
	public final static String wikiHistoryURL = bundle
			.getString("wikiHistoryURL");
	public final static String wikiGameplayURL = bundle
			.getString("wikiGameplayURL");
	// More
	public final static String image_cavePainting_name = bundle
			.getString("cavePainting");
	public final static String image_cavePainting_image = "images/cavePainting.jpg";
	public final static String image_makingOfTetris_name = bundle
			.getString("makingOfTetris");
	public final static String image_makingOfTetris_image = "images/makingOfTetris.jpg";
	public final static String image_steampunk_name = bundle
			.getString("steampunk");
	public final static String image_steampunk_image = "images/steampunk.jpg";
	public final static String image_masterplan_name = bundle
			.getString("masterplan");
	public final static String image_masterplan_image = "images/masterplan.jpg";
	public final static String next = bundle.getString("next");
	public final static String previous = bundle.getString("previous");
	public final static String console = bundle.getString("console");
	public final static String consoleInitialText = bundle
			.getString("consoleInitialText");

	// Program
	public final static String CallToPower = "CallToPower";
	public final static String calltopowerSoftware = "CallToPower Software";
	public final static String denisMeyer = "Denis Meyer (" + CallToPower + ")";
	public final static String jSpaceInvadersMainTitle = "jSpaceInvaders "
			+ " | " + calltopowerSoftware;
	public final static String aboutjSpaceInvaders = bundle.getString("About")
			+ " jSpaceInvaders";
	public final static String ok = bundle.getString("ok");
	public final static String about = bundle.getString("About");
	public final static String choseAMode = bundle.getString("choseAMode");

	// MenuItems
	public final static String menuHelp = bundle.getString("menuHelp");
	public final static String menujSpaceInvaders = bundle
			.getString("menujSpaceInvaders");
	public final static String menuAbout = bundle.getString("menuAbout");
	public final static String menuQuit = bundle.getString("menuQuit");
	public final static String menuOptions = bundle.getString("menuOptions");
	public final static String menuNewGame = bundle.getString("menuNewGame");
	public final static String menuConsole = bundle.getString("menuConsole");
	public final static String menuPauseGame = bundle
			.getString("menuPauseGame");
	public final static String menuHighscore = bundle
			.getString("menuHighscore");
	public final static String menuHistory = bundle.getString("menuHistory");
	public final static String menuGameplay = bundle.getString("menuGameplay");
	public final static String menuControls = bundle.getString("menuControls");
	public final static String menuAcknowledgementsandCopyright = bundle
			.getString("menuAcknowledgementsandCopyright");
	public final static String menuChangeLog = bundle
			.getString("menuChangeLog");
	public final static String menuMore = bundle.getString("menuMore");

	// Display Data
	public final static String PTS = bundle.getString("PTS");
	public final static String PTS_INFO = bundle.getString("PTS_INFO");
	public final static String mode = bundle.getString("mode");
	public final static String score = bundle.getString("score");
	public final static String remainingLifes = bundle
			.getString("remainingLifes");

	// About
	public final static String jSpaceInvaders = bundle
			.getString("jSpaceInvaders");
	public final static String author = bundle.getString("author");
	public final static String build = bundle.getString("build");

	// Questions/Errors
	public final static String error = bundle.getString("error");
	public final static String quitting = bundle.getString("quitting");
	public final static String quitGame = bundle.getString("quitGame");
	public final static String quitGameQuestion = bundle
			.getString("quitGameQuestion");
	public final static String startNewGame = bundle.getString("startNewGame");
	public final static String abortAndStartNewGame = bundle
			.getString("abortAndStartNewGame");
	public final static String cantFindReferenceToSprite = bundle
			.getString("cantFindReferenceToSprite");
	public final static String cantFindReferenceToSound = bundle
			.getString("cantFindReferenceToSound");
	public final static String resetHighscoreQuestion = bundle
			.getString("resetHighscoreQuestion");

	// Pause/Resume
	public final static String pauseGame = bundle.getString("pauseGame");
	public final static String resumeGame = bundle.getString("resumeGame");
	public final static String pressPtoPause = bundle
			.getString("pressPtoPause");

	// TextWindows
	public final static String history = bundle.getString("history");
	public final static String gameplay = bundle.getString("gameplay");
	public final static String controls = bundle.getString("controls");
	public final static String acknowledgementsAndCopyright = bundle
			.getString("acknowledgementsAndCopyright");
	public final static String changelog = bundle.getString("changelog");

	// Messages
	public final static String MESSAGE_WELCOME = bundle
			.getString("MESSAGE_WELCOME");
	public final static String MESSAGE_LOST = bundle.getString("MESSAGE_LOST");
	public final static String MESSAGE_LIFE_LOST = bundle
			.getString("MESSAGE_LIFE_LOST");
	public final static String MESSAGE_PAUSE = bundle
			.getString("MESSAGE_PAUSE");
	public final static String MESSAGE_WINDOW_DEACTIVATED = bundle
			.getString("MESSAGE_WINDOW_DEACTIVATED");
	public final static String MESSAGE_WIN = bundle.getString("MESSAGE_WIN");
	public final static String MESSAGE_PRESS_ANY_KEY = bundle
			.getString("MESSAGE_PRESS_ANY_KEY");

	// Help Strings
	public static String HISTORY;
	public static String GAMEPLAY;
	public static String CONTROLS;
	public static String ACKNOWLEDGEMENTS_AND_COPYRIGHT;
	public static String CHANGELOG;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 */
	private JSIExternalStrings() {
		try {
			setInputStreams();
		} catch (IOException ioe) {
			// Do nothing
		}
	}

	/*********** Functions ***********/

	/**
	 * Sets the InputStreams
	 * 
	 * @throws IOException
	 */
	private void setInputStreams() throws IOException {
		InputStream is_history = ClassLoader
				.getSystemResourceAsStream(documentHistory);
		InputStream is_gameplay = ClassLoader
				.getSystemResourceAsStream(documentGameplay);
		InputStream is_controls = ClassLoader
				.getSystemResourceAsStream(documentControls);
		InputStream is_acknowledgementsAndCopyright = ClassLoader
				.getSystemResourceAsStream(documentAcknowledgementsAndCopyright);
		InputStream is_changelog = ClassLoader
				.getSystemResourceAsStream(documentChangelog);
		if (is_history != null) {
			HISTORY = getTextFromInputStream(is_history);
		} else {
			HISTORY = bundle.getString("failedToOpen") + " '" + documentHistory
					+ "'";
		}
		if (is_gameplay != null) {
			GAMEPLAY = getTextFromInputStream(is_gameplay);
		} else {
			GAMEPLAY = bundle.getString("failedToOpen") + " '"
					+ documentGameplay + "'";
		}
		if (is_controls != null) {
			CONTROLS = getTextFromInputStream(is_controls);
		} else {
			CONTROLS = bundle.getString("failedToOpen") + " '"
					+ documentControls + "'";
		}
		if (is_acknowledgementsAndCopyright != null) {
			ACKNOWLEDGEMENTS_AND_COPYRIGHT = getTextFromInputStream(is_acknowledgementsAndCopyright);
		} else {
			ACKNOWLEDGEMENTS_AND_COPYRIGHT = bundle.getString("failedToOpen")
					+ " '" + documentAcknowledgementsAndCopyright + "'";
		}
		if (is_changelog != null) {
			CHANGELOG = getTextFromInputStream(is_changelog);
		} else {
			CHANGELOG = bundle.getString("failedToOpen") + " '"
					+ documentChangelog + "'";
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns the Build
	 * 
	 * @param s
	 *            Formatted Input
	 * @return The Build if correct formatted Input, s else
	 */
	private static String getBuild(String s) {
		StringBuilder newS = new StringBuilder("");
		char[] charArrS = s.toCharArray();
		if (charArrS.length == 13) {
			newS.append(String.valueOf(Integer.parseInt(String
					.valueOf(charArrS[0]))
					+ Integer.parseInt(String.valueOf(charArrS[1]))));
			newS.append(String.valueOf(Integer.parseInt(String
					.valueOf(charArrS[2]))
					+ Integer.parseInt(String.valueOf(charArrS[3]))));
			newS.append(String.valueOf(Integer.parseInt(String
					.valueOf(charArrS[4]))
					+ Integer.parseInt(String.valueOf(charArrS[5]))));
			newS.append(".");
			newS.append(String.valueOf(Integer.parseInt(String
					.valueOf(charArrS[7]))
					+ Integer.parseInt(String.valueOf(charArrS[8]))));
			newS.append(String.valueOf(Integer.parseInt(String
					.valueOf(charArrS[9]))
					+ Integer.parseInt(String.valueOf(charArrS[10]))));
			newS.append(String.valueOf(Integer.parseInt(String
					.valueOf(charArrS[11]))
					+ Integer.parseInt(String.valueOf(charArrS[12]))));
			newS.append(".");
			newS.append(buildNumberNumberOfBuild);
		} else {
			newS.append(s);
		}
		return newS.toString();
	}

	/**
	 * Returns the Text from an InputStream
	 * 
	 * @param in
	 *            File to read Text from
	 * @return Text from a File
	 * @throws IOException
	 */
	private String getTextFromInputStream(InputStream in) throws IOException {
		StringBuilder content = new StringBuilder();
		if (in != null) {
			BufferedReader input = new BufferedReader(new InputStreamReader(in));
			String line = null;
			if (!ds.useTextArea) {
				content.append("<html><head></head><body>");
			}
			while ((line = input.readLine()) != null) {
				if (ds.useTextArea) {
					content.append(" " + line);
					content.append(System.getProperty("line.separator"));
				} else {
					content.append("&#160;" + line);
					content.append("<br>");
				}
			}
			if (!ds.useTextArea) {
				content.append("</body></html>");
			}
			return content.toString();
		}

		return "";
	}

	/**
	 * Returns a String from the ResourceBundle
	 * 
	 * @return a String from the ResourceBundle if possible, "" else
	 */
	public static String getStringFromResourceBundle(String string) {
		String ret = "";
		ret = bundle.getString(string);
		return ret;
	}

	/**
	 * Returns the static ExternalStrings-Object
	 * 
	 * @return the static ExternalStrings-Object
	 */
	public static JSIExternalStrings getExternalStrings() {
		return et;
	}
}
