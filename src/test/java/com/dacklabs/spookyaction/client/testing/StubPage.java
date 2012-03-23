package com.dacklabs.spookyaction.client.testing;

import com.dacklabs.spookyaction.client.main.Page;

public class StubPage implements Page {

	public String windowTitle;

	@Override
	public void setWindowTitle(String title) {
		this.windowTitle = title;
	}
}
