/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package model;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import data.JSIExternalStrings;

/**
 * HighscoreTableData
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIHighscoreTableData {
	// The Lists to save the Highscore Entries
	private ArrayList<JSIHighscoreEntry> list;

	private JFrame mainFrame;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param mainFrame
	 */
	public JSIHighscoreTableData(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		list = new ArrayList<JSIHighscoreEntry>();
	}

	/*********** Functions ***********/

	/**
	 * Clears the HighscoreTable
	 */
	public void clear() {
		list.clear();
		list = new ArrayList<JSIHighscoreEntry>();
	}

	/**
	 * Inserts a HighscoreEntry if not already inserted, asks for the Name
	 * 
	 * @param mode
	 * @param score
	 * @param survived
	 *            If Player has been dead or alive at the End of the Game
	 */
	public void insert(double score, String mode, boolean survived, int lives) {
		JTextField textField = new JTextField();
		String diedOrSurvived = survived ? (JSIExternalStrings.survived + " ("
				+ lives + ")") : JSIExternalStrings.died;
		if (JOptionPane.showConfirmDialog(mainFrame, textField,
				JSIExternalStrings.enterYourName, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			String name = textField.getText();

			insert(new JSIHighscoreEntry(checkName(name), mode, diedOrSurvived,
					score));
		}
	}

	/**
	 * Inserts a HighscoreEntry if not already inserted
	 * 
	 * @param he
	 *            HighscoreEntry
	 */
	private void insert(JSIHighscoreEntry he) {
		if (!list.contains(he)) {
			list.add(he);
			checkForSize();
		}
	}

	/**
	 * Loads the HighscoreTable
	 * 
	 * @param entries
	 * @throws ClassCastException
	 */
	public void setHighscoreTable(ArrayList<String> entries)
			throws ClassCastException {
		clear();
		for (String str : entries) {
			String[] split = str.split("#");
			if ((split.length == 4) && !split[0].isEmpty()
					&& !split[1].isEmpty() && !split[2].isEmpty()
					&& !split[3].isEmpty()) {
				double d = Double.valueOf(split[3]);
				insert(new JSIHighscoreEntry(split[0], split[1], split[2], d));
			}
		}
	}

	/**
	 * Checks the Name
	 * 
	 * @param name
	 * @return checked Name
	 */
	private String checkName(String name) {
		if (name.isEmpty()) {
			return JSIExternalStrings.unknownName;
		}
		if (name.contains("#")) {
			name = name.replaceAll("#", "-");
		}
		return name;
	}

	/**
	 * If Size is too big -> delete old Records
	 */
	private void checkForSize() {
		Collections.sort(list);
		if (list.size() > 20) {
			for (int i = 20; i < list.size(); i++) {
				list.remove(i);
			}
		}
	}

	/*********** Boolean ***********/

	/**
	 * Returns if the List is empty
	 * 
	 * @return true if the List is empty, false else
	 */
	public boolean listIsEmpty() {
		return list.isEmpty();
	}

	/*********** Getter ***********/

	/**
	 * Returns the List
	 * 
	 * @return the List, empty or not
	 */
	public ArrayList<JSIHighscoreEntry> getSortedEntryList() {
		Collections.sort(list);
		return list;
	}

	/**
	 * Returns the List with Entries as String
	 * 
	 * @return the List, empty or not
	 */
	public ArrayList<String> getSortedEntryStringList() {
		ArrayList<String> listString = new ArrayList<String>(list.size());
		for (JSIHighscoreEntry he : list) {
			listString.add(he.getName() + "#" + he.getMode() + "#"
					+ he.getDeadOrAlive() + "#" + he.getScore());
		}

		return listString;
	}

	/**
	 * Returns the Data
	 * 
	 * @return the Data if list not empty, null else
	 */
	public String[][] getData() {
		if (!list.isEmpty()) {
			Collections.sort(list);
			String[][] data = new String[list.size()][5];

			for (int i = 0; i < list.size(); i++) {
				data[i][0] = String.valueOf(i + 1);
				data[i][1] = list.get(i).getName();
				data[i][2] = list.get(i).getMode();
				data[i][3] = list.get(i).getDeadOrAlive();
				data[i][4] = String.valueOf(list.get(i).getScore());
			}

			return data;
		}
		return null;
	}
}
