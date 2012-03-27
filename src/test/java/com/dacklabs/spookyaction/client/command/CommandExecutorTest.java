package com.dacklabs.spookyaction.client.command;

import static org.junit.Assert.assertArrayEquals;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.client.testing.StubEditingSurface;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.dacklabs.spookyaction.shared.CommandExecutor;
import com.google.common.collect.Lists;

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
		sendCommand(new Command(0, 12, CommandType.BACKSPACE, null, 11));
		assertEquals("Bill Brasky", editingSurface.getLine(0));
	}

	@Test
	public void testBackspaceCommandConcatenatesWithPreviousLineIfAtBeginningOfLine() {
		editingSurface.setContent("lorem ", "ipsum");
		sendCommand(new Command(1, 0, CommandType.BACKSPACE, null, 1));
		assertLines("lorem ipsum");
	}

	@Test
	public void testEnterKeyCreatesANewLineAndSplitsTheExistingOneAtTheCursorPosition() {
		editingSurface.setContent("extravaganza", "superfluous", "ambidexterous");
		sendCommand(new Command(1, 5, CommandType.NEWLINE, null, 1));
		assertLines("extravaganza", "super", "fluous", "ambidexterous");
	}

	@Test
	public void testHittingEnterTwiceMakesTwoNewlines() {
		editingSurface.setContent("extravaganza", "superfluous", "ambidexterous");
		sendCommand(new Command(2, 4, CommandType.NEWLINE, null, 1));
		sendCommand(new Command(3, 0, CommandType.NEWLINE, null, 1));
		assertLines("extravaganza", "superfluous", "ambi", "", "dexterous");
	}

	@Test
	public void testMoveCommandSwapsLineBelow() {
		editingSurface.setContent("line one", "line two", "line three");
		sendCommand(new Command(1, 0, CommandType.MOVE_LINE, null, 1));
		assertLines("line one", "line three", "line two");
	}

	@Test
	public void testMoveCommandSwapsTwoLinesBelow() {
		editingSurface.setContent("line one", "line two", "line three", "line four");
		sendCommand(new Command(1, 0, CommandType.MOVE_LINE, null, 2));
		assertLines("line one", "line three", "line four", "line two");
	}

	@Test
	public void testMoveCommandSwapsNegativeLines() {
		editingSurface.setContent("one", "two", "three");
		sendCommand(new Command(1, 0, CommandType.MOVE_LINE, null, -1));
		assertLines("two", "one", "three");
	}

	@Test
	public void testMoveCommandSwapsMultipleNegativeLines() {
		editingSurface.setContent("one", "two", "three", "seven", "eleven", "schfifty-five");
		sendCommand(new Command(5, 0, CommandType.MOVE_LINE, null, -3));
		assertLines("one", "two", "schfifty-five", "three", "seven", "eleven");
	}

	private void assertLines(String... expectedLines) {
		String[] actualLines = editingSurface.linesAsArray();
		assertArrayEquals(
		    String.format("expected: %s actual: %s", Lists.newArrayList(expectedLines), Lists.newArrayList(actualLines)),
		    expectedLines, actualLines);
	}

	private void assertEquals(String string, StringBuffer line) {
		Assert.assertEquals(string, line.toString());
	}

	private void sendCommand(Command command) {
		CommandExecutor.processCommand(editingSurface, command);
	}
}
