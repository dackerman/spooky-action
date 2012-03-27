package com.dacklabs.spookyaction.client.testing;

import java.util.ArrayList;
import java.util.Iterator;

import com.dacklabs.spookyaction.client.editor.EditorEventHandler;
import com.dacklabs.spookyaction.server.Lines;
import com.dacklabs.spookyaction.shared.EditingSurface;
import com.dacklabs.spookyaction.shared.LineBasedEditor;
import com.google.common.collect.Lists;

public class StubEditingSurface implements EditingSurface, LineBasedEditor {

	private Lines lines;

	public void setContent(String... content) {
		ArrayList<StringBuffer> sb = Lists.newArrayList();
		for (String line : content) {
			sb.add(new StringBuffer(line));
		}
		lines = new Lines(sb);
	}

	@Override
	public StringBuffer getLine(int lineNumber) {
		return lines.getLine(lineNumber);
	}

	@Override
	public void updateLine(int lineNumber, StringBuffer line) {
		lines.updateLine(lineNumber, line);
	}

	@Override
	public void setEditorEventHandler(EditorEventHandler handler) {
	}

	@Override
	public void insertLine(int lineNumber, StringBuffer text) {
		lines.insertLine(lineNumber, text);
	}

	@Override
	public void removeLine(int lineNumber) {
		lines.removeLine(lineNumber);
	}

	@Override
	public void swapLines(int starting, int ending) {
		lines.swapLines(starting, ending);
	}

	public String[] linesAsArray() {
		Iterator<StringBuffer> iter = lines.iterator();
		ArrayList<String> tmp = Lists.newArrayList();
		while (iter.hasNext()) {
			tmp.add(iter.next().toString());
		}
		return tmp.toArray(new String[] {});
	}
}