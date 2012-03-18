package com.dacklabs.spookyaction.client.command;

import java.util.List;

import com.dacklabs.spookyaction.shared.EditingSurface;
import com.dacklabs.spookyaction.shared.LineBasedEditor;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

class StubEditingSurface implements EditingSurface, LineBasedEditor {

	private final List<StringBuffer> content = Lists.newArrayList();
	private int location;

	public void setContent(String... content) {
		for (String line : content) {
			this.content.add(new StringBuffer(line));
		}
	}

	@Override
	public StringBuffer getLine(int lineNumber) {
		return content.get(lineNumber);
	}

	@Override
	public void updateLine(int lineNumber, StringBuffer line) {
		content.set(lineNumber, line);
	}

	public void setLocation(int location) {
		this.location = location;
	}

	@Override
	public int getCursorLocation() {
		return location;
	}

	@Override
	public void setCursorLocation(int cursorLocation) {
		location = cursorLocation;
	}

	@Override
	public void addKeyPressHandler(KeyPressHandler keyPressHandler) {
	}

	@Override
	public void addKeyUpHandler(KeyUpHandler keyUpHandler) {
	}
}