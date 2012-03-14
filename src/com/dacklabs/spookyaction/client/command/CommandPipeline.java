package com.dacklabs.spookyaction.client.command;

import com.dacklabs.spookyaction.client.editor.Editor;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class CommandPipeline {

	private final UiUpdater uiUpdater;
	private final KeyToCommandConverter converter;
	private final EventBus eventBus;
	private final Editor editor;

	@Inject
	public CommandPipeline(Editor editor, KeyToCommandConverter converter, UiUpdater uiUpdater, EventBus eventBus) {
		this.editor = editor;
		this.converter = converter;
		this.uiUpdater = uiUpdater;
		this.eventBus = eventBus;
	}

	public void hookUpEvents() {
		editor.addKeyPressHandler(converter);
		editor.addKeyUpHandler(converter);

		eventBus.addHandler(CommandEvent.TYPE, uiUpdater);
	}
}
