/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;

/**
 * HyperlinkLabel
 * 
 * @author Denis Meyer (CallToPower)
 */
@SuppressWarnings("serial")
public class JSIHyperlinkLabel extends JLabel {
	private URI uri;

	/*********** Constructors ***********/

	/**
	 * Default Constructor
	 * 
	 * @param label
	 * @param uri
	 * @throws URISyntaxException
	 */
	public JSIHyperlinkLabel(String label, String uri)
			throws URISyntaxException {
		this(label, new URI(uri));
	}

	/**
	 * Constructor
	 * 
	 * @param label
	 * @param uri
	 */
	public JSIHyperlinkLabel(String label, URI uri) {
		super(label);
		super.setToolTipText(uri.toString());
		setForeground(Color.BLUE);
		addMouseListener(linker);
		this.uri = uri;
	}

	/*********** Functions ***********/

	/**
	 * MouseListener
	 */
	private static MouseListener linker = new MouseAdapter() {

		/**
		 * mouseClicked
		 * 
		 * @param e
		 *            MouseEvent
		 */
		public void mouseClicked(MouseEvent e) {
			JSIHyperlinkLabel self = (JSIHyperlinkLabel) e.getSource();
			if (self.uri == null)
				return;
			try {
				Desktop.getDesktop().browse(new URI(self.uri.toString()));
			} catch (IOException ioe) {
				// Do nothing
			} catch (URISyntaxException urise) {
				// Do nothing
			}
		}

		/**
		 * mouseEntered
		 * 
		 * @param e
		 *            MouseEvent
		 */
		public void mouseEntered(MouseEvent e) {
			e.getComponent().setCursor(
					Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	};

	/*********** Getter ***********/

	/**
	 * Returns the URI
	 * 
	 * @return the URI
	 */
	public URI getURI() {
		return uri;
	}

	/*********** Setter ***********/

	/**
	 * Sets the URI
	 * 
	 * @param uri
	 */
	public void setURI(URI uri) {
		this.uri = uri;
	}
}
