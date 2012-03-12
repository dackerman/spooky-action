package com.dacklabs.spookyaction.client.events;

import com.google.web.bindery.event.shared.Event;

public class InfoEvent extends Event<InfoEventHandler> {

    public static final Type<InfoEventHandler> TYPE = new Type<InfoEventHandler>();

	private final String errorMessage;

	public InfoEvent(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public InfoEvent() {
		this.errorMessage = null;
	}

	@Override
    public com.google.web.bindery.event.shared.Event.Type<InfoEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
    protected void dispatch(InfoEventHandler handler) {
		handler.onInfo(errorMessage);
	}
}
