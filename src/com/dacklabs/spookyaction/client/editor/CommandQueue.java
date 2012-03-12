package com.dacklabs.spookyaction.client.editor;

import java.util.ArrayList;

import com.dacklabs.spookyaction.shared.Command;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Queues up commands the user executes and then syncs them with the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
@Singleton
public class CommandQueue implements CommandConsumer {

	private ArrayList<Command> queuedCommands = new ArrayList<Command>();

	@Inject
	CommandQueue(CommandGenerator generator) {
		generator.addCommandConsumer(this);
	}

	@Override
	public void commandDetected(Command command) {
		queuedCommands.add(command);
	}

	public Iterable<Command> viewQueuedCommands() {
		return queuedCommands;
	}

	public Iterable<Command> popQueuedCommands() {
		ArrayList<Command> copy = queuedCommands;
		queuedCommands = new ArrayList<Command>();
		return copy;
	}
}