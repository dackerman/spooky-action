package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
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

	@UiField(provided = true) EditorArea editorArea;
	@UiField(provided = true) CommandStatus status;

	@Inject
	public EditorView(EditorArea editorArea, CommandStatus status) {
		this.editorArea = editorArea;
		this.status = status;

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setEditorContent(String content) {
		editorArea.setText(content);
	}
}
