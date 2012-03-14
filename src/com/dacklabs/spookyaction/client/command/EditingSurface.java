package com.dacklabs.spookyaction.client.command;

/**
 * Represents the editing surface for the UI.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public interface EditingSurface {

	/**
	 * Gets the line of text at the given line number.
	 */
	String getLine(int lineNumber);

	/**
	 * Overwrites the text at the line number with the given data.
	 */
	void updateLine(int lineNumber, String line);
}
