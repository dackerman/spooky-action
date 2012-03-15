package com.dacklabs.spookyaction.client.editor;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * View for the main editor window, showing the current status and the editor area itself.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class EditorView extends Composite implements Editor.Display {

	private static EditorViewUiBinder uiBinder = GWT.create(EditorViewUiBinder.class);

	interface EditorViewUiBinder extends UiBinder<Widget, EditorView> {
	}

	@UiField Button saveButton;
	@UiField FocusPanel textArea;
	private final TestHandler handler;

	@Inject
	public EditorView() {
		initWidget(uiBinder.createAndBindUi(this));

		this.handler = new TestHandler();
		addKeyPressHandler(handler);
		addKeyUpHandler(handler);
	}

	@Override
	public void setSaveHandler(ClickHandler handler) {
		saveButton.addClickHandler(handler);
	}

	@Override
	public HasText newLine() {
		Label label = new Label();
		textArea.add(label);
		return label;
	}

	@Override
	public void clearWindow() {
		textArea.clear();
	}

	@Override
	public void addKeyPressHandler(KeyPressHandler handler) {
		textArea.addKeyPressHandler(handler);
	}

	@Override
	public void addKeyUpHandler(KeyUpHandler handler) {
		textArea.addKeyUpHandler(handler);
	}

	private static class TestHandler implements KeyPressHandler, KeyUpHandler {

		private static final Logger logger = Logger.getLogger(TestHandler.class.getName());

		@Override
		public void onKeyUp(KeyUpEvent event) {
			// logger.severe("Key up!");
		}

		@Override
		public void onKeyPress(KeyPressEvent event) {
			logger.severe("Key press!");
		}
	}
}
