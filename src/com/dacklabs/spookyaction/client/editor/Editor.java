package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.client.diff.DiffAlgorithm;
import com.dacklabs.spookyaction.client.events.ErrorEvent;
import com.dacklabs.spookyaction.client.events.InfoEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.Diff;
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

/**
 * The main editor window. Displays the file in a text area and then listens for events on it to
 * send back changes to the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class Editor implements IsWidget {

	@ImplementedBy(EditorView.class)
	public interface Display extends IsWidget {

        String getEditorContent();

		void setEditorContent(String content);

        void setSaveHandler(ClickHandler handler);
	}

	private final Display display;
    private final FileServiceAsync fileService;
    private final DiffAlgorithm diffAlgorithm;
	
	private File currentFile;
    private final EventBus eventBus;

	@Inject
    public Editor(Display display, EventBus eventBus, FileServiceAsync fileService, DiffAlgorithm diffAlgorithm) {
		this.display = display;
        this.eventBus = eventBus;
        this.fileService = fileService;
        this.diffAlgorithm = diffAlgorithm;

		eventBus.addHandler(OpenFileEvent.TYPE, new NewFileHandler());
        display.setSaveHandler(new SaveHandler());
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
            Diff diff = diffAlgorithm.calculateDiff(currentFile.getContent(), display.getEditorContent());
            fileService.updateFileWithDiff(diff, new OnFileUpdated());
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
