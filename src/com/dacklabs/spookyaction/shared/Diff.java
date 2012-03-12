package com.dacklabs.spookyaction.shared;

import java.util.ArrayList;

/**
 * Represents a diff between the file on the client and file on the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class Diff {

    private final String filename;
    private final ArrayList<DiffLine> diff = new ArrayList<DiffLine>();

    public Diff(String filename) {
        this.filename = filename;
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
