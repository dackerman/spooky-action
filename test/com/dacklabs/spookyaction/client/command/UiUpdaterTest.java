package com.dacklabs.spookyaction.client.command;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;

public class UiUpdaterTest {

	private UiUpdater updater;
	private StubEditingSurface editingSurface;

	@Before
	public void setUp() {
		editingSurface = new StubEditingSurface();
		updater = new UiUpdater(editingSurface);
	}

	@Test
	public void testTextCommandInsertsCharacterIntoEditor() {
		editingSurface.setContent("");
		sendCommand(new Command(0, 0, CommandType.KEY, "b", 1));
		assertEquals("b", editingSurface.getLine(0));
	}

	@Test
	public void testTextCommandInsertsCharacterMultipleTimes() {
		editingSurface.setContent("Bill Bu");
		sendCommand(new Command(0, 7, CommandType.KEY, "r", 5));
		assertEquals("Bill Burrrrr", editingSurface.getLine(0));
	}

	@Test
	public void testTextCommandOnDifferentLines() {
		editingSurface.setContent("This is line one", "This is line to");
		sendCommand(new Command(1, 14, CommandType.KEY, "w", 1));
		assertEquals("This is line one", editingSurface.getLine(0));
		assertEquals("This is line two", editingSurface.getLine(1));
	}

	@Test
	public void testBackspaceCommandRemovesACharacter() {
		editingSurface.setContent("Bill Brasky was a wimp");
		sendCommand(new Command(0, 11, CommandType.BACKSPACE, null, 11));
		assertEquals("Bill Brasky", editingSurface.getLine(0));
	}

	private void sendCommand(Command command) {
		updater.onCommand(command);
	}

	private static class StubEditingSurface implements EditingSurface {

		private String[] content = new String[] {};

		public void setContent(String... content) {
			this.content = content;
		}

		@Override
		public String getLine(int lineNumber) {
			return content[lineNumber];
		}

		@Override
		public void updateLine(int lineNumber, String line) {
			content[lineNumber] = line;
		}
	}
}
