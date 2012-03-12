package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.shared.File;

public interface SyncWithServer {
	
	/**
	 * Sync the given file
	 */
	void sync(File file);
}
