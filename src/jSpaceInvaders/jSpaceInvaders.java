/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package jSpaceInvaders;

import view.JSISplashScreen;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.apple.eawt.Application;

import controller.JSIGameController;
import controller.JSIMacOSHandler;
import data.JSIDataStorage;
import data.JSIExternalStrings;
import data.JSIImageSpriteCache;
import data.JSISoundCache;

/**
 * Spave Invaders
 * 
 * @author Denis Meyer (CallToPower)
 */
public class jSpaceInvaders {
	private static JSIDataStorage ds;
	private static JSIGameController gc;
	private static JSISplashScreen screen;

	/**
	 * Main Function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (System.getProperty("os.name").startsWith("Mac")) {
			// Putting the Application Name on the MenuBar
			System.setProperty(
					"com.apple.mrj.application.apple.menu.about.name",
					"jSpaceInvaders");
			// Set MenuBar to the Top of the Screen
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.macos.smallTabs", "true");
		}

		// Try to set Native LookAndFeel (LAF)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// Do nothing
		} catch (InstantiationException e) {
			// Do nothing
		} catch (IllegalAccessException e) {
			// Do nothing
		} catch (UnsupportedLookAndFeelException e) {
			// Do nothing
		} catch (Exception e) {
			// Do nothing
		}

		// Cashe ExternalStrings
		JSIExternalStrings.getExternalStrings();

		// Get the DataStorage
		ds = JSIDataStorage.getDataStorage();
		// Initialise Game Controller
		gc = new JSIGameController(false);
		// Initialise Splashscreen
		splashScreenInit(gc);

		setSplashScreenFromTo(0, 15);
		if (ds.is("Mac")) {
			try {
				Image iconDock = ImageIO.read(gc
						.getClass()
						.getClassLoader()
						.getResource(
								JSIExternalStrings.imageDockAndSplashscreen));
				if (iconDock != null) {
					Application.getApplication().setDockIconImage(iconDock);
				}
			} catch (IOException ioe) {
				// Do nothing
			} catch (Exception e) {
				// Do nothing
			}
			new JSIMacOSHandler(gc);
		}

		// Cashe GC 1
		setSplashScreenFromTo(16, 30);
		gc.initFirst();
		// Cashe GC 2
		setSplashScreenFromTo(31, 50);
		gc.initSecond();
		// Cashe GC 3
		setSplashScreenFromTo(51, 65);
		gc.initThird();
		// Cashe Images and Sprites
		setSplashScreenFromTo(66, 85);
		JSIImageSpriteCache isc = JSIImageSpriteCache.getImageSpriteCache();
		try {
			isc.preCache();
		} catch (IOException e) {
			gc.printErrorAndQuit("IOException: JSIImageSpriteCache"
					+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		} catch (Exception e) {
			gc.printErrorAndQuit("Exception: JSIImageSpriteCache"
					+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		}
		// Cashe Sounds
		setSplashScreenFromTo(86, 100);
		JSISoundCache sc = JSISoundCache.getSoundCache();
		try {
			sc.preCache();
		} catch (IOException e) {
			gc.printErrorAndQuit("IOException: JSISoundCache.preCache"
					+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		} catch (Exception e) {
			gc.printErrorAndQuit("Exception: JSISoundCache.preCache"
					+ JSIExternalStrings.quitting + "...",
					JSIExternalStrings.error);
		}

		// Set 100%-Status
		if (screen != null) {
			screen.setProgress(100);
			screen.setScreenVisible(false);
		}

		// Start the Game
		gc.startGame();
	}

	/**
	 * Sets the SplashScreen
	 * 
	 * @param screen
	 * @param from
	 * @param to
	 */
	private static void setSplashScreenFromTo(int from, int to) {
		if ((screen != null) && (from >= 0) && (to <= 100)) {
			try {
				for (int i = from; i < to; i++) {
					screen.setProgress(
							i
									+ "% - "
									+ JSIExternalStrings
											.getStringFromResourceBundle("loading")
									+ "...", i);
					Thread.sleep(ds.THREAD_SLEEP);
				}
			} catch (InterruptedException e) {
				gc.printErrorAndQuit(
						"InterruptedException: Thread.sleep in setSplashScreenFromTo"
								+ JSIExternalStrings.quitting + "...",
						JSIExternalStrings.error);
			}
		}
	}

	/**
	 * Initialises the Splashscreen
	 */
	private static void splashScreenInit(JSIGameController gc) {
		ImageIcon logo = new ImageIcon(gc.getClass().getClassLoader()
				.getResource(JSIExternalStrings.imageDockAndSplashscreen));
		if (logo != null) {
			screen = new JSISplashScreen(logo);
			screen.setLocationRelativeTo(null);
			screen.setProgressMax(100);
			screen.setScreenVisible(true);
		}
	}
}
