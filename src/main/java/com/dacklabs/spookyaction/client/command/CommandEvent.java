package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.shared.Command;
import com.google.web.bindery.event.shared.Event;

/**
 * Represents a fired Command. Is sent to {@link CommandEventHandler}s.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class CommandEvent extends Event<CommandEventHandler> {

	public static final Type<CommandEventHandler> TYPE = new Type<CommandEventHandler>();

	private final Command command;

	public CommandEvent(Command command) {
		this.command = command;
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<CommandEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CommandEventHandler handler) {
		handler.onCommand(command);
	}
}
