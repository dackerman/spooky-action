package com.dacklabs.spookyaction.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class FileLoadingEvent extends GwtEvent<FileLoadingEventHandler> {

	public static final Type<FileLoadingEventHandler> TYPE = new Type<FileLoadingEventHandler>();

	private final String path;

	public FileLoadingEvent(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	@Override
	public Type<FileLoadingEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FileLoadingEventHandler handler) {
		handler.onFileLoading(this);
	}
}
