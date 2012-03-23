package com.dacklabs.spookyaction.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface FileSavedEventHandler extends EventHandler {

	void onFileSaved(FileSavedEvent event);
}
