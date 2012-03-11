package com.dacklabs.spookyaction.shared;

public class FileUnavailableException extends Exception {
	private static final long serialVersionUID = -6066645044799741061L;

	public FileUnavailableException() {
		super();
	}

	public FileUnavailableException(String reason) {
		super(reason);
	}
}
