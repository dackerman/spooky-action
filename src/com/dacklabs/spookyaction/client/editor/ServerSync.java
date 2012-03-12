package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * Periodically syncs the commands that the user is creating with the backend.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class ServerSync implements OpenFileEventHandler {

	private static final int SYNC_TIMEOUT = 1000;

    private final SyncWithServer syncWithServer;
	private final Scheduler scheduler;

	private SyncWithServerTask currentSyncingTask = null;

	@Inject
    ServerSync(SyncWithServer syncWithServer, EventBus eventBus, Scheduler scheduler) {
		this.syncWithServer = syncWithServer;
		this.scheduler = scheduler;

		eventBus.addHandler(OpenFileEvent.TYPE, this);
	}

	@Override
	public void onFileRecieved(File file) {
		stopCurrentSyncTask();
		currentSyncingTask = new SyncWithServerTask(file, syncWithServer);
		scheduler.scheduleFixedDelay(currentSyncingTask, SYNC_TIMEOUT);
	}

	private void stopCurrentSyncTask() {
		if (currentSyncingTask != null) {
			currentSyncingTask.stopSyncing();
		}
	}

	private static class SyncWithServerTask implements RepeatingCommand {

        private final SyncWithServer syncWithServer;
		private final File currentFile;

		private boolean shouldSync = true;

        public SyncWithServerTask(File currentFile, SyncWithServer syncWithServer) {
			this.currentFile = currentFile;
			this.syncWithServer = syncWithServer;
		}

		@Override
		public boolean execute() {
			if (shouldSync) {
				syncWithServer.sync(currentFile);
			}
			return true;
		}

		public void stopSyncing() {
			this.shouldSync = false;
		}
	}
}
