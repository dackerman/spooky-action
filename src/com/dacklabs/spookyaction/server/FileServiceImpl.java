package com.dacklabs.spookyaction.server;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.dacklabs.spookyaction.client.rpc.FileService;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.FileUnavailableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class FileServiceImpl extends RemoteServiceServlet implements FileService {

	@Override
	public File fromPath(String path) throws FileUnavailableException {
		Scanner s = null;
		try {
			s = getFile(path);
			StringBuffer buffer = new StringBuffer(1024);
			while (s.hasNextLine()) {
				buffer.append(s.nextLine());
			}
			return new File(path, buffer.toString());
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	private Scanner getFile(String path) throws FileUnavailableException {
		try {
			java.io.File f = new java.io.File(path);
			return new Scanner(f);
		} catch (FileNotFoundException e) {
			throw new FileUnavailableException("Couldn't find file.");
		}
	}
}
