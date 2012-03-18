package com.dacklabs.spookyaction.shared;

/**
 * Represents an editor that can be read and updated on a per-line basis.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public interface LineBasedEditor {

	/**
	 * Get a single line in the form of a {@link StringBuffer}.
	 */
	StringBuffer getLine(int lineNumber);

	/**
	 * Update a given line with a {@link StringBuffer}.
	 */
	void updateLine(int lineNumber, StringBuffer line);
}
