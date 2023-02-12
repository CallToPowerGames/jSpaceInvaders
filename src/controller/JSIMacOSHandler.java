/**
 * jSpaceInvaders
 * Author: Denis Meyer (CallToPower)
 * Copyright 2010-2012 by Denis Meyer (CallToPower)
 */
package controller;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationEvent;
import com.apple.eawt.ApplicationListener;

/**
 * Handles Mac OS-specific Settings
 * 
 * @author Denis Meyer (CallToPower)
 */
@SuppressWarnings("deprecation")
public class JSIMacOSHandler extends Application {

	/**
	 * Mac OS X Settings Handler
	 * 
	 * @param gc
	 *            The Game Controller
	 */
	public JSIMacOSHandler(final JSIGameController gc) {
		addApplicationListener(new ApplicationListener() {
			public void handleAbout(ApplicationEvent event) {
				event.setHandled(true);
				gc.showAbout();
			}

			public void handleOpenApplication(ApplicationEvent event) {
				// Do nothing
			}

			public void handleOpenFile(ApplicationEvent event) {
				// Do nothing
			}

			public void handlePreferences(ApplicationEvent event) {
				// Do nothing
			}

			public void handlePrintFile(ApplicationEvent event) {
				// Do nothing
			}

			public void handleQuit(ApplicationEvent event) {
				event.setHandled(true);
				gc.quit(false);
			}

			public void handleReOpenApplication(ApplicationEvent event) {
				// Do nothing
			}
		});
		this.setEnabledPreferencesMenu(false);
	}
}
