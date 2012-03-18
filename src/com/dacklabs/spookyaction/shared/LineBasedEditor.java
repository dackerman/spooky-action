package com.dacklabs.spookyaction.shared;

public interface LineBasedEditor {

	StringBuffer getLine(int lineNumber);

	void updateLine(int lineNumber, StringBuffer line);
}
