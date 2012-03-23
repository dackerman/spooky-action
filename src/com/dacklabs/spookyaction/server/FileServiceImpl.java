package com.dacklabs.spookyaction.server;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.dacklabs.spookyaction.client.rpc.FileService;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.CommandExecutor;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.FileUnavailableException;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class FileServiceImpl extends RemoteServiceServlet implements FileService {

	@Override
	public File fromPath(String path) throws FileUnavailableException {
		return new File(path, getFileContents(path).toString());
	}

	@Override
	public UpdateResult updateFile(String path, ArrayList<Command> commands) throws FileUnavailableException {
		UpdateResult.Builder resultBuilder = new UpdateResult.Builder();

		Lines lines = getLines(path);
		for (Command command : commands) {
			CommandExecutor.processCommand(lines, command);
		}
		writeBackToFile(path, lines);
		return resultBuilder.build();
	}

	private void writeBackToFile(String path, Lines lines) throws FileUnavailableException {
		java.io.File f = new java.io.File(path);
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(f, false));
			int offset = 0;
			StringBuffer linesToWrite = lines.asSingleBuffer();
			byte[] bytes = linesToWrite.toString().getBytes();
			bos.write(bytes, offset, bytes.length);
			offset += bytes.length;
			System.out.println(String.format("Wrote %d bytes to file.", bytes.length));
		} catch (IOException e) {
			throw new FileUnavailableException("File couldn't be written to: " + e.getMessage());
		} finally {
			if (bos != null) {
				try {
					bos.flush();
					bos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private StringBuffer getFileContents(String path) throws FileUnavailableException {
		return concatLines(getLines(path));
	}

	private StringBuffer concatLines(Iterable<StringBuffer> lines) throws FileUnavailableException {
		StringBuffer out = new StringBuffer(1024);
		for (StringBuffer line : lines) {
			out.append(line).append('\n');
		}
		return out;
	}

	private Lines getLines(String path) throws FileUnavailableException {
		Scanner s = null;
		try {
			s = getScannerFromFile(path);
			ArrayList<StringBuffer> lines = Lists.newArrayList();
			while (s.hasNextLine()) {
				lines.add(new StringBuffer(s.nextLine()));
			}
			return new Lines(lines);
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
