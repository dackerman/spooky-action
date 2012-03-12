package com.dacklabs.spookyaction.client.rpc;

import java.util.ArrayList;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FileServiceAsync {

	void fromPath(String path, AsyncCallback<File> callback);

	void updateFile(File file, ArrayList<Command> commands, AsyncCallback<UpdateResult> callback);
}
