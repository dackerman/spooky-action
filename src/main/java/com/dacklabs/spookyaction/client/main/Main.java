package com.dacklabs.spookyaction.client.main;

import com.dacklabs.spookyaction.client.gin.MainInjector;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class Main implements EntryPoint {

	private final MainInjector injector = GWT.create(MainInjector.class);

	@Override
	public void onModuleLoad() {
		SpookyAction main = injector.getMainView();

		RootPanel.get().add(main);

		main.start();
	}
}
