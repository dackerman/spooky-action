package com.dacklabs.spookyaction.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Represents a command that the user executed in the editor. Includes the current offset so that
 * each command can be completely duplicated in the file on the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class Command implements IsSerializable {

	private int offset;
	private CommandType type;
	private int keyCode;

	public enum CommandType {
		BACKSPACE, KEY, CLICK
	}

	@SuppressWarnings("unused")
	private Command() {
	}

	public Command(int offset, CommandType type) {
		this(offset, type, -1);
	}

	public Command(int offset, CommandType type, int keyCode) {
		this.offset = offset;
		this.type = type;
		this.keyCode = keyCode;
	}

	/**
	 * The offset into the file. If a key was pressed (which changes this value), the offset is the
	 * value before the user pressed the key.
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * The type of command executed. If it's not a keypress, then the keyCode value may be -1.
	 */
	public CommandType getType() {
		return type;
	}

	/**
	 * The keyCode that was pressed. If
	 */
	public int getKeyCode() {
		return keyCode;
	}
}
