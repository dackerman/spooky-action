package com.dacklabs.spookyaction.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.shared.Command.CommandType;

public class KeyCommandTest {

	@Before
	public void setUp() {
	}

	@Test
	public void testCompressesKeyCommands() {
		KeyCommand keyCommand = new KeyCommand(0, 0, CommandType.KEY, "david ", 1);
		boolean result = keyCommand.merge(new Command(0, 6, CommandType.KEY, "ackerman", 1));

		assertEquals("david ackerman", keyCommand.getData());
		assertTrue("Result of merge should be true", result);
	}

	@Test
	public void testCompressesRepeatedKeyCommands() {
		KeyCommand keyCommand = new KeyCommand(0, 0, CommandType.KEY, "ba", 1);
		boolean result = keyCommand.merge(new Command(0, 2, CommandType.KEY, "na", 2));

		assertEquals("banana", keyCommand.getData());
		assertTrue("Result of merge should be true", result);
	}

	@Test
	public void testBackspaceCommandDeletesKeys() {
		KeyCommand keyCommand = new KeyCommand(5, 1, CommandType.KEY, "meoown", 1);
		boolean result = keyCommand.merge(new Command(5, 4, CommandType.BACKSPACE, null, 1));
		result &= keyCommand.merge(new Command(5, 6, CommandType.BACKSPACE, null, 1));

		assertEquals("meow", keyCommand.getData());
		assertTrue("Result of merge should be true", result);
	}

	@Test
	public void testBackspaceCanCancelOutAKeyOperation() {
		KeyCommand keyCommand = new KeyCommand(2, 2, CommandType.KEY, "F*&#", 1);
		assertFalse(keyCommand.isNoOp());
		boolean result = keyCommand.merge(new Command(2, 3, CommandType.BACKSPACE, null, 4));

		assertEquals("", keyCommand.getData());
		assertTrue("Key command should have been a no-op", keyCommand.isNoOp());
		assertTrue("Result of merge should be true", result);
	}
}
