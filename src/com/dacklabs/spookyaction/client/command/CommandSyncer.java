package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.client.events.ErrorEvent;
import com.dacklabs.spookyaction.client.events.FileSavedEvent;
import com.dacklabs.spookyaction.client.events.SaveRequestedEvent;
import com.dacklabs.spookyaction.client.events.SaveRequestedEventHandler;
import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * Object that keeps track of what commands users have created, and syncs with the server when they
 * click "save".
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class CommandSyncer implements SaveRequestedEventHandler {

	private final CommandQueue queue;
	private final FileServiceAsync fileService;
	private final EventBus eventBus;

	@Inject
	public CommandSyncer(CommandQueue queue, FileServiceAsync fileService, EventBus eventBus) {
		this.queue = queue;
		this.fileService = fileService;
		this.eventBus = eventBus;

		eventBus.addHandler(SaveRequestedEvent.TYPE, this);
	}

	@Override
	public void onSaveRequested(SaveRequestedEvent event) {
		fileService.updateFile(event.getFile().getFilename(), queue.popQueuedCommands(), new FileUpdatedCallback());
	}

	private class FileUpdatedCallback implements AsyncCallback<UpdateResult> {

		@Override
		public void onFailure(Throwable caught) {
			eventBus.fireEvent(new ErrorEvent("Could not save file.", caught));
		}

		@Override
		public void onSuccess(UpdateResult result) {
			eventBus.fireEvent(new FileSavedEvent());
		}
	}
}
