package com.dacklabs.spookyaction.client.main;

import com.dacklabs.spookyaction.client.command.CommandPipeline;
import com.dacklabs.spookyaction.client.gin.MainInjector;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Main implements EntryPoint {

	private final MainInjector injector = GWT.create(MainInjector.class);

	@Override
	public void onModuleLoad() {
		RootLayoutPanel.get().add(injector.getMainView());

		CommandPipeline mediator = injector.getCommandPipeline();
		mediator.hookUpEvents();
	}
}
