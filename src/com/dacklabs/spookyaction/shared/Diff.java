package com.dacklabs.spookyaction.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Represents a diff between the file on the client and file on the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class Diff implements IsSerializable {

	private String filename;
	private ArrayList<DiffLine> diff;

	@SuppressWarnings("unused")
	private Diff() {
	}

	public Diff(String filename) {
		this.filename = filename;
		this.diff = new ArrayList<DiffLine>();
	}

	public String getFilename() {
		return filename;
	}

	public Iterable<DiffLine> getDiffLines() {
		return diff;
	}

	public void addToDiff(DiffLine diffLine) {
		diff.add(diffLine);
	}

	public static class DiffLine {
		private final int line;
		private final String content;

		public DiffLine(int line, String content) {
			this.line = line;
			this.content = content;
		}

		public int getLine() {
			return line;
		}

		public String getContent() {
			return content;
		}
	}
}
