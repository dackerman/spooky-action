package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;

public class KeyToCommandConverter implements KeyPressHandler {

	private final SimpleEventBus eventBus;

	public KeyToCommandConverter(SimpleEventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		char charCode = event.getCharCode();

		Command.Builder builder = Command.builder();
		builder.ofType(CommandType.KEY);
		builder.repeatedTimes(1);
		builder.withOffset(0);
		builder.withData(String.valueOf(charCode));

		eventBus.fireEvent(new CommandEvent(builder.build()));
	}
}
