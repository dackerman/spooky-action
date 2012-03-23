package com.dacklabs.spookyaction.client.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
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

	private void assertExpectedCommandWas(Command expectedCommand) {
		assertNotNull("No command was fired.", commandReturned);
		assertEquals(expectedCommand, commandReturned);
	}

	private void assertNoCommand() {
		assertNull("No command should have been fired. " + commandReturned, commandReturned);
	}

	private void userPressesKey(char key) {
		converter.onKeyPress(0, 0, new TestKeyPressEvent(key));
	}

	private void userPressesKeyCode(int keyCode) {
		converter.onKeyUp(0, 0, new TestKeyUpEvent(keyCode));
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
