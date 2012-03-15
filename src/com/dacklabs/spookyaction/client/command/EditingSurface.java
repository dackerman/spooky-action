package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.client.editor.Editor;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.inject.ImplementedBy;

/**
 * Represents the editing surface for the UI.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
@ImplementedBy(Editor.class)
public interface EditingSurface {

	/**
	 * Gets the line of text at the given line number.
	 */
	String getLine(int lineNumber);

	/**
	 * Overwrites the text at the line number with the given data.
	 */
	void updateLine(int lineNumber, String line);

	/**
	 * Returns the cursor's current location.
	 */
	int getCursorLocation();

	void addKeyPressHandler(KeyPressHandler keyPressHandler);

	void addKeyUpHandler(KeyUpHandler keyUpHandler);
}
