package com.dacklabs.spookyaction.client.main;

import com.dacklabs.spookyaction.client.command.CommandSyncer;
import com.dacklabs.spookyaction.client.command.KeyToCommandConverter;
import com.dacklabs.spookyaction.client.command.UiUpdater;
import com.dacklabs.spookyaction.client.editor.Editor;
import com.dacklabs.spookyaction.client.stats.StatsViewer;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class SpookyAction implements IsWidget {

	private final Display display;
	private final KeyToCommandConverter converter;
	private final UiUpdater uiUpdater;
	private final CommandSyncer syncer;

	@ImplementedBy(SpookyActionView.class)
	public interface Display extends IsWidget {
		StatsViewer getStats();

		Editor getEditor();
	}

	@Inject
	public SpookyAction(Display display, KeyToCommandConverter converter, UiUpdater uiUpdater, CommandSyncer syncer) {
		this.display = display;
		this.converter = converter;
		this.uiUpdater = uiUpdater;
		this.syncer = syncer;
	}

	public void start() {
		converter.setEditor(display.getEditor());
		uiUpdater.setEditor(display.getEditor());
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
