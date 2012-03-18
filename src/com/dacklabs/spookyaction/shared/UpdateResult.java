package com.dacklabs.spookyaction.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Represents the result of a file's update on the server.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class UpdateResult implements IsSerializable {

	private ArrayList<FailedCommand> failedCommands = new ArrayList<FailedCommand>();

	public static UpdateResult success() {
		return new UpdateResult();
	}

	private UpdateResult() {
		failedCommands = new ArrayList<FailedCommand>();
	}

	public boolean wasSuccessful() {
		return failedCommands.isEmpty();
	}

	public Iterable<FailedCommand> failedCommands() {
		return failedCommands;
	}

	public static class FailedCommand implements IsSerializable {
		public final Command command;
		public final String reason;

		public FailedCommand(Command command, String reason) {
			this.command = command;
			this.reason = reason;
		}
	}

	public static class Builder {
		private UpdateResult r = new UpdateResult();

		public Builder addFailedCommand(Command c, String reason) {
			r.failedCommands.add(new FailedCommand(c, reason));
			return this;
		}

		public UpdateResult build() {
			UpdateResult built = r;
			r = new UpdateResult();
			return built;
		}
	}
}
