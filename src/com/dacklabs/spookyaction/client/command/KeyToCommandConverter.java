package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * Converts User keystrokes into {@link Command} objects for syncing with the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class KeyToCommandConverter implements KeyPressHandler, KeyUpHandler {

	private final EventBus eventBus;
	private EditingSurface editingSurface;

	@Inject
	public KeyToCommandConverter(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void setEditor(EditingSurface editor) {
		editingSurface = editor;

		editingSurface.addKeyPressHandler(this);
		editingSurface.addKeyUpHandler(this);
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		int cursorPosition = editingSurface.getCursorLocation();
		char charCode = event.getCharCode();

		Command.Builder builder = Command.builder();
		builder.ofType(CommandType.KEY);
		builder.repeatedTimes(1);
		builder.withOffset(cursorPosition);
		builder.withData(String.valueOf(charCode));

		cursorPosition++;
		editingSurface.setCursorLocation(cursorPosition);

		eventBus.fireEvent(new CommandEvent(builder.build()));
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		Command.Builder builder = Command.builder();

		switch (event.getNativeKeyCode()) {

		case KeyCodes.KEY_BACKSPACE:
			int cursorPosition = editingSurface.getCursorLocation() - 1;
			if (cursorPosition < 0) {
				return;
			}
			builder.withOffset(cursorPosition);
			builder.ofType(CommandType.BACKSPACE);
			editingSurface.setCursorLocation(cursorPosition);
			break;

		default:
			return;
		}

		eventBus.fireEvent(new CommandEvent(builder.build()));
	}
}
