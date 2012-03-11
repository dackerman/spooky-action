package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class Editor implements IsWidget {

	@ImplementedBy(EditorView.class)
	public interface Display extends IsWidget {

		void replaceContent(String content);
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
			display.replaceContent(file.getContent());
		}
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
