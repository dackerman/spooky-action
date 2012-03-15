package com.dacklabs.spookyaction.client.stats;

import com.dacklabs.spookyaction.client.command.CommandEvent;
import com.dacklabs.spookyaction.client.command.CommandEventHandler;
import com.dacklabs.spookyaction.shared.Command;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class StatsViewer implements IsWidget, CommandEventHandler {

	@ImplementedBy(StatsViewerView.class)
	public interface Display extends IsWidget {
		void setCommand(int offset, String type, String data, int repeated, int line);
	}

	private final Display display;

	@Inject
	public StatsViewer(Display display, EventBus eventBus) {
		this.display = display;

		eventBus.addHandler(CommandEvent.TYPE, this);
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}

	@Override
	public void onCommand(Command command) {
		display.setCommand(command.getOffset(), command.getType().toString(), command.getData(), command.getRepeated(),
		    command.getLineNumber());
	}
}
