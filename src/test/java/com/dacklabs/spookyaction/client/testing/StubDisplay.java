package com.dacklabs.spookyaction.client.testing;

import java.util.List;

import com.dacklabs.spookyaction.client.editor.Editor.Display;
import com.dacklabs.spookyaction.client.editor.EditorLine;
import com.google.common.collect.Lists;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Widget;

public class StubDisplay implements Display {

	public KeyPressHandler keyPressHandler;
	public KeyUpHandler keyUpHandler;

	private final List<EditorLine> lines = Lists.newArrayList();

	@Override
	public void addLine(EditorLine line) {
		lines.add(line);
	}

	@Override
	public void clearWindow() {
		lines.clear();
	}

	public String line(int lineNumber) {
		if (lineNumber > lines.size()) {
			return null;
		}
		return lines.get(lineNumber).getText();
	}

	public int lines() {
		return lines.size();
	}

	@Override
	public Widget asWidget() {
		return null;
	}

	@Override
	public void showLoading(String path) {
	}

	@Override
	public void setStyleName(String style) {
	}
}
