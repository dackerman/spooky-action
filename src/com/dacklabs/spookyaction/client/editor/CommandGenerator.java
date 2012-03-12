package com.dacklabs.spookyaction.client.editor;

import java.util.ArrayList;
import java.util.List;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.inject.Singleton;

/**
 * Listens for key and click events on an editor box, and generates {@link Command}s from them.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
@Singleton
public class CommandGenerator implements KeyDownHandler, ClickHandler {

	private EditorArea editorArea;
	private final List<CommandConsumer> consumers = new ArrayList<CommandConsumer>();

	@Override
	public void onKeyDown(KeyDownEvent event) {
		int loc = editorArea.cursorLocation();
		logCommand(new Command(loc, CommandType.KEY, event.getNativeKeyCode()));
	}

	@Override
	public void onClick(ClickEvent event) {
		int loc = editorArea.cursorLocation();
		logCommand(new Command(loc, CommandType.CLICK));
	}

	private void logCommand(Command c) {
		for (CommandConsumer consumer : consumers) {
			consumer.commandDetected(c);
		}
	}

	public void setEditor(EditorArea editorArea) {
		this.editorArea = editorArea;
	}

	public void addCommandConsumer(CommandConsumer consumer) {
		consumers.add(consumer);
	}
}
