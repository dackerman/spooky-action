package com.dacklabs.spookyaction.client.gin;

import com.dacklabs.spookyaction.client.main.GwtPage;
import com.dacklabs.spookyaction.client.main.Page;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;

public class MainModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).asEagerSingleton();
		bind(Page.class).to(GwtPage.class);
	}
}
