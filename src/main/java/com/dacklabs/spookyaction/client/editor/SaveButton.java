package com.dacklabs.spookyaction.client.editor;

import java.util.ArrayList;

import com.dacklabs.spookyaction.client.command.CommandEvent;
import com.dacklabs.spookyaction.client.command.CommandEventHandler;
import com.dacklabs.spookyaction.client.events.ErrorEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEventHandler;
import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * The save button that keeps track of what commands have been applied to the editor and syncs them
 * with the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class SaveButton implements OpenFileEventHandler, CommandEventHandler, ClickHandler, IsWidget {

	@ImplementedBy(SaveButtonView.class)
	public interface Display extends IsWidget {

		/**
		 * Sets the button background to green and updates the button's text.
		 */
		void setGreenText(String string);

		/**
		 * Sets the button background to yellow and updates the button's text.
		 */
		void setYellowText(String string);

		/**
		 * Sets the button background to red and updates the button's text.
		 */
		void setRedText(String string);

		/**
		 * Sets the style of the view.
		 */
		void setStyleName(String className);

		/**
		 * Set whether this button is enabled or not.
		 */
		void setEnabled(boolean enabled);

		/**
		 * Sets the click handler for the button
		 */
		void setClickHandler(ClickHandler handler);
	}

	private final Display display;
	private final FileServiceAsync fileService;

	private File file;
	private ArrayList<Command> commands = new ArrayList<Command>();
	private final EventBus eventBus;

	@Inject
	public SaveButton(Display display, FileServiceAsync fileService, EventBus eventBus) {
		this.display = display;
		this.fileService = fileService;
		this.eventBus = eventBus;

		eventBus.addHandler(OpenFileEvent.TYPE, this);
		eventBus.addHandler(CommandEvent.TYPE, this);
		display.setClickHandler(this);
		display.setEnabled(false);
	}

	@Override
	public void onClick(ClickEvent event) {
		display.setEnabled(false);
		if (commands.isEmpty()) {
			return;
		}
		ArrayList<Command> commandsToSave = commands;
		commands = new ArrayList<Command>();
		fileService.updateFile(file.getFilename(), commandsToSave, new UpdateFileCallback(commandsToSave));
	}

	@Override
	public void onCommand(Command command) {
		display.setEnabled(true);
		commands.add(command);
		display.setYellowText("Save (" + commands.size() + ")");
	}

	@Override
	public void onFileRecieved(File file) {
		this.file = file;
	}

	public void setStyleName(String className) {
		display.setStyleName(className);
	}

	public class UpdateFileCallback implements AsyncCallback<UpdateResult> {

		private final ArrayList<Command> commandsToSave;

		public UpdateFileCallback(ArrayList<Command> commandsToSave) {
			this.commandsToSave = commandsToSave;
		}

		@Override
		public void onSuccess(UpdateResult result) {
			display.setGreenText("Saved");
		}

		@Override
		public void onFailure(Throwable caught) {
			eventBus.fireEvent(new ErrorEvent("Failed to save the file to the server" + caught.getClass()));
			for (int i = commandsToSave.size() - 1; i >= 0; i--) {
				commands.add(0, commandsToSave.get(i));
			}
			display.setRedText("Retry");
			display.setEnabled(true);
		}
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
