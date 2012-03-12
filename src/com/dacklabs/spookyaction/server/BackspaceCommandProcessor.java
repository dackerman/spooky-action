package com.dacklabs.spookyaction.server;

import com.dacklabs.spookyaction.shared.Command;

public class BackspaceCommandProcessor implements CommandProcessor {

	@Override
	public boolean process(Command command, StringBuffer data) {
		try {
			data.deleteCharAt(0);
			return true;
		} catch (StringIndexOutOfBoundsException e) {
			return false;
		}
	}
}
