package com.dacklabs.spookyaction.client.notifications;

import com.dacklabs.spookyaction.client.command.CommandEvent;
import com.dacklabs.spookyaction.client.command.CommandEventHandler;
import com.dacklabs.spookyaction.client.events.FileSavedEvent;
import com.dacklabs.spookyaction.client.events.FileSavedEventHandler;
import com.dacklabs.spookyaction.shared.Command;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class SyncStatus implements IsWidget, FileSavedEventHandler, CommandEventHandler {

	private final Display display;

	@ImplementedBy(SyncStatusView.class)
	public interface Display extends IsWidget {

		/**
		 * Set the text to display in the notification area.
		 */
		void setText(String text);

		/**
		 * Set the styling to a "healthy" color.
		 */
		void setHealthy();

		/**
		 * Set the styling to a "warning" color.
		 */
		void setWarning();

		/**
		 * Set the styling to a dangerous "unhealthy" color.
		 */
		void setUnhealthy();

	}

	private int numberOfCommandsBehind = 0;

	@Inject
	SyncStatus(Display display, EventBus eventBus) {
		this.display = display;

		eventBus.addHandler(FileSavedEvent.TYPE, this);
		eventBus.addHandler(CommandEvent.TYPE, this);
	}

	@Override
	public void onCommand(Command command) {
		numberOfCommandsBehind++;
		updateUi();
	}

	@Override
	public void onFileSaved(FileSavedEvent event) {
		numberOfCommandsBehind = 0;
		updateUi();
	}

	private void updateUi() {
		if (numberOfCommandsBehind == 0) {
			display.setText("Synced");
			display.setHealthy();
		} else if (numberOfCommandsBehind < 50) {
			display.setText(numberOfCommandsBehind + " commands not synced.");
			display.setWarning();
		} else {
			display.setText("Very behind! (" + numberOfCommandsBehind + " commands)");
			display.setUnhealthy();
		}
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
