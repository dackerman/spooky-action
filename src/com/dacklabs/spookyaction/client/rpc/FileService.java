package com.dacklabs.spookyaction.client.rpc;

import java.util.ArrayList;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.FileUnavailableException;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("file")
public interface FileService extends RemoteService {

	File fromPath(String path) throws FileUnavailableException;

	UpdateResult updateFile(File file, ArrayList<Command> commands) throws FileUnavailableException;
}
