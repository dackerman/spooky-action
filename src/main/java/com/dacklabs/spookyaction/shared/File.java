package com.dacklabs.spookyaction.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class File implements IsSerializable {

	private String filename;
	private String content;

	@SuppressWarnings("unused")
	private File() {
	}

	public File(String filename, String content) {
		this.filename = filename;
		this.content = content;
	}

	public String getFilename() {
		return filename;
	}

	public String getContent() {
		return content;
	}
}
