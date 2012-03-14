package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.client.events.ErrorEvent;
import com.dacklabs.spookyaction.client.events.InfoEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The main editor window. Displays the file in a text area and then listens for events on it to
 * send back changes to the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
@Singleton
public class Editor implements IsWidget, HasCursor {

	@ImplementedBy(EditorView.class)
	public interface Display extends IsWidget {

		String getEditorContent();

		void setEditorContent(String content);

		void setSaveHandler(ClickHandler handler);

		int getCursorPosition();
	}

	private final Display display;
	private final FileServiceAsync fileService;

	private File currentFile;
	private final EventBus eventBus;

	@Inject
	public Editor(Display display, EventBus eventBus, FileServiceAsync fileService) {
		this.display = display;
		this.eventBus = eventBus;
		this.fileService = fileService;

		eventBus.addHandler(OpenFileEvent.TYPE, new NewFileHandler());
		display.setSaveHandler(new SaveHandler());
	}

	@Override
	public int getCursorLocation() {
		return display.getCursorPosition();
	}

	private class NewFileHandler implements OpenFileEventHandler {

		@Override
		public void onFileRecieved(File file) {
			currentFile = file;
			display.setEditorContent(file.getContent());
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

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
