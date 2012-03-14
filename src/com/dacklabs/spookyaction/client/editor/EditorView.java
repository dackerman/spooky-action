package com.dacklabs.spookyaction.client.editor;

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
import com.google.gwt.user.client.ui.FlowPanel;
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
	@UiField FlowPanel textArea;

	@Inject
	public EditorView() {
		initWidget(uiBinder.createAndBindUi(this));
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
		textArea.addDomHandler(handler, KeyPressEvent.getType());
	}

	@Override
	public void addKeyUpHandler(KeyUpHandler handler) {
		textArea.addDomHandler(handler, KeyUpEvent.getType());
	}
}
