/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

/**
 * HighscoreEntry
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIHighscoreEntry implements Comparable<JSIHighscoreEntry> {
	private String name;
	private String mode;
	private String deadOrAlive;
	private double score;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param mode
	 * @param deadOrAlive
	 * @param score
	 */
	public JSIHighscoreEntry(String name, String mode, String deadOrAlive,
			double score) {
		this.name = name;
		this.mode = mode;
		this.deadOrAlive = deadOrAlive;
		this.score = score;
	}

	/*********** Functions ***********/

	/**
	 * Compares this to another HighscoreEntry
	 * 
	 * @param he
	 *            HighscoreEntry
	 * @return 0 if same Score, +x if this Score is smaller, -x if this Score is
	 *         higher
	 */
	public int compareTo(JSIHighscoreEntry he) {
		return (int) (he.getScore() - score);
	}

	/*********** Getter ***********/

	/**
	 * Returns the Name
	 * 
	 * @return the Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the Mode
	 * 
	 * @return the Mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Returns deadOrAlive
	 * 
	 * @return deadOrAlive
	 */
	public String getDeadOrAlive() {
		return deadOrAlive;
	}

	/**
	 * Returns the Score
	 * 
	 * @return the Score
	 */
	public double getScore() {
		return score;
	}
}
