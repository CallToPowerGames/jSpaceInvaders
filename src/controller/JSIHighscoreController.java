/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Helper.JSIEnDeCrypt;
import Helper.JSIFileIO;

import view.JSIHighscoreTable;

import model.JSIHighscoreTableData;

import data.JSIExternalStrings;

/**
 * Handles the Files for the Highscore
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIHighscoreController {
	private static JSIFileIO fio = JSIFileIO.getFileIO();

	private JSIEnDeCrypt crypt;
	private JSIHighscoreTableData htd;
	private JSIHighscoreTable ht;

	// The Key and Database Files
	private File fileKey;
	private File fileDatabase;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param gc
	 *            JSIGameController
	 */
	public JSIHighscoreController(JSIGameController gc) {
		try {
			crypt = new JSIEnDeCrypt();
			htd = new JSIHighscoreTableData(gc.getMainFrame());

			fileKey = fio.createFile(fio.getCurrentPath(),
					JSIExternalStrings.highscoreDatabaseKey, true);
			fileDatabase = fio.createFile(fio.getCurrentPath(),
					JSIExternalStrings.highscoreDatabase, true);

			// If the File with the Key doesn't exist
			if (!fileKeyExists()) {
				createNewFiles();
				// If the File with the Key exists but the File with the
				// Database doesn't
			} else {
				loadKeyFromKeyFile();
				createNewDatabaseFileIfNotExists();
			}

			// Load Entries
			htd.setHighscoreTable(getEntryList());
			if (!htd.listIsEmpty()) {
				ht = new JSIHighscoreTable(gc, htd.getData());
			} else {
				ht = new JSIHighscoreTable(gc, new String[0][]);
			}
		} catch (FileNotFoundException e) {
			// Do nothing
		} catch (IOException e1) {
			// Do nothing
		} catch (NoSuchAlgorithmException e) {
			// Do nothing
		} catch (InterruptedException e) {
			// Do nothing
		}
	}

	/*********** Functions ***********/

	/**
	 * Creates the new Files
	 */
	private void createNewFiles() {
		writeNewKeyFile();
		createNewDatabaseFile();

		// Set the Files invisible under Windows
		try {
			fio.setInvisibleUnderWindows(fileKey);
			fio.setInvisibleUnderWindows(fileDatabase);
		} catch (IOException e) {
			// Do nothing
		} catch (InterruptedException e) {
			// Do nothing
		}
	}

	/**
	 * Saves the Entries to the DatabaseFile
	 */
	public void save() {
		try {
			// Load decrypted Strings
			ArrayList<String> decryptedList = htd.getSortedEntryStringList();

			// Set and store a new Key
			crypt.generateKey();
			writeNewKeyFile();

			// Encrypt all Strings (with the new Key)
			ArrayList<String> encryptedStringList = new ArrayList<String>(
					decryptedList.size());
			for (String str : decryptedList) {
				encryptedStringList.add(crypt.encrypt(str));
			}
			// Shuffle Elements before writing to File
			Collections.shuffle(encryptedStringList);
			// Write the encrypted Strings to the Database File
			fio.writeToFilePerLine(fileDatabase, encryptedStringList);
		} catch (NoSuchAlgorithmException e) {
			// Do nothing
		} catch (InvalidKeyException e) {
			// Do nothing
		} catch (IllegalBlockSizeException e) {
			// Do nothing
		} catch (BadPaddingException e) {
			// Do nothing
		} catch (NoSuchPaddingException e) {
			// Do nothing
		} catch (UnsupportedEncodingException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		}
	}

	/**
	 * Returns if the Key-File exists
	 * 
	 * @return true if the Key-File exists, false else
	 */
	public boolean fileKeyExists() {
		return fileKey.exists();
	}

	/**
	 * Returns if the Database-File exists
	 * 
	 * @return true if the Database-File exists, false else
	 */
	public boolean fileDatabaseExists() {
		return fileDatabase.exists();
	}

	/**
	 * Loads a Key from the KeyFile
	 */
	private void loadKeyFromKeyFile() {
		try {
			byte[] byteArr = fio.getFromBinaryFile(fileKey);
			if (!(byteArr.length == 0) && !(byteArr == null)) {
				crypt.setKey(byteArr);
			} else {
				createNewFiles();
			}
		} catch (FileNotFoundException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns the EntryList
	 * 
	 * @return EntryList
	 */
	private ArrayList<String> getEntryList() {
		ArrayList<String> ret = new ArrayList<String>();
		try {
			// Put the decrypted Strings from the Database File into the
			// StringList
			ArrayList<String> arrList = fio.getFromFilePerLine(fileDatabase);
			for (String str : arrList) {
				ret.add(crypt.decrypt(str));
			}
		} catch (FileNotFoundException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		} catch (InvalidKeyException e) {
			createNewFiles();
		} catch (NoSuchAlgorithmException e) {
			// Do nothing
		} catch (NoSuchPaddingException e) {
			createNewFiles();
		} catch (IllegalBlockSizeException e) {
			createNewFiles();
		} catch (BadPaddingException e) {
			createNewFiles();
		}
		return ret;
	}

	/**
	 * Returns the HighscoreTable
	 * 
	 * @return the HighscoreTable
	 */
	public JSIHighscoreTable getHighscoreTable() {
		setNewHighscoreTableData();
		return ht;
	}

	/**
	 * Returns the HighscoreTableData
	 * 
	 * @return the HighscoreTableData
	 */
	public JSIHighscoreTableData getHighscoreTableData() {
		return htd;
	}

	/*********** Setter ***********/

	/**
	 * Saves the new Key to the KeyFile
	 */
	private void writeNewKeyFile() {
		try {
			fio.writeBinaryFile(fileKey, crypt.getEncodedKey());
		} catch (FileNotFoundException e) {
			// Do nothing
		} catch (IOException e) {
			// Do nothing
		}
	}

	/**
	 * Creates a new Databse File, deletes the old one if exists
	 */
	private void createNewDatabaseFile() {
		if (fileDatabase.exists()) {
			fio.deleteFile(fileDatabase);
		}
		try {
			fileDatabase = fio.createFile(fileDatabase, true);
		} catch (IOException e) {
			// Do nothing
		} catch (InterruptedException e) {
			// Do nothing
		}
	}

	/**
	 * Creates a new Databse File if not exists
	 */
	private void createNewDatabaseFileIfNotExists() {
		if (!fileDatabase.exists()) {
			try {
				fileDatabase = fio.createFile(fileDatabase, true);
			} catch (IOException e) {
				// Do nothing
			} catch (InterruptedException e) {
				// Do nothing
			}
		}
	}

	/**
	 * Sets a new Highscore Table
	 */
	private void setNewHighscoreTableData() {
		if (!htd.listIsEmpty()) {
			ht.setNewData(htd.getData());
		} else {
			ht.setNewData(new String[0][]);
		}
	}
}
