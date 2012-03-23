package com.dacklabs.spookyaction.client.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dacklabs.spookyaction.client.events.OpenFileEvent;
import com.dacklabs.spookyaction.client.events.SaveRequestedEvent;
import com.dacklabs.spookyaction.client.events.SaveRequestedEventHandler;
import com.dacklabs.spookyaction.shared.File;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;

public class EditorTest {

	private final StubDisplay stubDisplay = new StubDisplay();
	private final StubPage stubPage = new StubPage();
	private final EventBus eventBus = new SimpleEventBus();
	private final Provider<EditorLine> stubProvider = new StubLineProvider();

	private Editor editor;

	@Before
	public void setUp() {
		editor = new Editor(stubDisplay, eventBus, stubPage, stubProvider);
	}

	@Test
	public void testSetsContentOnFileOpen() {
		fireOpenFileEvent("myfile", "test content");

		assertDisplayIs("test content");
	}

	@Test
	public void testSetsContentOnMultipleLines() {
		fireOpenFileEvent("myfile", "lineone\nlinetwo");

		assertDisplayIs("lineone", "linetwo");
	}

	@Test
	public void testSetsContentWithBlankLines() {
		fireOpenFileEvent("myfile", "lineone\n\nlinetwo");

		assertDisplayIs("lineone", "", "linetwo");
	}

	@Test
	public void testOpeningASecondFileOverwritesTheFirst() {
		fireOpenFileEvent("myfile1", "one\ntwo");
		fireOpenFileEvent("myfile2", "testing");

		assertDisplayIs("testing");
	}

	@Test
	public void testGetLine_ReturnsCorrectLine() {
		fireOpenFileEvent("myfile", "one\ntwo\nthree\n");

		assertEquals("two", editor.getLine(1).toString());
	}

	@Test
	public void updateLine_UpdatesCorrectLine() {
		fireOpenFileEvent("testfile", "blah\nblerg\nblam\nblow");

		editor.updateLine(2, new StringBuffer("blammo"));

		assertDisplayIs("blah", "blerg", "blammo", "blow");
	}

	@Test
	public void testClickSave_FiresSaveRequestedEventWithTheCorrectFile() {
		SaveRequestedEventCatcher handler = listenForSaveRequestedEvent();
		fireOpenFileEvent("myfile", "data");
		clickSaveButton();

		assertTrue("SaveRequestedEvent was never fired", handler.saveRequested);
	}

	/**
	 * Loops through stub display and verifies that each line matches the expected lines. Also
	 * verifies that the number of lines is the same (in case the display has more lines than
	 * expected).
	 */
	private void assertDisplayIs(String... lines) {
		int lineNumber = 0;
		for (String line : lines) {
			assertEquals("lines not equal", line, stubDisplay.line(lineNumber++));
		}
		assertEquals("Display has wrong number of lines.", lines.length, stubDisplay.lines());
	}

	private void clickSaveButton() {
		stubDisplay.clickHandler.onClick(stubClickEvent());
	}

	private ClickEvent stubClickEvent() {
		return new ClickEvent() {
		};
	}

	private void fireOpenFileEvent(String filename, String content) {
		eventBus.fireEvent(new OpenFileEvent(new File(filename, content)));
	}

	private SaveRequestedEventCatcher listenForSaveRequestedEvent() {
		SaveRequestedEventCatcher handler = new SaveRequestedEventCatcher();
		eventBus.addHandler(SaveRequestedEvent.TYPE, handler);
		return handler;
	}

	private static class SaveRequestedEventCatcher implements SaveRequestedEventHandler {

		boolean saveRequested = false;

		@Override
		public void onSaveRequested(SaveRequestedEvent event) {
			saveRequested = true;
		}
	}

	private static class StubLineProvider implements Provider<EditorLine> {

		@Override
		public EditorLine get() {
			return new EditorLine(new StubLineDisplay());
		}
	}

	private static class StubLineDisplay implements EditorLine.Display {

		private String text;

		@Override
		public Widget asWidget() {
			return null;
		}

		@Override
		public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
			return null;
		}

		@Override
		public void fireEvent(GwtEvent<?> event) {
		}

		@Override
		public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
			return null;
		}

		@Override
		public String getText() {
			return text;
		}

		@Override
		public void setText(String text) {
			this.text = text;
		}

		@Override
		public HandlerRegistration addClickHandler(ClickHandler handler) {
			return null;
		}

		@Override
		public String getHTML() {
			return text;
		}

		@Override
		public void setHTML(String html) {
			text = html;
		}

		@Override
		public int getCursorPos() {
			return 0;
		}

		@Override
		public void setCursorPos(int pos) {
		}
	}
}
