package com.dacklabs.spookyaction.client.editor;

import java.util.List;

import com.dacklabs.spookyaction.client.editor.Editor.Display;
import com.google.common.collect.Lists;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class StubDisplay implements Display {

	public ClickHandler clickHandler;
	public KeyPressHandler keyPressHandler;
	public KeyUpHandler keyUpHandler;

	private final List<StubLine> lines = Lists.newArrayList();

	@Override
	public HasText newLine() {
		StubLine line = new StubLine("");
		lines.add(line);
		return line;
	}

	@Override
	public void clearWindow() {
		lines.clear();
	}

	@Override
	public void setSaveHandler(ClickHandler handler) {
		this.clickHandler = handler;
	}

	@Override
	public void addKeyPressHandler(KeyPressHandler handler) {
		this.keyPressHandler = handler;
	}

	@Override
	public void addKeyUpHandler(KeyUpHandler handler) {
		this.keyUpHandler = handler;
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

	public static class StubLine implements HasText {

		private String text;

		public StubLine(String text) {
			setText(text);
		}

		@Override
		public String getText() {
			return text;
		}

		@Override
		public void setText(String text) {
			this.text = text;
		}
	}

	@Override
	public Widget asWidget() {
		return null;
	}
}
