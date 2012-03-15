package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.shared.Command;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * A listener of {@link CommandEvent}s that updates the editor UI to reflect the changes that will
 * be made on the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class UiUpdater implements CommandEventHandler {

	private EditingSurface editingSurface;

	@Inject
	public UiUpdater(EventBus eventBus) {
		eventBus.addHandler(CommandEvent.TYPE, this);
	}

	public void setEditor(EditingSurface editor) {
		this.editingSurface = editor;
	}

	@Override
	public void onCommand(Command command) {
		int lineNumber = command.getLineNumber();
		StringBuffer line = new StringBuffer(editingSurface.getLine(lineNumber));

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

		editingSurface.updateLine(lineNumber, line.toString());
	}
}
