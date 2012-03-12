package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.shared.Command;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * Widget that displays the last command that was issued to the editor window.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class CommandStatus implements IsWidget, CommandConsumer {

	@ImplementedBy(CommandStatusView.class)
	public interface Display extends IsWidget {

		void setCurrentCommand(String text);

		void clearQueueView();

		void addToQueueList(String command);
	}

	private final Display display;
	private final CommandQueue queue;

	@Inject
	public CommandStatus(Display display, CommandQueue queue, CommandGenerator generator) {
		this.display = display;
		this.queue = queue;
		generator.addCommandConsumer(this);
	}

	@Override
	public void commandDetected(Command command) {
		display.setCurrentCommand(renderCommand(command));
		display.clearQueueView();
		for (Command c : queue.viewQueuedCommands()) {
			display.addToQueueList(renderCommand(c));
		}
	}

	private String renderCommand(Command command) {
		String text = "Command: " + command.getType().toString();
		text += " keyCode: " + command.getKeyCode();
		text += " offset: " + command.getOffset();
		return text;
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
