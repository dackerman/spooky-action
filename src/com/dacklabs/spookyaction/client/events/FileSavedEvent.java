package com.dacklabs.spookyaction.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Fired when a file has been successfully saved to the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class FileSavedEvent extends GwtEvent<FileSavedEventHandler> {

	public static final Type<FileSavedEventHandler> TYPE = new Type<FileSavedEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<FileSavedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FileSavedEventHandler handler) {
		handler.onFileSaved(this);
	}
}
