package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.shared.Command;

/**
 * Consumer of {@link Command}s.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public interface CommandConsumer {

	/**
	 * Fires when a command is detected.
	 */
	void commandDetected(Command command);
}
