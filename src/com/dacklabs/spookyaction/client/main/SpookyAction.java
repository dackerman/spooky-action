package com.dacklabs.spookyaction.client.main;

import com.dacklabs.spookyaction.client.command.CommandEvent;
import com.dacklabs.spookyaction.client.command.CommandEventHandler;
import com.dacklabs.spookyaction.client.command.CommandSyncer;
import com.dacklabs.spookyaction.client.command.KeyToCommandConverter;
import com.dacklabs.spookyaction.client.editor.Editor;
import com.dacklabs.spookyaction.client.stats.StatsViewer;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.CommandExecutor;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class SpookyAction implements IsWidget, CommandEventHandler {

	private final Display display;
	private final KeyToCommandConverter converter;
	private final CommandSyncer syncer;

	@ImplementedBy(SpookyActionView.class)
	public interface Display extends IsWidget {
		StatsViewer getStats();

		Editor getEditor();
	}

	@Inject
	public SpookyAction(Display display, EventBus eventBus, KeyToCommandConverter converter, CommandSyncer syncer) {
		this.display = display;
		this.converter = converter;
		this.syncer = syncer;

		eventBus.addHandler(CommandEvent.TYPE, this);
	}

	public void start() {
		converter.setEditor(display.getEditor());
	}

	@Override
	public void onCommand(Command command) {
		CommandExecutor.processCommand(display.getEditor(), command);
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
