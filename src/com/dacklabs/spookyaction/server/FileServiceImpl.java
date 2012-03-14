package com.dacklabs.spookyaction.server;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.dacklabs.spookyaction.client.rpc.FileService;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.FileUnavailableException;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class FileServiceImpl extends RemoteServiceServlet implements FileService {

	@Override
	public File fromPath(String path) throws FileUnavailableException {
		return new File(path, getFileContents(path).toString());
	}

	@Override
	public UpdateResult updateFile(File file, ArrayList<Command> commands) throws FileUnavailableException {
		return null;
	}

	private StringBuffer getFileContents(String path) throws FileUnavailableException {
		Scanner s = null;
		try {
			s = getScannerFromFile(path);
			StringBuffer buffer = new StringBuffer(1024);
			while (s.hasNextLine()) {
				buffer.append(s.nextLine());
			}
			return buffer;
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	private Scanner getScannerFromFile(String path) throws FileUnavailableException {
		java.io.File f = new java.io.File(path);
		try {
			return new Scanner(f);
		} catch (FileNotFoundException e) {
			throw new FileUnavailableException("Couldn't find file.");
		}
	}
}
