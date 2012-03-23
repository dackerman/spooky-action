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
public class Editor implements IsWidget, EditingSurface, LineBasedEditor {

	@ImplementedBy(EditorView.class)
	public interface Display extends IsWidget {

		/**
		 * Creates a new line in the editor and returns it.
		 */
		void addLine(EditorLine line);

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
			EditorLine newLine = lineFactory.get();
			newLine.setText(line);
			newLine.setLineNumber(lineNumber++);
			newLine.addLineHandler(handler);
			display.addLine(newLine);
			uiLines.add(newLine);
		}
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

	public void setStyleName(String style) {
		display.setStyleName(style);
	}
}
