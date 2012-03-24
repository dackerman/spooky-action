package com.dacklabs.spookyaction.server;

import java.util.ArrayList;
import java.util.Iterator;

import com.dacklabs.spookyaction.shared.LineBasedEditor;

public class Lines implements Iterable<StringBuffer>, LineBasedEditor {
	private final ArrayList<StringBuffer> lines;

	public Lines(ArrayList<StringBuffer> lines) {
		this.lines = lines;
	}

	@Override
	public Iterator<StringBuffer> iterator() {
		return lines.iterator();
	}

	@Override
	public StringBuffer getLine(int lineNumber) {
		return lines.get(lineNumber);
	}

	@Override
	public void updateLine(int lineNumber, StringBuffer line) {
		lines.set(lineNumber, line);
	}

	public StringBuffer asSingleBuffer() {
		StringBuffer out = new StringBuffer(1024);
		for (StringBuffer line : lines) {
			out.append(line).append('\n');
		}
		return out;
	}

	@Override
	public void insertLine(int lineNumber, StringBuffer text) {
		lines.add(lineNumber, text);
	}

	@Override
	public void removeLine(int lineNumber) {
		lines.remove(lineNumber);
	}
}
