package com.dacklabs.spookyaction.client.events;

import com.google.web.bindery.event.shared.Event;

public class ErrorEvent extends Event<ErrorEventHandler> {

	public static final Type<ErrorEventHandler> TYPE = new Type<ErrorEventHandler>();

	private final String errorMessage;

	public ErrorEvent(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorEvent() {
		this.errorMessage = null;
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<ErrorEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ErrorEventHandler handler) {
		handler.onError(errorMessage);
	}
}
