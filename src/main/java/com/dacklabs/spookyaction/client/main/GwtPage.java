package com.dacklabs.spookyaction.client.main;

import com.google.gwt.user.client.Window;

public class GwtPage implements Page {

	@Override
	public void setWindowTitle(String title) {
		Window.setTitle(title);
	}
}
