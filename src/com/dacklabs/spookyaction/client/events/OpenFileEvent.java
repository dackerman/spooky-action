package com.dacklabs.spookyaction.client.events;

import com.dacklabs.spookyaction.shared.File;
import com.google.web.bindery.event.shared.Event;

public class OpenFileEvent extends Event<OpenFileEventHandler> {

	public static final Type<OpenFileEventHandler> TYPE = new Type<OpenFileEventHandler>();

	private final File file;

	public OpenFileEvent(File file) {
		this.file = file;
	}

	@Override
	public Type<OpenFileEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(OpenFileEventHandler handler) {
		handler.onFileRecieved(file);
	}
}
