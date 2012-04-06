package com.dacklabs.spookyaction.client.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.client.command.CommandEvent;
import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.testing.StubFileService;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Widget;

public class SaveButtonTest {

	private final StubDisplay display = new StubDisplay();
	private final SimpleEventBus eventBus = new SimpleEventBus();
	private final StubFileService fileService = new StubFileService();

	@Before
	public void setUp() {
		createSaveButton();
	}

	@Test
	public void testSaveButtonIsDisabledIfThereIsNothingToSave() {
		display.assertDisabled(); // Starts out disabled

		fireOpenFileEvent("/testfile");
		eventBus.fireEvent(new CommandEvent(basicKeyCommand()));

		display.assertEnabled(); // Enabled once there are commands to sync

		display.click();

		display.assertDisabled(); // Disabled after save is clicked

		fileService.updateFileFailure(); // If there's a failure, you should be able to retry

		display.assertEnabled();
	}

	@Test
	public void testSaveButtonSavesCommandToBackend() {
		fireOpenFileEvent("/testfile");
		eventBus.fireEvent(new CommandEvent(basicKeyCommand()));

		display.click();
		fileService.assertCommandSaved("/testfile", basicKeyCommand());
	}

	@Test
	public void testSaveButtonSavesMultipleCommandsToBackend() {
		fireOpenFileEvent("/testfile2");
		eventBus.fireEvent(new CommandEvent(basicKeyCommand()));
		eventBus.fireEvent(new CommandEvent(basicKeyCommand()));

		display.click();
		fileService.assertCommandsSaved("/testfile2", basicKeyCommand(), basicKeyCommand());
	}

	@Test
	public void testSaveButtonDoesntSaveTheSameCommandTwice() {
		fireOpenFileEvent("/somefile");
		eventBus.fireEvent(new CommandEvent(basicKeyCommand()));
		display.click();
		display.click();
		eventBus.fireEvent(new CommandEvent(basicBackspaceCommand()));
		display.click();

		fileService.assertNumberOfUpdateRpcsFired(2);
		fileService.assertCommandSaved("/somefile", basicBackspaceCommand());
	}

	@Test
	public void testSaveButtonIsYellowAndTracksTheNumberOfCommandsToSync() {
		fireOpenFileEvent("/somefile");
		fireKeyCommands(3);

		display.assertYellowText("Save (3)");
	}

	@Test
	public void testSaveButtonIsGreenAfterClickingSave() {
		fireOpenFileEvent("/somefile");
		fireKeyCommands(5);

		display.click();
		fileService.updateFileSuccess();
		display.assertGreenText("Saved");
	}

	@Test
	public void testSaveButtonIsRedAfterClickingSaveAndFailing() {
		fireOpenFileEvent("/somefile");
		fireKeyCommands(5);

		display.click();
		fileService.updateFileFailure();
		display.assertRedText("Retry");
	}

	@Test
	public void testRetriesOldAndNewCommandsIfThereWasAFailure() {
		fireOpenFileEvent("/somefile");
		eventBus.fireEvent(new CommandEvent(commandWithData("command1")));

		display.click();
		fileService.updateFileFailure();

		eventBus.fireEvent(new CommandEvent(commandWithData("command2")));

		display.click();
		fileService.updateFileSuccess();

		fileService.assertCommandsSaved("/somefile", commandWithData("command1"), commandWithData("command2"));
		fileService.assertNumberOfUpdateRpcsFired(2);
	}

	private void fireKeyCommands(int numberToFire) {
		for (int i = 0; i < numberToFire; i++) {
			eventBus.fireEvent(new CommandEvent(basicKeyCommand()));
		}
	}

	private void fireOpenFileEvent(String filename) {
		eventBus.fireEvent(fileEventWithName(filename));
	}

	private OpenFileEvent fileEventWithName(String filename) {
		return fileEvent(filename, "");
	}

	private OpenFileEvent fileEvent(String filename, String content) {
		return new OpenFileEvent(new File(filename, content));
	}

	private Command basicBackspaceCommand() {
		return Command.builder(CommandType.BACKSPACE).build();
	}

	private Command commandWithData(String data) {
		return Command.builder(CommandType.KEY).withData(data).build();
	}

	private Command basicKeyCommand() {
		return Command.builder(CommandType.KEY).build();
	}

	private SaveButton createSaveButton() {
		return new SaveButton(display, fileService, eventBus);
	}

	private static class StubDisplay implements SaveButton.Display {

		private String greenText;
		private String yellowText;
		private String redText;
		private ClickHandler handler;

		private Boolean enabled; // nullable

		public void assertGreenText(String expectedText) {
			assertEquals(expectedText, greenText);
		}

		public void assertEnabled() {
			if (enabled == null) {
				Assert.fail("enabled/disabled never set");
			}
			assertTrue("Button should be enabled", enabled == true);
		}

		public void assertDisabled() {
			if (enabled == null) {
				Assert.fail("enabled/disabled never set");
			}
			assertTrue("Button should be disabled", enabled == false);
		}

		public void assertYellowText(String expectedText) {
			assertEquals(expectedText, yellowText);
		}

		public void assertRedText(String expectedText) {
			assertEquals(expectedText, redText);
		}

		public void click() {
			handler.onClick(null);
		}

		@Override
		public void setGreenText(String text) {
			this.greenText = text;
		}

		@Override
		public void setYellowText(String text) {
			this.yellowText = text;
		}

		@Override
		public void setRedText(String text) {
			this.redText = text;
		}

		@Override
		public Widget asWidget() {
			return null;
		}

		@Override
		public void setStyleName(String className) {
		}

		@Override
		public void setClickHandler(ClickHandler handler) {
			this.handler = handler;
		}

		@Override
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}
}
