package com.dacklabs.spookyaction.client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for save events. Should sync the pending commands from the editor to the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public interface SaveRequestedEventHandler extends EventHandler {

	/**
	 * Fires when the user clicks the save button on the {@link Editor}.
	 */
	public void onSaveRequested(SaveRequestedEvent event);
}
