package com.dacklabs.spookyaction.client.testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import com.dacklabs.spookyaction.client.rpc.FileServiceAsync;
import com.dacklabs.spookyaction.shared.Command;
import com.dacklabs.spookyaction.shared.File;
import com.dacklabs.spookyaction.shared.UpdateResult;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StubFileService implements FileServiceAsync {

	private String pathRequested;
	private AsyncCallback<File> fromPathCallback;

	private String pathUpdated;
	private ArrayList<Command> commandsSaved;
	private AsyncCallback<UpdateResult> updateFileCallback;
	private int numberOfUpdateRpcs;

	@Override
	public void fromPath(String path, AsyncCallback<File> callback) {
		this.pathRequested = path;
		this.fromPathCallback = callback;
	}

	@Override
	public void updateFile(String path, ArrayList<Command> commands, AsyncCallback<UpdateResult> callback) {
		numberOfUpdateRpcs++;
		this.pathUpdated = path;
		this.commandsSaved = commands;
		this.updateFileCallback = callback;
	}

	public void updateFileSuccess() {
		updateFileCallback.onSuccess(UpdateResult.success());
	}

	public void updateFileFailure() {
		updateFileCallback.onFailure(new RuntimeException("fail"));
	}

	public void assertCommandSaved(String expectedPath, Command command) {
		assertCommandsSaved(expectedPath, command);
	}

	public void assertCommandsSaved(String expectedPath, Command... expectedCommands) {
		assertEquals("Requested path doesn't match.", expectedPath, pathUpdated);
		assertEquals(
		    String.format("Size of command list is different: %nexpected:%s%nactual:%s%n",
		        Lists.newArrayList(expectedCommands), commandsSaved), commandsSaved.size(), expectedCommands.length);
		for (int i = 0; i < expectedCommands.length; i++) {
			assertEquals("Command " + i + " doesn't match", commandsSaved.get(i), expectedCommands[i]);
		}
	}

	public void assertNumberOfUpdateRpcsFired(int expectedNumberOfCommands) {
		assertEquals("Number of RPCs doesn't match.", expectedNumberOfCommands, numberOfUpdateRpcs);
	}
}
