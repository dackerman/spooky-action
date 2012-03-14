package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.client.editor.Editor;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

public class CommandModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EditingSurface.class).to(Editor.class).asEagerSingleton();
	}

	@Provides
	KeyToCommandConverter getConverter(Editor editor, EventBus eventBus) {
		return new KeyToCommandConverter(editor, eventBus);
	}
}
