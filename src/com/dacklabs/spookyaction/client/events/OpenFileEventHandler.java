package com.dacklabs.spookyaction.client.events;

import com.dacklabs.spookyaction.shared.File;

public interface OpenFileEventHandler {

	void onFileRecieved(File file);
}
