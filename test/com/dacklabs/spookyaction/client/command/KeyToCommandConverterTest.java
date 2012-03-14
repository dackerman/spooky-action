package com.dacklabs.spookyaction.client.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.Command.CommandType;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.shared.SimpleEventBus;

public class KeyToCommandConverterTest implements CommandEventHandler {

	private KeyToCommandConverter converter;
	private Command commandReturned;
	private SimpleEventBus eventBus;

	@Before
	public void setUp() {
		eventBus = new SimpleEventBus();
		eventBus.addHandler(CommandEvent.TYPE, this);
		converter = new KeyToCommandConverter(eventBus);
	}

	@Test
	public void testKeyIsTranslatedToACommand() {
		userPressesKey('d');
		assertExpectedCommandWas(new Command(0, CommandType.KEY, "d", 1));
	}

	private void assertExpectedCommandWas(Command expectedCommand) {
		assertNotNull("No command was fired.", commandReturned);
		assertEquals(expectedCommand, commandReturned);
	}

	private void userPressesKey(char key) {
		converter.onKeyPress(new TestKeyPressEvent(key));
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
