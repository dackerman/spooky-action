package com.dacklabs.spookyaction.client.fileselector;

import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class FileSelector implements IsWidget {

	@ImplementedBy(FileSelectorView.class)
	public interface Display extends IsWidget {

		String currentPathText();

		void onFileRequested(ClickHandler clickHandler);

		void showLoadingMessage();

		void hideLoadingMessage();
	}

	private final Display display;
	private final EventBus eventBus;
	private final FileServiceAsync fileService;

	@Inject
	public FileSelector(Display display, EventBus eventBus, FileServiceAsync fileService) {
		this.display = display;
		this.eventBus = eventBus;
		this.fileService = fileService;

		display.onFileRequested(new OnFileRequested());
	}

	private class OnFileRequested implements ClickHandler {

		@Override
		public void onClick(ClickEvent unused) {
			display.showLoadingMessage();
			fileService.fromPath(display.currentPathText(), new OnFileRecieved());
		}
	}

	private class OnFileRecieved implements AsyncCallback<File> {

		@Override
		public void onSuccess(File file) {
			display.hideLoadingMessage();
			eventBus.fireEvent(new OpenFileEvent(file));
		}

		@Override
		public void onFailure(Throwable caught) {
			display.hideLoadingMessage();
		}
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}