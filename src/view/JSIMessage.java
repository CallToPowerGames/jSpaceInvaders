/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Message
 * 
 * @author Denis Meyer (CallToPower)
 */
public class JSIMessage {
	private JFrame mainFrame;

	private String message;
	private String title;

	private int messageType;

	/*********** Constructor ***********/

	/**
	 * Constructor
	 * 
	 * @param mainFrame
	 *            The parent Frame
	 * @param message
	 *            The Message
	 * @param title
	 *            The Title
	 */
	public JSIMessage(JFrame mainFrame, String message, String title) {
		this.message = message;
		this.mainFrame = mainFrame;
		this.title = title;
		setInformationType();
	}

	/*********** Functions ***********/

	/**
	 * Shows the Message
	 */
	public void show() {
		JOptionPane.showMessageDialog(mainFrame, message, title, messageType);
	}

	/*********** Setter ***********/

	/**
	 * Sets the Message Type to ERROR_MESSAGE
	 */
	public void setErrorType() {
		messageType = JOptionPane.ERROR_MESSAGE;
	}

	/**
	 * Sets the Message Type to INFORMATION_MESSAGE
	 */
	public void setInformationType() {
		messageType = JOptionPane.INFORMATION_MESSAGE;
	}

	/**
	 * Sets the Message Type to QUESTION_MESSAGE
	 */
	public void setQuestionType() {
		messageType = JOptionPane.QUESTION_MESSAGE;
	}

	/**
	 * Sets the Message Type to WARNING_MESSAGE
	 */
	public void setWarningType() {
		messageType = JOptionPane.WARNING_MESSAGE;
	}
}
