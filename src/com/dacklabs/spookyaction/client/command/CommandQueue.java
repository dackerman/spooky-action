package com.dacklabs.spookyaction.client.command;

import java.util.ArrayList;

import com.dacklabs.spookyaction.shared.Command;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Queues up commands the user executes and then syncs them with the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
@Singleton
public class CommandQueue implements CommandEventHandler {

	private ArrayList<Command> queuedCommands = new ArrayList<Command>();

	@Inject
	CommandQueue(EventBus eventBus) {
		eventBus.addHandler(CommandEvent.TYPE, this);
	}

	@Override
	public void onCommand(Command command) {
		queuedCommands.add(command);
	}

	public Iterable<Command> viewQueuedCommands() {
		return queuedCommands;
	}

	public ArrayList<Command> popQueuedCommands() {
		ArrayList<Command> copy = queuedCommands;
		queuedCommands = new ArrayList<Command>();
		return copy;
	}
}