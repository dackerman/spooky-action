package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Display for the {@link CommandStatus}.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class CommandStatusView extends Composite implements CommandStatus.Display {

	private static CommandStatusViewUiBinder uiBinder = GWT.create(CommandStatusViewUiBinder.class);

	interface CommandStatusViewUiBinder extends UiBinder<Widget, CommandStatusView> {
	}

	@UiField Label currentCommand;
	@UiField HTMLPanel commandsInQueue;

	public CommandStatusView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setCurrentCommand(String text) {
		currentCommand.setText(text);
	}

	@Override
	public void clearQueueView() {
		commandsInQueue.clear();
	}

	@Override
	public void addToQueueList(String command) {
		commandsInQueue.add(new HTML(command));
	}
}
