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
	private String data;
	private int repeated = 1;

	public enum CommandType {
		BACKSPACE, KEY, CLICK
	}

	@SuppressWarnings("unused")
	private Command() {
	}

	public Command(int offset, CommandType type, String data, int repeated) {
		this.offset = offset;
		this.type = type;
		this.data = data;
		this.repeated = repeated;
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
	 * The data included in the command.
	 */
	public String getData() {
		return data;
	}

	public int getRepeated() {
		return repeated;
	}

	public static Command.Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Command command = newCommand();

		private Builder() {
		}

		public Builder withOffset(int offset) {
			command.offset = offset;
			return this;
		}

		public Builder ofType(CommandType type) {
			command.type = type;
			return this;
		}

		public Builder withData(String data) {
			command.data = data;
			return this;
		}

		public Builder repeatedTimes(int repeated) {
			command.repeated = repeated;
			return this;
		}

		public Command build() {
			Command builtCommand = command;
			command = newCommand();
			return builtCommand;
		}

		private Command newCommand() {
			return new Command(0, CommandType.KEY, null, 1);
		}
	}
}
