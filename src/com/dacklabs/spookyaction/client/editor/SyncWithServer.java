package com.dacklabs.spookyaction.client.editor;

import java.util.ArrayList;

import com.dacklabs.spookyaction.client.events.ErrorEvent;
import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class SyncWithServer {

	private final CommandQueue queue;
	private final FileServiceAsync fileService;
	private final EventBus eventBus;

	@Inject
	SyncWithServer(CommandQueue queue, FileServiceAsync fileService, EventBus eventBus) {
		this.queue = queue;
		this.fileService = fileService;
		this.eventBus = eventBus;
	}

	void sync(File file) {
		ArrayList<Command> commandsToSync = (ArrayList<Command>) queue.popQueuedCommands();
		fileService.updateFile(file, commandsToSync, new UpdatedCallback());
	}

	private class UpdatedCallback implements AsyncCallback<UpdateResult> {

		@Override
		public void onSuccess(UpdateResult result) {
		}

		@Override
		public void onFailure(Throwable caught) {
			eventBus.fireEvent(new ErrorEvent(caught.getMessage()));
		}
	}
}
