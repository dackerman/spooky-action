package com.dacklabs.spookyaction.client.editor;

import java.util.ArrayList;
import java.util.List;

import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.client.events.SaveRequestedEvent;
import com.dacklabs.spookyaction.client.main.Page;
import com.dacklabs.spookyaction.shared.EditingSurface;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.LineBasedEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * The main editor window. Displays the file in a text area and then listens for events on it to
 * send back changes to the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class Editor implements IsWidget, EditingSurface, LineBasedEditor {

	@ImplementedBy(EditorView.class)
	public interface Display extends IsWidget {

		/**
		 * Creates a new line in the editor and returns it.
		 */
		HasText newLine();

		/**
		 * Clears all lines from the editor.
		 */
		void clearWindow();

		/**
		 * Sets the callback for when the user clicks "save".
		 */
		void setSaveHandler(ClickHandler handler);

		/**
		 * Adds a handler for user keypresses.
		 */
		void addKeyPressHandler(KeyPressHandler handler);

		/**
		 * Adds a handler for non-character key events.
		 */
		void addKeyUpHandler(KeyUpHandler handler);
	}

	private final Display display;

	private File currentFile;
	private final EventBus eventBus;

	private final List<String> lines = new ArrayList<String>();
	private final List<HasText> uiLines = new ArrayList<HasText>();
	private int cursorLocation = 0;

	private final Page page;

	@Inject
	public Editor(Display display, EventBus eventBus, Page page) {
		this.display = display;
		this.eventBus = eventBus;
		this.page = page;

		eventBus.addHandler(OpenFileEvent.TYPE, new NewFileHandler());
		display.setSaveHandler(new SaveHandler());
	}

	@Override
	public void addKeyPressHandler(KeyPressHandler handler) {
		display.addKeyPressHandler(handler);
	}

	@Override
	public void addKeyUpHandler(KeyUpHandler handler) {
		display.addKeyUpHandler(handler);
	}

	@Override
	public int getCursorLocation() {
		return cursorLocation;
	}

	@Override
	public void setCursorLocation(int cursorLocation) {
		this.cursorLocation = cursorLocation;
	}

	private class NewFileHandler implements OpenFileEventHandler {

		@Override
		public void onFileRecieved(File file) {
			currentFile = file;
			setContent(file);
			page.setWindowTitle(file.getFilename() + " (spooky action)");
		}
	}

	private class SaveHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			eventBus.fireEvent(new SaveRequestedEvent(currentFile));
		}
	}

	private void setContent(File file) {
		lines.clear();
		uiLines.clear();
		display.clearWindow();

		String[] fileLines = file.getContent().split("\n");
		for (String line : fileLines) {
			HasText newLine = display.newLine();
			newLine.setText(line);
			lines.add(line);
			uiLines.add(newLine);
		}
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}

	@Override
	public StringBuffer getLine(int lineNumber) {
		return new StringBuffer(lines.get(lineNumber));
	}

	@Override
	public void updateLine(int lineNumber, StringBuffer line) {
		String lineString = line.toString();
		lines.set(lineNumber, lineString);
		uiLines.get(lineNumber).setText(lineString);
	}
}
