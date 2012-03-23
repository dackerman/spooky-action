package com.dacklabs.spookyaction.client.command;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.client.testing.StubEditingSurface;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.dacklabs.spookyaction.shared.CommandExecutor;

public class CommandExecutorTest {

	private StubEditingSurface editingSurface;

	@Before
	public void setUp() {
		editingSurface = new StubEditingSurface();
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

	private void assertEquals(String string, StringBuffer line) {
		Assert.assertEquals(string, line.toString());
	}

	private void sendCommand(Command command) {
		CommandExecutor.processCommand(editingSurface, command);
	}
}
