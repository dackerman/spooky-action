package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;

/**
 * Represents a handler for any type of event the editor produces.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public interface EditorEventHandler {

	void onKeyPress(int lineNumber, int cursorPosition, KeyPressEvent event);

	void onKeyUp(int lineNumber, int cursorPosition, KeyUpEvent event);

	void onKeyDown(int lineNumber, int cursorPosition, KeyDownEvent event);

	void onClick(int lineNumber, int cursorPosition, ClickEvent event);
}
