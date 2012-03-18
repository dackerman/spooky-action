package com.dacklabs.spookyaction.shared;

/**
 * Object that executes commands on an editing surface.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class CommandExecutor {

	public static void processCommand(LineBasedEditor editingSurface, Command command) {
		int lineNumber = command.getLineNumber();
		StringBuffer line = editingSurface.getLine(lineNumber);

		for (int i = 0; i < command.getRepeated(); i++) {

			switch (command.getType()) {
			case KEY:
				line.insert(command.getOffset(), command.getData());
				break;
			case BACKSPACE:
				line.deleteCharAt(command.getOffset());
				break;
			}
		}

		editingSurface.updateLine(lineNumber, line);
	}
}
