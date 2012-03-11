package com.dacklabs.spookyaction.client.rpc;

import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FileServiceAsync {

	void fromPath(String path, AsyncCallback<File> callback);
}
