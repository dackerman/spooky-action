package com.dacklabs.spookyaction.server;

import com.dacklabs.spookyaction.shared.Command;

public class CommandProcessorFactory {

	private static final CommandProcessor NULL = new NullProcessor();
	private static final CommandProcessor BACKSPACE = new BackspaceCommandProcessor();

	public static CommandProcessor fromCommand(Command c) {
		switch (c.getKeyCode()) {
		case 8:
			return BACKSPACE;
		}
		return NULL;
	}
}
