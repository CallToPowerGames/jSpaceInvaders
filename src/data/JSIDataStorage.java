/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package data;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

/**
 * DataStorage
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIDataStorage {
	public static JSIDataStorage ds = new JSIDataStorage();

	/*********** Constants ***********/

	/** Variable not Changeable */

	// Window Settings
	public int WIDTH = 800;
	public int HEIGHT = 600;
	public int WINDOW_BIGGER_FACTOR = 0;
	// Only needed if WINDOW_BIGGER_FACTOR is defined
	public double WINDOW_BIGGER_STRETCH_FACTOR = 1.5;
	// Text Settings
	public final String FONT_NAME = "monospaced";
	public final Color COLOR_MARQUEE = Color.green;
	public final Color COLOR_OTHER = Color.white;
	public final int STYLE_MAIN = Font.BOLD;
	public final int STYLE_OTHER = Font.PLAIN;
	public final int SIZE_MAIN = 14;
	public final int SIZE_OTHER = 12;
	// Thread Settings
	public final long THREAD_SLEEP = 20;
	public final long THREAD_SLEEP_PAUSE_MODE = 200;
	public final double MOVE_FACTOR = 25.0;
	public final double MOVE_FACTOR_BIG_INVADERS = MOVE_FACTOR * 1.6;
	// Probability for AlienShots between 1 and 100 (Higher == less probable)
	public final int ALIEN_SHOT_PROBABILITY_WTF = 20;
	public final int ALIEN_SHOT_PROBABILITY_NOOBY = 70;
	public final int ALIEN_SHOT_PROBABILITY_EASY = 60;
	public final int ALIEN_SHOT_PROBABILITY_NORMAL = 50;
	public final int ALIEN_SHOT_PROBABILITY_HARD = 40;
	public final int ALIEN_SHOT_PROBABILITY_INSANE = 30;
	// Probability for BigAlienShip spawning between 1 and 100 (Higher == less
	// probable)
	public final int ALIEN_BIG_SPAWNING_PROBABILITY_WTF = 10;
	public final int ALIEN_BIG_SPAWNING_PROBABILITY_NOOBY = 20;
	public final int ALIEN_BIG_SPAWNING_PROBABILITY_EASY = 30;
	public final int ALIEN_BIG_SPAWNING_PROBABILITY_NORMAL = 50;
	public final int ALIEN_BIG_SPAWNING_PROBABILITY_HARD = 70;
	public final int ALIEN_BIG_SPAWNING_PROBABILITY_INSANE = 80;
	// Lifes
	public final int LIFES_WTF = 3;
	public final int LIFES_NOOBY = 4;
	public final int LIFES_EASY = 3;
	public final int LIFES_NORMAL = 2;
	public final int LIFES_HARD = 2;
	public final int LIFES_INSANE = 2;
	// Alien Points
	public final double ALIEN_TYPE_3_POINTS = 10.0;
	public final double ALIEN_TYPE_2_POINTS = 20.0;
	public final double ALIEN_TYPE_1_POINTS = 40.0;
	public final double ALIEN_TYPE_4_POINTS = 100.0;

	/** Variable Changeable */

	// The Interval between the Players Shots (ms)
	public final double SHIP_FIRING_INTERVALL_WTF = 50.0;
	public final double SHIP_FIRING_INTERVALL_NOOBY = 100.0;
	public final double SHIP_FIRING_INTERVALL_EASY = 300.0;
	public final double SHIP_FIRING_INTERVALL_NORMAL = 500.0;
	public final double SHIP_FIRING_INTERVALL_HARD = 600.0;
	public final double SHIP_FIRING_INTERVALL_INSANE = 700.0;
	// The Interval between the Aliens Shots (ms) [+random]
	public final double ALIEN_FIRING_INTERVALL_WTF = 500.0;
	public final double ALIEN_FIRING_INTERVALL_NOOBY = 2200.0;
	public final double ALIEN_FIRING_INTERVALL_EASY = 2000.0;
	public final double ALIEN_FIRING_INTERVALL_NORMAL = 1800.0;
	public final double ALIEN_FIRING_INTERVALL_HARD = 1650.0;
	public final double ALIEN_FIRING_INTERVALL_INSANE = 1550.0;
	// Speed up Factor
	public final double SPEED_UP_FACTOR_WTF = 1.05;
	public final double SPEED_UP_FACTOR_NOOBY = 1.00;
	public final double SPEED_UP_FACTOR_EASY = 1.01;
	public final double SPEED_UP_FACTOR_NORMAL = 1.02;
	public final double SPEED_UP_FACTOR_HARD = 1.03;
	public final double SPEED_UP_FACTOR_INSANE = 1.035;
	// Ship Speed
	public final double SHIP_SPEED_WTF = 600.0;
	public final double SHIP_SPEED_NOOBY = 400.0;
	public final double SHIP_SPEED_EASY = 350.0;
	public final double SHIP_SPEED_NORMAL = 300.0;
	public final double SHIP_SPEED_HARD = 250.0;
	public final double SHIP_SPEED_INSANE = 200.0;
	// Alien Speed
	public final double ALIEN_SPEED_WTF = -100.0;
	public final double ALIEN_SPEED_NOOBY = -60.0;
	public final double ALIEN_SPEED_EASY = -70.0;
	public final double ALIEN_SPEED_NORMAL = -75.0;
	public final double ALIEN_SPEED_HARD = -80.0;
	public final double ALIEN_SPEED_INSANE = -85.0;
	// Ship Shot Speed
	public final double SHIP_SHOT_SPEED_WTF = -900.0;
	public final double SHIP_SHOT_SPEED_NOOBY = -600.0;
	public final double SHIP_SHOT_SPEED_EASY = -550.0;
	public final double SHIP_SHOT_SPEED_NORMAL = -500.0;
	public final double SHIP_SHOT_SPEED_HARD = -450.0;
	public final double SHIP_SHOT_SPEED_INSANE = -400.0;
	// Alien Shot Speed
	public final double ALIEN_SHOT_SPEED_WTF = 20.0;
	public final double ALIEN_SHOT_SPEED_NOOBY = 5.0;
	public final double ALIEN_SHOT_SPEED_EASY = 7.5;
	public final double ALIEN_SHOT_SPEED_NORMAL = 10.0;
	public final double ALIEN_SHOT_SPEED_HARD = 12.5;
	public final double ALIEN_SHOT_SPEED_INSANE = 15.0;
	// Number of Rows/Columns of Aliens
	public final int ROWS = 5;
	public final int COLUMNS = 13;

	// Flag whether using a JTextArea or a JLabel
	public final boolean useTextArea = false;
	// Flag whether Windows are resizable
	public final boolean resizable = false;
	// whether some of the Text marquees
	public final boolean MARQUEE_ON = true;

	/*********** Other ***********/

	/**
	 * Mode Enumeration
	 */
	public enum Mode {
		WTF, NOOBY, EASY, NORMAL, HARD, INSANE
	}

	private Random random = new Random();

	/*********** Variables ***********/

	// Current Mode
	private Mode mode;

	// The Interval between the Players Shots (ms)
	private double shipFiringIntervall;
	// The Interval between the Aliens Shots (ms) [+random]
	private double alienFiringIntervall;
	// Speed up Factor
	private double speedUpFactor;
	// Ship Speed
	private double shipSpeed;
	// Entity related Data
	public double alienSpeed;
	public double shipShotSpeed;
	public double alienShotSpeed;
	// Number of Rows/Columns of Aliens
	private int rows;
	private int columns;

	/*********** Constructors ***********/

	/**
	 * Constructor
	 */
	private JSIDataStorage() {
		mode = Mode.NORMAL;
		shipFiringIntervall = SHIP_FIRING_INTERVALL_NORMAL;
		alienFiringIntervall = ALIEN_FIRING_INTERVALL_NORMAL;
		speedUpFactor = SPEED_UP_FACTOR_NORMAL;
		shipSpeed = SHIP_SPEED_NORMAL;
		alienSpeed = ALIEN_SPEED_NORMAL;
		shipShotSpeed = SHIP_SHOT_SPEED_NORMAL;
		alienShotSpeed = ALIEN_SHOT_SPEED_NORMAL;
		rows = ROWS;
		columns = COLUMNS;

		// Under Windows: Get a bigger Window
		if (is("Windows")) {
			WINDOW_BIGGER_FACTOR = 25;
			HEIGHT += WINDOW_BIGGER_FACTOR;
			WINDOW_BIGGER_STRETCH_FACTOR = 1.5;
		} else if (contains("Linux")) {
			WINDOW_BIGGER_FACTOR = 30;
			HEIGHT += WINDOW_BIGGER_FACTOR;
			WINDOW_BIGGER_STRETCH_FACTOR = 1.5;
		}
	}

	/**
	 * Returns the DataStorage
	 * 
	 * @return the DataStorage
	 */
	public static JSIDataStorage getDataStorage() {
		return ds;
	}

	/*********** Boolean ***********/

	/**
	 * Returns if running on a Big Screen
	 * 
	 * @return true if running on a Big Screen, false else
	 */
	public boolean bigScreen() {
		return (WINDOW_BIGGER_FACTOR != 0);
	}

	/**
	 * Returns if the current running Operating System is 'os' or not
	 * 
	 * @param os
	 *            Operating System
	 * 
	 * @return true if the current running Operating System is 'os', false else
	 */
	public boolean is(String os) {
		return (System.getProperty("os.name").startsWith(os) ? true : false);
	}

	/**
	 * Returns if the current running Operating System contains 'os' or not
	 * 
	 * @param os
	 *            Operating System
	 * 
	 * @return true if the current running Operating System contains 'os', false
	 *         else
	 */
	public boolean contains(String os) {
		return (System.getProperty("os.name").contains(os) ? true : false);
	}

	/**
	 * Updates the Values after a Change of the Mode
	 */
	private void updateValues() {
		switch (mode) {
		case WTF:
			setValues(SHIP_FIRING_INTERVALL_WTF, ALIEN_FIRING_INTERVALL_WTF,
					SPEED_UP_FACTOR_WTF, SHIP_SPEED_WTF, ALIEN_SPEED_WTF,
					SHIP_SHOT_SPEED_WTF, ALIEN_SHOT_SPEED_WTF);
			break;
		case NOOBY:
			setValues(SHIP_FIRING_INTERVALL_NOOBY,
					ALIEN_FIRING_INTERVALL_NOOBY, SPEED_UP_FACTOR_NOOBY,
					SHIP_SPEED_NOOBY, ALIEN_SPEED_NOOBY, SHIP_SHOT_SPEED_NOOBY,
					ALIEN_SHOT_SPEED_NOOBY);
			break;
		case EASY:
			setValues(SHIP_FIRING_INTERVALL_EASY, ALIEN_FIRING_INTERVALL_EASY,
					SPEED_UP_FACTOR_EASY, SHIP_SPEED_EASY, ALIEN_SPEED_EASY,
					SHIP_SHOT_SPEED_EASY, ALIEN_SHOT_SPEED_EASY);
			break;
		case NORMAL:
			setValues(SHIP_FIRING_INTERVALL_NORMAL,
					ALIEN_FIRING_INTERVALL_NORMAL, SPEED_UP_FACTOR_NORMAL,
					SHIP_SPEED_NORMAL, ALIEN_SPEED_NORMAL,
					SHIP_SHOT_SPEED_NORMAL, ALIEN_SHOT_SPEED_NORMAL);
			break;
		case HARD:
			setValues(SHIP_FIRING_INTERVALL_HARD, ALIEN_FIRING_INTERVALL_HARD,
					SPEED_UP_FACTOR_HARD, SHIP_SPEED_HARD, ALIEN_SPEED_HARD,
					SHIP_SHOT_SPEED_HARD, ALIEN_SHOT_SPEED_HARD);
			break;
		case INSANE:
			setValues(SHIP_FIRING_INTERVALL_INSANE,
					ALIEN_FIRING_INTERVALL_INSANE, SPEED_UP_FACTOR_INSANE,
					SHIP_SPEED_INSANE, ALIEN_SPEED_INSANE,
					SHIP_SHOT_SPEED_INSANE, ALIEN_SHOT_SPEED_INSANE);
			break;
		default:
			setValues(SHIP_FIRING_INTERVALL_NORMAL,
					ALIEN_FIRING_INTERVALL_NORMAL, SPEED_UP_FACTOR_NORMAL,
					SHIP_SPEED_NORMAL, ALIEN_SPEED_NORMAL,
					SHIP_SHOT_SPEED_NORMAL, ALIEN_SHOT_SPEED_NORMAL);
			break;
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns the Mode in String Representation
	 * 
	 * @return the Mode in String Representation
	 */
	public String getModeToString() {
		return getModeToString(mode);
	}

	/**
	 * Returns the Mode in String Representation
	 * 
	 * @return the Mode in String Representation
	 */
	public String getModeToString(Mode mode) {
		switch (mode) {
		case WTF:
			return JSIExternalStrings.modeWTF;
		case NOOBY:
			return JSIExternalStrings.modeNooby;
		case EASY:
			return JSIExternalStrings.modeEasy;
		case NORMAL:
			return JSIExternalStrings.modeNormal;
		case HARD:
			return JSIExternalStrings.modeHard;
		case INSANE:
			return JSIExternalStrings.modeInsane;
		default:
			return JSIExternalStrings.modeNormal;
		}
	}

	/**
	 * Returns the Alien Shoot Probability between 1 and 100
	 * 
	 * @return the Alien Shoot Probability between 1 and 100
	 */
	public int getAlienShotProbability() {
		switch (mode) {
		case WTF:
			return ALIEN_SHOT_PROBABILITY_WTF;
		case NOOBY:
			return ALIEN_SHOT_PROBABILITY_NOOBY;
		case EASY:
			return ALIEN_SHOT_PROBABILITY_EASY;
		case NORMAL:
			return ALIEN_SHOT_PROBABILITY_NORMAL;
		case HARD:
			return ALIEN_SHOT_PROBABILITY_HARD;
		case INSANE:
			return ALIEN_SHOT_PROBABILITY_INSANE;
		default:
			return ALIEN_SHOT_PROBABILITY_NORMAL;
		}
	}

	/**
	 * Returns the Alien Big Spawning between 1 and 100
	 * 
	 * @return the Alien Big Spawning Probability between 1 and 100
	 */
	public int getAlienBigSpawningProbability() {
		switch (mode) {
		case WTF:
			return ALIEN_BIG_SPAWNING_PROBABILITY_WTF;
		case NOOBY:
			return ALIEN_BIG_SPAWNING_PROBABILITY_NOOBY;
		case EASY:
			return ALIEN_BIG_SPAWNING_PROBABILITY_EASY;
		case NORMAL:
			return ALIEN_BIG_SPAWNING_PROBABILITY_NORMAL;
		case HARD:
			return ALIEN_BIG_SPAWNING_PROBABILITY_HARD;
		case INSANE:
			return ALIEN_BIG_SPAWNING_PROBABILITY_INSANE;
		default:
			return ALIEN_BIG_SPAWNING_PROBABILITY_NORMAL;
		}
	}

	/**
	 * Returns the current Mode
	 * 
	 * @return Mode
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * Returns the shipFiringIntervall
	 * 
	 * @return the shipFiringIntervall
	 */
	public double getShipFiringIntervall() {
		return shipFiringIntervall;
	}

	/**
	 * Returns the alienFiringIntervall
	 * 
	 * @return the alienFiringIntervall
	 */
	public double getAlienFiringIntervall() {
		return alienFiringIntervall;
	}

	/**
	 * Returns the speedUpFactor
	 * 
	 * @return the speedUpFactor
	 */
	public double getSpeedUpFactor() {
		return speedUpFactor;
	}

	/**
	 * Returns the shipSpeed
	 * 
	 * @return the shipSpeed
	 */
	public double getshipSpeed() {
		return shipSpeed;
	}

	/**
	 * Returns the alienSpeed
	 * 
	 * @return the alienSpeed
	 */
	public double getAlienSpeed() {
		return alienSpeed;
	}

	/**
	 * Returns the shipShotSpeed
	 * 
	 * @return the shipShotSpeed
	 */
	public double getShipShotSpeed() {
		return shipShotSpeed;
	}

	/**
	 * Returns the alienShotSpeed
	 * 
	 * @return the alienShotSpeed
	 */
	public double getAlienShotSpeed() {
		return alienShotSpeed;
	}

	/**
	 * Returns the rows
	 * 
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Returns the columns
	 * 
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Returns a random Integer between 0 and x or if x < 0 between 0 and 100
	 * 
	 * @param x
	 *            upper Limit
	 * @return a random Integer between 0 and x or if x < 0 between 0 and 100
	 */
	public int getRandomBetweenZeroAnd(int x) {
		if (x > 0) {
			return random.nextInt(x);
		}
		return random.nextInt(100);
	}

	/**
	 * Returns a random Number between lowerLimit and upperLimit
	 * 
	 * @param lowerLimit
	 * @param upperLimit
	 * @return a random Number between lowerLimit and upperLimit, -999999999 if
	 *         something went wrong
	 */
	public int getRandomIntWithinLimits(int lowerLimit, int upperLimit) {
		if (lowerLimit > upperLimit) {
			return -999999999;
		}
		long range = (long) upperLimit - (long) lowerLimit + 1;
		long fraction = (long) (range * Math.random());

		return (int) (fraction + lowerLimit);
	}

	/**
	 * Returns a random Double Number between lowerLimit and upperLimit
	 * 
	 * @param lowerLimit
	 * @param upperLimit
	 * @return a random Double Number between lowerLimit and upperLimit,
	 *         -99999999999999999999.9 if something went wrong
	 */
	public double getRandomDoubleWithinLimits(double lowerLimit,
			double upperLimit) {
		if (lowerLimit > upperLimit) {
			return -99999999999999999999.9;
		}
		double range = upperLimit - lowerLimit;
		double fraction = (range * Math.random());

		return fraction + lowerLimit;
	}

	/*********** Setter ***********/

	/**
	 * Sets a new Mode
	 * 
	 * @param mode
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
		updateValues();
	}

	/**
	 * Sets a new Ship firing Intervall
	 * 
	 * @param shipFiringIntervall
	 */
	public void setShipFiringIntervall(double shipFiringIntervall) {
		this.shipFiringIntervall = shipFiringIntervall;
	}

	/**
	 * Sets a new Alien firing Intervall
	 * 
	 * @param alienFiringIntervall
	 */
	public void setAlienFiringIntervall(double alienFiringIntervall) {
		this.alienFiringIntervall = alienFiringIntervall;
	}

	/**
	 * Sets a new speedUpFactor
	 * 
	 * @param speedUpFactor
	 */
	public void setSpeedUpFactor(double speedUpFactor) {
		this.speedUpFactor = speedUpFactor;
	}

	/**
	 * Sets a new shipSpeed
	 * 
	 * @param shipSpeed
	 */
	public void setShipSpeed(double shipSpeed) {
		this.shipSpeed = shipSpeed;
	}

	/**
	 * Sets a new alienSpeed
	 * 
	 * @param alienSpeed
	 */
	public void setAlienSpeed(double alienSpeed) {
		this.alienSpeed = alienSpeed;
	}

	/**
	 * Sets a new shipShotSpeed
	 * 
	 * @param shipShotSpeed
	 */
	public void setShipShotSpeed(double shipShotSpeed) {
		this.shipShotSpeed = shipShotSpeed;
	}

	/**
	 * Sets a new alienShotSpeed
	 * 
	 * @param alienShotSpeed
	 */
	public void setAlienShotSpeed(double alienShotSpeed) {
		this.alienShotSpeed = alienShotSpeed;
	}

	/**
	 * Sets a new rows
	 * 
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Sets a new columns
	 * 
	 * @param columns
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * Sets the Values
	 * 
	 * @param fiInt
	 *            Ship Firing Intervall
	 * @param aliFiInt
	 *            Alien Firing Intervall
	 * @param spFac
	 *            Speed-up Factor
	 * @param shiSp
	 *            Ship Speed
	 * @param alSpe
	 *            Alien Speed
	 * @param shiShoSp
	 *            Ship Shot Speed
	 * @param aliShoSp
	 *            Alien Shot Speed
	 */
	private void setValues(double fiInt, double aliFiInt, double spFac,
			double shiSp, double alSpe, double shiShoSp, double aliShoSp) {
		shipFiringIntervall = fiInt;
		alienFiringIntervall = aliFiInt;
		speedUpFactor = spFac;
		shipSpeed = shiSp;
		alienSpeed = alSpe;
		shipShotSpeed = shiShoSp;
		alienShotSpeed = aliShoSp;
	}

}
