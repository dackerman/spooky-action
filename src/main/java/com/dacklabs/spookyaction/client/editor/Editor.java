package com.dacklabs.spookyaction.client.editor;

import java.util.ArrayList;
import java.util.List;

import com.dacklabs.spookyaction.client.events.FileLoadingEvent;
import com.dacklabs.spookyaction.client.events.FileLoadingEventHandler;
import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.client.main.Page;
import com.dacklabs.spookyaction.shared.EditingSurface;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.LineBasedEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The main editor window. Displays the file in a text area and then listens for events on it to
 * send back changes to the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class Editor implements IsWidget, EditingSurface, LineBasedEditor, EditorEventHandler {

	@ImplementedBy(EditorView.class)
	public interface Display extends IsWidget {

		/**
		 * Creates a new line in the editor and returns it.
		 */
		void addLine(int lineNumber, EditorLine newLine);

		/**
		 * Removes a EditorLine at the given line number.
		 */
		void removeLine(int lineNumber);

		/**
		 * Clears all lines from the editor.
		 */
		void clearWindow();

		/**
		 * Show that a file is loading in the editor window.
		 */
		void showLoading(String path);

		/**
		 * Sets the style of this widget.
		 */
		void setStyleName(String style);
	}

	private final Display display;

	private final List<EditorLine> uiLines = new ArrayList<EditorLine>();
	private final Provider<EditorLine> lineFactory;

	private final Page page;

	private EditorEventHandler handler;

	@Inject
	public Editor(Display display, EventBus eventBus, Page page, Provider<EditorLine> lineFactory) {
		this.display = display;
		this.page = page;
		this.lineFactory = lineFactory;

		eventBus.addHandler(FileLoadingEvent.TYPE, new LoadingScreenHandler());
		eventBus.addHandler(OpenFileEvent.TYPE, new NewFileHandler());
	}

	@Override
	public void setEditorEventHandler(EditorEventHandler handler) {
		this.handler = handler;
	}

	private class NewFileHandler implements OpenFileEventHandler {

		@Override
		public void onFileRecieved(File file) {
			setContent(file);
			page.setWindowTitle(file.getFilename() + " (spooky action)");
		}
	}

	private class LoadingScreenHandler implements FileLoadingEventHandler {

		@Override
		public void onFileLoading(FileLoadingEvent event) {
			display.showLoading(event.getPath());
		}
	}

	private void setContent(File file) {
		unregisterHandlers();
		uiLines.clear();
		display.clearWindow();

		String[] fileLines = file.getContent().split("\n");
		int lineNumber = 0;
		for (String line : fileLines) {
			addLine(lineNumber++, line);
		}
	}

	private void addLine(int lineNumber, String line) {
		EditorLine newLine = lineFactory.get();
		newLine.setText(line);
		newLine.setLineNumber(lineNumber);
		newLine.addLineHandler(handler);
		newLine.addLineHandler(this);
		display.addLine(lineNumber, newLine);
		uiLines.add(lineNumber, newLine);
	}

	private void deleteLine(int lineNumber) {
		display.removeLine(lineNumber);
		uiLines.remove(lineNumber);
	}

	private void unregisterHandlers() {
		for (EditorLine line : uiLines) {
			line.unregisterHandlers();
		}
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}

	@Override
	public StringBuffer getLine(int lineNumber) {
		return new StringBuffer(uiLines.get(lineNumber).getText());
	}

	@Override
	public void updateLine(int lineNumber, StringBuffer line) {
		String lineString = line.toString();
		uiLines.get(lineNumber).setText(lineString);
	}

	@Override
	public void insertLine(int lineNumber, StringBuffer text) {
		addLine(lineNumber, text.toString());
		resetLineNumbersAfter(lineNumber);
	}

	@Override
	public void removeLine(int lineNumber) {
		deleteLine(lineNumber);
		resetLineNumbersAfter(lineNumber);
	}

	/**
	 * Resets line numbers if we insert a line.
	 */
	private void resetLineNumbersAfter(int lineNumber) {
		for (int i = lineNumber; i < uiLines.size(); i++) {
			uiLines.get(i).setLineNumber(i);
		}
	}

	public void setStyleName(String style) {
		display.setStyleName(style);
	}

	@Override
	public void onKeyPress(int lineNumber, int cursorPosition, KeyPressEvent event) {
	}

	@Override
	public void onKeyUp(int lineNumber, int cursorPosition, KeyUpEvent event) {
	}

	@Override
	public void onKeyDown(int lineNumber, int cursorPosition, KeyDownEvent event) {
		int currentLineLength = uiLines.get(lineNumber).getText().length();
		// Wrapping from beginning of line to previous line
		if (event.isLeftArrow() && cursorPosition <= 0 && lineNumber > 0) {
			uiLines.get(lineNumber - 1).setCursor(-1);
		}
		// Wrapping from end of line to next line
		else if (event.isRightArrow() && cursorPosition >= currentLineLength && lineNumber < uiLines.size() - 1) {
			uiLines.get(lineNumber + 1).setCursor(0);
		}
		// Going up lines on same column
		else if (event.isUpArrow() && lineNumber > 0) {
			uiLines.get(lineNumber - 1).setCursor(cursorPosition);
			event.stopPropagation();
			event.preventDefault();
		}
		// Going down lines on same column
		else if (event.isDownArrow() && lineNumber < uiLines.size() - 1) {
			uiLines.get(lineNumber + 1).setCursor(cursorPosition);
			event.stopPropagation();
			event.preventDefault();
		}
	}

	@Override
	public void onClick(int lineNumber, int cursorPosition, ClickEvent event) {
	}
}
