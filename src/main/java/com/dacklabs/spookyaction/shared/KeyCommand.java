package com.dacklabs.spookyaction.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class KeyCommand extends Command implements IsSerializable {

	protected KeyCommand() {
	}

	public KeyCommand(int line, int offset, CommandType type, String data, int repeated) {
		super(line, offset, type, data, repeated);
	}

	@Override
	public boolean merge(Command other) {
		if (!areOnSameLine(other)) {
			return false;
		}
		int location = other.offset - offset;
		if (typesMatch(type, other) && offsetWithinRange(other)) {
			StringBuffer buffer = new StringBuffer(data);
			for (int i = 0; i < other.repeated; i++) {
				buffer.insert(location, other.data);
			}
			data = buffer.toString();
			return true;
		} else if (other.type.equals(CommandType.BACKSPACE) && backspaceWithinRange(other)) {
			StringBuffer buffer = new StringBuffer(data);
			for (int i = 0; i < other.repeated; i++) {
				buffer.delete(location - 1, location);
			}
			data = buffer.toString();
			return true;
		}
		return false;
	}

	@Override
	public boolean isNoOp() {
		return getData().isEmpty();
	}

	private boolean backspaceWithinRange(Command other) {
		int adjusted = other.offset - 1;
		return offset <= adjusted && (offset + data.length()) >= adjusted;
	}

	private boolean offsetWithinRange(Command other) {
		return offset <= other.offset && (offset + data.length()) >= other.offset;
	}
}
