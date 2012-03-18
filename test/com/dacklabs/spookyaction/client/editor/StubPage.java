package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.client.main.Page;

public class StubPage implements Page {

	public String windowTitle;

	@Override
	public void setWindowTitle(String title) {
		this.windowTitle = title;
	}
}
