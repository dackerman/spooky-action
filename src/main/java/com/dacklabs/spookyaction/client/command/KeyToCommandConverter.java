package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.client.editor.EditorEventHandler;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.dacklabs.spookyaction.shared.EditingSurface;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * Converts User keystrokes into {@link Command} objects for syncing with the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class KeyToCommandConverter implements EditorEventHandler {

	private final EventBus eventBus;
	private EditingSurface editingSurface;

	@Inject
	public KeyToCommandConverter(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void setEditor(EditingSurface editor) {
		editingSurface = editor;

		editingSurface.setEditorEventHandler(this);
	}

	@Override
	public void onClick(int lineNumber, int cursorPosition, ClickEvent event) {
	}

	@Override
	public void onKeyPress(int lineNumber, int cursorPosition, KeyPressEvent event) {
		char charCode = event.getCharCode();
		if (!typeableCharacter(charCode)) {
			return;
		}

		Command.Builder builder = Command.builder(CommandType.KEY);
		builder.repeatedTimes(1);
		builder.withOffset(cursorPosition);
		builder.onLine(lineNumber);
		builder.withData(String.valueOf(charCode));

		fireCommandEvent(event, builder);
	}

	private boolean typeableCharacter(char charCode) {
		// upper & lower case, numbers, special characters, and space.
		if (charCode >= ' ' && charCode <= '~') {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onKeyUp(int lineNumber, int cursorPosition, KeyUpEvent event) {
	}

	@Override
	public void onKeyDown(int lineNumber, int cursorPosition, KeyDownEvent event) {

		Command.Builder builder;
		switch (event.getNativeKeyCode()) {

		case KeyCodes.KEY_ENTER:
			builder = Command.builder(CommandType.NEWLINE);
			builder.withOffset(cursorPosition);
			builder.onLine(lineNumber);
			fireCommandEvent(event, builder);
			return;

		case KeyCodes.KEY_BACKSPACE:
			builder = Command.builder(CommandType.BACKSPACE);
			builder.withOffset(cursorPosition);
			builder.onLine(lineNumber);
			fireCommandEvent(event, builder);
			return;

		case KeyCodes.KEY_DOWN:
			if (event.isAltKeyDown()) {
				builder = Command.builder(CommandType.MOVE_LINE);
				builder.withOffset(0);
				builder.onLine(lineNumber);
				builder.repeatedTimes(1);
				fireCommandEvent(event, builder);
				return;
			}

		case KeyCodes.KEY_UP:
			if (event.isAltKeyDown()) {
				builder = Command.builder(CommandType.MOVE_LINE);
				builder.withOffset(0);
				builder.onLine(lineNumber);
				builder.repeatedTimes(-1);
				fireCommandEvent(event, builder);
				return;
			}
		}
	}

	/**
	 * Fires a command event and prevents any other events from propagating.
	 */
	private void fireCommandEvent(KeyEvent<?> event, Command.Builder builder) {
		event.stopPropagation();
		event.preventDefault();
		eventBus.fireEvent(new CommandEvent(builder.build()));
	}
}
