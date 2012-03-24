package com.dacklabs.spookyaction.client.testing;

import java.util.List;

import com.dacklabs.spookyaction.client.editor.EditorEventHandler;
import com.dacklabs.spookyaction.shared.EditingSurface;
import com.dacklabs.spookyaction.shared.LineBasedEditor;
import com.google.common.collect.Lists;

public class StubEditingSurface implements EditingSurface, LineBasedEditor {

	private final List<StringBuffer> content = Lists.newArrayList();

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

	@Override
	public void setEditorEventHandler(EditorEventHandler handler) {
	}

	@Override
	public void insertLine(int lineNumber, StringBuffer text) {
		content.add(lineNumber, text);
	}

	@Override
	public void removeLine(int lineNumber) {
		content.remove(lineNumber);
	}
}