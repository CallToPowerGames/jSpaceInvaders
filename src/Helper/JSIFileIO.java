/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package Helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

//import data.JSIDataStorage;

/**
 * FileIO - File Input and Output
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIFileIO {
	// private static JSIDataStorage ds = JSIDataStorage.getDataStorage();
	public static JSIFileIO fio = new JSIFileIO();

	/*********** Constructor ***********/

	/**
	 * Constructor
	 */
	private JSIFileIO() {

	}

	/*********** Functions ***********/

	/**
	 * Returns the current Path + /
	 * 
	 * @return the current Path
	 */
	public String getCurrentPath() {
		try {
			return (new File(".").getCanonicalPath() + "/");
		} catch (IOException e) {
			return "./";
		}
	}

	/**
	 * Creates a new File if not already existing
	 * 
	 * @param path
	 * @param name
	 * @param invisible
	 * @return new File if not existing or File
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public File createFile(String path, String name, boolean invisible)
			throws IOException, InterruptedException {
		String newName = name;
		if (invisible) {
			if (!name.startsWith(".")) {
				newName = "." + name;
			}
		}
		return createFile(new File(path + newName), invisible);
	}

	/**
	 * Creates a new File if not already existing
	 * 
	 * @param file
	 * @param invisibleUnderWindows
	 * @return created File
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public File createFile(File file, boolean invisibleUnderWindows)
			throws IOException, InterruptedException {
		if (!file.exists()) {
			file.createNewFile();
			if (invisibleUnderWindows) {
				setInvisibleUnderWindows(file);
			}
		}
		return file;
	}

	/**
	 * Writes to a binary File
	 * 
	 * @param file
	 * @param toWrite
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeBinaryFile(File file, byte[] toWrite)
			throws FileNotFoundException, IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
		dos.write(toWrite);
		dos.close();
	}

	/**
	 * Deletes file if exists
	 * 
	 * @param file
	 */
	public void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * Writes to a File
	 * 
	 * @param file
	 * @param stringList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeToFilePerLine(File file, ArrayList<String> stringList)
			throws FileNotFoundException, IOException {
		if (file.exists()) {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			for (String str : stringList) {
				writer.write(str);
				writer.newLine();
			}
			writer.close();
		}
	}

	/*********** Getter ***********/

	/**
	 * Returns a FileIO-Object
	 * 
	 * @return FileIO
	 */
	public static JSIFileIO getFileIO() {
		return fio;
	}

	/**
	 * Returns a Files Content per Line
	 * 
	 * @param file
	 * @return Files Content per Line
	 * @returna Files Content per Line
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ArrayList<String> getFromFilePerLine(File file)
			throws FileNotFoundException, IOException {
		ArrayList<String> stringList = new ArrayList<String>();
		if (file.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String str;
			while ((str = reader.readLine()) != null) {
				stringList.add(str);
			}
			reader.close();
		}
		return stringList;
	}

	/**
	 * Returns the fully Content of a binary File via readFully if file exists
	 * 
	 * @param file
	 * @return the fully Content of a binary File via readFully if file exists,
	 *         null else
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public byte[] getFromBinaryFile(File file) throws FileNotFoundException,
			IOException {
		byte[] byteArr = new byte[(int) file.length()];
		if (file.exists()) {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(byteArr);
			dis.close();
		}
		return byteArr;
	}

	/*********** Setter ***********/

	/**
	 * Sets a File under Windows insisible
	 * 
	 * TODO NOT CORRECTLY IMPLEMENTED YET!!!
	 * 
	 * @param file
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void setInvisibleUnderWindows(File file) throws IOException,
			InterruptedException {
		// if (ds.is("Windows") && file.exists() && !file.isHidden()) {
		// Process p;
		// p = Runtime.getRuntime().exec("attrib +H " + file.getPath());
		// p.waitFor();
		// }
	}

}
