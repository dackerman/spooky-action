package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.CommandExecutor;
import com.dacklabs.spookyaction.shared.LineBasedEditor;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * A listener of {@link CommandEvent}s that updates the editor UI to reflect the changes that will
 * be made on the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class UiUpdater implements CommandEventHandler {

	private LineBasedEditor editor;

	@Inject
	public UiUpdater(EventBus eventBus) {
		eventBus.addHandler(CommandEvent.TYPE, this);
	}

	public void setEditor(LineBasedEditor editor) {
		this.editor = editor;
	}

	@Override
	public void onCommand(Command command) {
		CommandExecutor.processCommand(editor, command);
	}
}
