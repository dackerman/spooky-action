package com.dacklabs.spookyaction.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Represents a command that the user executed in the editor. Includes the current offset so that
 * each command can be completely duplicated in the file on the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class Command implements IsSerializable {

	protected int offset;
	protected CommandType type;
	protected String data;
	protected int repeated;
	protected int line;

	public enum CommandType {
		BACKSPACE, KEY, CLICK, NEWLINE, MOVE_LINE
	}

	protected Command() {
	}

	public Command(int line, int offset, CommandType type, String data, int repeated) {
		this.line = line;
		this.offset = offset;
		this.type = type;
		this.data = data;
		this.repeated = repeated;
	}

	/**
	 * Attempts to merge the given command into this one. Returns false if it can't be merged.
	 */
	public boolean merge(Command otherCommand) {
		return false; // Only subclasses attempt optimizations
	}

	/**
	 * Returns whether or not this command is a "no-op", meaning that it has no effect when applied to
	 * a document. This might mean a key command with no data, or a backspace repeated 0 times.
	 * <p>
	 * This can happen if another command merges with this one and effectively cancels it out.
	 */
	public boolean isNoOp() {
		return false;
	}

	/**
	 * The line number on which the command was executed.
	 */
	public int getLineNumber() {
		return line;
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

	protected boolean typesMatch(CommandType type, Command other) {
		return getType().equals(type) && other.getType().equals(type);
	}

	protected boolean areOnSameLine(Command other) {
		return getLineNumber() == other.getLineNumber();
	}

	protected boolean haveSameOffset(Command other) {
		return getOffset() == other.getOffset();
	}

	protected boolean haveSameData(Command other) {
		return getData().equals(other.getData());
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

	public static Command.Builder builder(CommandType type) {
		return new Builder(type);
	}

	public static class Builder {
		private Command command;

		private Builder(CommandType type) {
			if (type.equals(CommandType.KEY)) {
				command = new KeyCommand(0, 0, type, "", 1);
			} else {
				command = newCommand();
				command.type = type;
			}
		}

		public Builder onLine(int line) {
			command.line = line;
			return this;
		}

		public Builder withOffset(int offset) {
			command.offset = offset;
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
			return command;
		}

		private Command newCommand() {
			return new Command(0, 0, CommandType.KEY, null, 1);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + offset;
		result = prime * result + repeated;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (offset != other.offset)
			return false;
		if (repeated != other.repeated)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Command [offset=");
		builder.append(offset);
		builder.append(", type=");
		builder.append(type);
		builder.append(", data=");
		builder.append(data);
		builder.append(", repeated=");
		builder.append(repeated);
		builder.append(", line=");
		builder.append(line);
		builder.append("]");
		return builder.toString();
	}
}
