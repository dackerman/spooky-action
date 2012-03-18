package com.dacklabs.spookyaction.client.events;

import com.google.web.bindery.event.shared.Event;

public class InfoEvent extends Event<InfoEventHandler> {

	public static final Type<InfoEventHandler> TYPE = new Type<InfoEventHandler>();

	private final String message;

	public InfoEvent(String message) {
		this.message = message;
	}

	public InfoEvent() {
		this.message = null;
	}

	public String message() {
		return message;
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<InfoEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(InfoEventHandler handler) {
		handler.onInfo(this);
	}
}
