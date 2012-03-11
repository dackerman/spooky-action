package com.dacklabs.spookyaction.client.rpc;

import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("file")
public interface FileService extends RemoteService {
	File fromPath(String path);
}
