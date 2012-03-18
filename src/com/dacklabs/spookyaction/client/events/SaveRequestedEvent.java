package com.dacklabs.spookyaction.client.events;

import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.event.shared.GwtEvent;

public class SaveRequestedEvent extends GwtEvent<SaveRequestedEventHandler> {

	public static Type<SaveRequestedEventHandler> TYPE = new Type<SaveRequestedEventHandler>();

	private final File file;

	public SaveRequestedEvent(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	@Override
	public GwtEvent.Type<SaveRequestedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveRequestedEventHandler handler) {
		handler.onSaveRequested(this);
	}
}
