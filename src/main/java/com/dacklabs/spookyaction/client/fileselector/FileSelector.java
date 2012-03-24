package com.dacklabs.spookyaction.client.fileselector;

import com.dacklabs.spookyaction.client.events.ErrorEvent;
import com.dacklabs.spookyaction.client.events.FileLoadingEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class FileSelector implements IsWidget {

	@ImplementedBy(FileSelectorView.class)
	public interface Display extends IsWidget {

		void focus();

		String currentPathText();

		void onFileRequested(ClickHandler clickHandler);

		void onEnterPressed(KeyDownHandler keyDownHandler);
	}

	private final Display display;
	private final EventBus eventBus;
	private final FileServiceAsync fileService;

	private final OnFileRequested fileRequestedHandler = new OnFileRequested();
	private final OnEnterPressed enterPressedHandler = new OnEnterPressed();

	@Inject
	public FileSelector(Display display, EventBus eventBus, FileServiceAsync fileService) {
		this.display = display;
		this.eventBus = eventBus;
		this.fileService = fileService;

		display.onFileRequested(fileRequestedHandler);
		display.onEnterPressed(enterPressedHandler);
	}

	private class OnFileRequested implements ClickHandler {

		@Override
		public void onClick(ClickEvent unused) {
			eventBus.fireEvent(new FileLoadingEvent(display.currentPathText()));
			fileService.fromPath(display.currentPathText(), new OnFileRecieved());
		}
	}

	private class OnEnterPressed implements KeyDownHandler {

		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				fileRequestedHandler.onClick(null);
			}
		}
	}

	private class OnFileRecieved implements AsyncCallback<File> {

		@Override
		public void onSuccess(File file) {
			eventBus.fireEvent(new OpenFileEvent(file));
		}

		@Override
		public void onFailure(Throwable caught) {
			eventBus.fireEvent(new ErrorEvent("Woah there, couldn't load your file.", caught));
		}
	}

	public void focus() {
		display.focus();
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
