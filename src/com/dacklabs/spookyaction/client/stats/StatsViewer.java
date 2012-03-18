package com.dacklabs.spookyaction.client.stats;

import java.util.Iterator;

import com.dacklabs.spookyaction.client.command.CommandEvent;
import com.dacklabs.spookyaction.client.command.CommandEventHandler;
import com.dacklabs.spookyaction.client.command.CommandQueue;
import com.dacklabs.spookyaction.shared.Command;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class StatsViewer implements IsWidget, CommandEventHandler {

	@ImplementedBy(StatsViewerView.class)
	public interface Display extends IsWidget {

		void setNumberOfCommandsInQueue(int numberOfCommands);

		void setCommand(int offset, String type, String data, int repeated, int line);
	}

	private final Display display;
	private final CommandQueue queue;

	@Inject
	public StatsViewer(Display display, EventBus eventBus, CommandQueue queue) {
		this.display = display;
		this.queue = queue;

		eventBus.addHandler(CommandEvent.TYPE, this);
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}

	@Override
	public void onCommand(Command command) {
		display.setNumberOfCommandsInQueue(size(queue.viewQueuedCommands()));
		display.setCommand(command.getOffset(), command.getType().toString(), command.getData(), command.getRepeated(),
		    command.getLineNumber());
	}

	private <T> int size(Iterable<T> iterable) {
		int i = 0;
		Iterator<T> iter = iterable.iterator();
		while (iter.hasNext()) {
			iter.next();
			i++;
		}
		return i;
	}
}
