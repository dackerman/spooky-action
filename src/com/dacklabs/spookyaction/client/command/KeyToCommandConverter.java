package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.client.editor.HasCursor;
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
	private final HasCursor hasCursor;

	@Inject
	public KeyToCommandConverter(HasCursor hasCursor, EventBus eventBus) {
		this.hasCursor = hasCursor;
		this.eventBus = eventBus;
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		char charCode = event.getCharCode();

		Command.Builder builder = Command.builder();
		builder.ofType(CommandType.KEY);
		builder.repeatedTimes(1);
		builder.withOffset(hasCursor.getCursorLocation());
		builder.withData(String.valueOf(charCode));

		eventBus.fireEvent(new CommandEvent(builder.build()));
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		Command.Builder builder = Command.builder();
		builder.withOffset(hasCursor.getCursorLocation());

		switch (event.getNativeKeyCode()) {

		case KeyCodes.KEY_BACKSPACE:
			builder.ofType(CommandType.BACKSPACE);
			break;

		default:
			return;
		}

		eventBus.fireEvent(new CommandEvent(builder.build()));
	}
}
