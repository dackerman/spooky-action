package com.dacklabs.spookyaction.server;

import com.dacklabs.spookyaction.client.rpc.FileService;
import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class FileServiceImpl extends RemoteServiceServlet implements FileService {

	@Override
	public File fromPath(String path) {
		return new File("src/com/dacksoft/testfile.java", "public static void main(String[] args) {}");
	}
}
