package com.dacklabs.spookyaction.client.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.SimpleEventBus;

public class KeyToCommandConverterTest implements CommandEventHandler {

	private KeyToCommandConverter converter;
	private Command commandReturned;
	private SimpleEventBus eventBus;
	private StubEditingSurface editingSurface;

	@Before
	public void setUp() {
		eventBus = new SimpleEventBus();
		eventBus.addHandler(CommandEvent.TYPE, this);
		editingSurface = new StubEditingSurface();
		converter = new KeyToCommandConverter(eventBus);
		converter.setEditor(editingSurface);
	}

	@Test
	public void testKeyIsTranslatedToACommand() {
		userPressesKey('d');
		assertExpectedCommandWas(new Command(0, 0, CommandType.KEY, "d", 1));
	}

	@Test
	public void testOffsetDependsOnTheCursorPosition() {
		cursorIsAt(24);
		userPressesKey('a');
		assertExpectedCommandWas(new Command(0, 24, CommandType.KEY, "a", 1));
	}

	@Test
	public void testBackspaceGeneratesBackspaceCommand() {
		cursorIsAt(10);
		userPressesKeyCode(KeyCodes.KEY_BACKSPACE);
		assertExpectedCommandWas(new Command(0, 9, CommandType.BACKSPACE, null, 1));
	}

	@Test
	public void testPressingBackspaceMultipleTimesMovesTheCursorBack() {
		cursorIsAt(5);
		userPressesKeyCode(KeyCodes.KEY_BACKSPACE);
		userPressesKeyCode(KeyCodes.KEY_BACKSPACE);
		userPressesKeyCode(KeyCodes.KEY_BACKSPACE);
		userPressesKeyCode(KeyCodes.KEY_BACKSPACE);
		assertCursorIsAt(1);
	}

	@Test
	public void testBackspaceDoesntDoAnythingIfItIsAtTheBeginningOfTheLine() {
		cursorIsAt(0);
		userPressesKeyCode(KeyCodes.KEY_BACKSPACE);
		assertNoCommand();
	}

	@Test
	public void testCursorPositionIsUpdatedAfterProcessingACommmand() {
		cursorIsAt(1);
		userPressesKey('a');
		assertCursorIsAt(2);
	}

	@Test
	public void testCursorPositionChangesAfterMultipleCommands() {
		cursorIsAt(5);
		userPressesKey('d');
		userPressesKey('f');
		userPressesKey('l');
		assertCursorIsAt(8);
	}

	private void cursorIsAt(int location) {
		editingSurface.setLocation(location);
	}

	private void assertExpectedCommandWas(Command expectedCommand) {
		assertNotNull("No command was fired.", commandReturned);
		assertEquals(expectedCommand, commandReturned);
	}

	private void assertNoCommand() {
		assertNull("No command should have been fired. " + commandReturned, commandReturned);
	}

	private void assertCursorIsAt(int expectedCursorLocation) {
		assertEquals(expectedCursorLocation, editingSurface.getCursorLocation());
	}

	private void userPressesKey(char key) {
		converter.onKeyPress(new TestKeyPressEvent(key));
	}

	private void userPressesKeyCode(int keyCode) {
		converter.onKeyUp(new TestKeyUpEvent(keyCode));
	}

	private static class TestKeyUpEvent extends KeyUpEvent {
		private final int keyCode;

		public TestKeyUpEvent(int keyCode) {
			this.keyCode = keyCode;
		}

		@Override
		public int getNativeKeyCode() {
			return keyCode;
		}
	}

	private static class TestKeyPressEvent extends KeyPressEvent {

		private final char key;

		public TestKeyPressEvent(char key) {
			this.key = key;
		}

		@Override
		public char getCharCode() {
			return key;
		}
	}

	@Override
	public void onCommand(Command command) {
		commandReturned = command;
	}
}
