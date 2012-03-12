package com.dacklabs.spookyaction.server;

import com.dacklabs.spookyaction.shared.Command;

/**
 * An object that can process a single command.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public interface CommandProcessor {

	/**
	 * Process one command, and return whether the command was successfully processed (i.e. if the
	 * command doesn't make sense for the data given, this method should return false).
	 * 
	 * @param command The command to process
	 * @param data The data to manipulate
	 * @return whether the command was successfully completed.
	 */
	boolean process(Command command, StringBuffer data);
}
