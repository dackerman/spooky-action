package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.event.shared.EventBus;
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

		void setEditorContent(String content);
	}

	private final Display display;

	@Inject
	public Editor(Display display, EventBus eventBus) {
		this.display = display;

		eventBus.addHandler(OpenFileEvent.TYPE, new NewFileHandler());
	}

	private class NewFileHandler implements OpenFileEventHandler {

		@Override
		public void onFileRecieved(File file) {
			display.setEditorContent(file.getContent());
		}
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
