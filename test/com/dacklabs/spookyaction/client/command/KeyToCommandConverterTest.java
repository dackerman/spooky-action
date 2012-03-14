package com.dacklabs.spookyaction.client.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.client.editor.HasCursor;
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
	private StubHasCursor hasCursor;

	@Before
	public void setUp() {
		eventBus = new SimpleEventBus();
		eventBus.addHandler(CommandEvent.TYPE, this);
		hasCursor = new StubHasCursor();
		converter = new KeyToCommandConverter(hasCursor, eventBus);
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
		assertExpectedCommandWas(new Command(0, 10, CommandType.BACKSPACE, null, 1));
	}

	private void cursorIsAt(int location) {
		hasCursor.setLocation(location);
	}

	private void assertExpectedCommandWas(Command expectedCommand) {
		assertNotNull("No command was fired.", commandReturned);
		assertEquals(expectedCommand, commandReturned);
	}

	private void userPressesKey(char key) {
		converter.onKeyPress(new TestKeyPressEvent(key));
	}

	private void userPressesKeyCode(int keyCode) {
		converter.onKeyUp(new TestKeyUpEvent(keyCode));
	}

	private static class StubHasCursor implements HasCursor {

		private int location;

		public void setLocation(int location) {
			this.location = location;
		}

		@Override
		public int getCursorLocation() {
			return location;
		}
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
