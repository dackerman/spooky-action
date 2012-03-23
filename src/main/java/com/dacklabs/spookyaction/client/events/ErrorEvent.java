package com.dacklabs.spookyaction.client.events;

import com.google.web.bindery.event.shared.Event;

public class ErrorEvent extends Event<ErrorEventHandler> {

	public static final Type<ErrorEventHandler> TYPE = new Type<ErrorEventHandler>();

	private final String errorMessage;

	private final Throwable throwable;

	public ErrorEvent(String errorMessage, Throwable throwable) {
		this.errorMessage = errorMessage;
		this.throwable = throwable;
	}

	public ErrorEvent(String errorMessage) {
		this.errorMessage = errorMessage;
		this.throwable = null;
	}

	public ErrorEvent() {
		this.errorMessage = null;
		this.throwable = null;
	}

	public boolean hasMessage() {
		return message() != null;
	}

	public String message() {
		return errorMessage;
	}

	public boolean hasThrowable() {
		return throwable() != null;
	}

	public Throwable throwable() {
		return throwable;
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<ErrorEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ErrorEventHandler handler) {
		handler.onError(this);
	}
}
