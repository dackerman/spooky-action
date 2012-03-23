package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.shared.Command;

/**
 * Handler of user events from the editor. This is an uncompressed stream, so up to one command per
 * keystroke may be generated.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public interface CommandEventHandler {

	/**
	 * Invoked when the user generates a command.
	 */
	void onCommand(Command command);
}
