package com.dacklabs.spookyaction.shared;

import com.dacklabs.spookyaction.client.editor.Editor;
import com.dacklabs.spookyaction.client.editor.EditorEventHandler;
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
	StringBuffer getLine(int lineNumber);

	/**
	 * Overwrites the text at the line number with the given data.
	 */
	void updateLine(int lineNumber, StringBuffer line);

	/**
	 * Sets the event handlers for this editor.
	 */
	void setEditorEventHandler(EditorEventHandler handler);
}
