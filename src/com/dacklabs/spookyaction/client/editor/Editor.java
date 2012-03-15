package com.dacklabs.spookyaction.client.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dacklabs.spookyaction.client.command.EditingSurface;
import com.dacklabs.spookyaction.client.events.ErrorEvent;
import com.dacklabs.spookyaction.client.events.InfoEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
public class Editor implements IsWidget, EditingSurface {

	private static final Logger logger = Logger.getLogger(Editor.class.getName());

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

		void addKeyPressHandler(KeyPressHandler handler);

		void addKeyUpHandler(KeyUpHandler handler);
	}

	private final Display display;
	private final FileServiceAsync fileService;

	private File currentFile;
	private final EventBus eventBus;

	private final List<String> lines = new ArrayList<String>();
	private final List<HasText> uiLines = new ArrayList<HasText>();
	private final int cursorLocation = 0;

	@Inject
	public Editor(Display display, EventBus eventBus, FileServiceAsync fileService) {
		logger.log(Level.SEVERE, "Editor created: " + this.toString());
		this.display = display;
		this.eventBus = eventBus;
		this.fileService = fileService;

		eventBus.addHandler(OpenFileEvent.TYPE, new NewFileHandler());
		display.setSaveHandler(new SaveHandler());
	}

	public void addKeyPressHandler(KeyPressHandler handler) {
		logger.log(Level.SEVERE, "Added keypress handler: " + handler.toString());
		display.addKeyPressHandler(handler);
	}

	public void addKeyUpHandler(KeyUpHandler handler) {
		logger.log(Level.SEVERE, "Added keyup handler: " + handler.toString());
		display.addKeyUpHandler(handler);
	}

	@Override
	public int getCursorLocation() {
		return cursorLocation;
	}

	private class NewFileHandler implements OpenFileEventHandler {

		@Override
		public void onFileRecieved(File file) {
			currentFile = file;
			setContent(file);
		}
	}

	private class SaveHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO(dackerman): Compress and send commands to the server.
		}
	}

	private class OnFileUpdated implements AsyncCallback<UpdateResult> {

		@Override
		public void onSuccess(UpdateResult result) {
			eventBus.fireEvent(new InfoEvent("Saved."));
		}

		@Override
		public void onFailure(Throwable caught) {
			eventBus.fireEvent(new ErrorEvent("Couldn't save: " + caught.getMessage()));
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
	public String getLine(int lineNumber) {
		return lines.get(lineNumber);
	}

	@Override
	public void updateLine(int lineNumber, String line) {
		lines.set(lineNumber, line);
		uiLines.get(lineNumber).setText(line);
	}
}
