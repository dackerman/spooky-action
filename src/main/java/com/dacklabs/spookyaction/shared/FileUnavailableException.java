package com.dacklabs.spookyaction.shared;

/**
 * Exception thrown when the file being retrieved couldn't be found.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class FileUnavailableException extends Exception {
	private static final long serialVersionUID = -6066645044799741061L;

	public FileUnavailableException() {
		super();
	}

	public FileUnavailableException(String reason) {
		super(reason);
	}
}
