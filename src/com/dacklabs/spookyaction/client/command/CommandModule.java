package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.client.editor.Editor;
import com.google.gwt.inject.client.AbstractGinModule;

public class CommandModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(Editor.class).asEagerSingleton();
		bind(CommandQueue.class).asEagerSingleton();
	}
}
