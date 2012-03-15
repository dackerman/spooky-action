package com.dacklabs.spookyaction.client.command;

import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;

class StubEditingSurface implements EditingSurface {

	private String[] content = new String[] {};
	private int location;

	public void setContent(String... content) {
		this.content = content;
	}

	@Override
	public String getLine(int lineNumber) {
		return content[lineNumber];
	}

	@Override
	public void updateLine(int lineNumber, String line) {
		content[lineNumber] = line;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	@Override
	public int getCursorLocation() {
		return location;
	}

	@Override
	public void addKeyPressHandler(KeyPressHandler keyPressHandler) {
	}

	@Override
	public void addKeyUpHandler(KeyUpHandler keyUpHandler) {
	}
}