package com.dacklabs.spookyaction.server;

import com.dacklabs.spookyaction.shared.Command;

public class NullProcessor implements CommandProcessor {

	@Override
	public boolean process(Command command, StringBuffer data) {
		// Do nothing.
		return true;
	}
}
