package com.dacklabs.spookyaction.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface FileLoadingEventHandler extends EventHandler {

	void onFileLoading(FileLoadingEvent event);
}
