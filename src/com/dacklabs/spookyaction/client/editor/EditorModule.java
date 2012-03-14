package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.inject.client.AbstractGinModule;

public class EditorModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(Editor.class).asEagerSingleton();
	}
}
