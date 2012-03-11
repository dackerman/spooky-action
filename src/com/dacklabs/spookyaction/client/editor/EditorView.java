package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class EditorView extends Composite implements Editor.Display {

	private static EditorViewUiBinder uiBinder = GWT.create(EditorViewUiBinder.class);

	interface EditorViewUiBinder extends UiBinder<Widget, EditorView> {
	}

	@UiField(provided = true) EditorArea editorArea;
	@UiField Label status;

	@Inject
	public EditorView(EditorArea editorArea) {
		this.editorArea = editorArea;

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void replaceContent(String content) {
		editorArea.setText(content);
	}

	@Override
	public void updateStatus(String statusString) {
		status.setText(statusString);
	}
}
