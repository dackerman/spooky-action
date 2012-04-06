package com.dacklabs.spookyaction.client.command;

import java.util.ArrayList;

import com.dacklabs.spookyaction.shared.Command;

public class CompressedCommands {

	private final ArrayList<Command> commands = new ArrayList<Command>();

	public void add(Command command) {
		if (commands.isEmpty() || !last().merge(command)) {
			commands.add(command);
		}
	}

	public void push(Command command) {
		commands.add(0, command);
	}

	private Command last() {
		return commands.get(commands.size() - 1);
	}

	public ArrayList<Command> compressedCommands() {
		return commands;
	}

	public boolean isEmpty() {
		return commands.isEmpty();
	}

	public int size() {
		return commands.size();
	}
}
