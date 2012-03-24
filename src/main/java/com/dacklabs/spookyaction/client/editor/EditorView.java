package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
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

	@UiField FocusPanel focusPanel;
	@UiField FlowPanel editorPanel;
	@UiField Image loadingImage;

	@Inject
	public EditorView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void addLine(int lineNumber, EditorLine line) {
		editorPanel.insert(line, lineNumber);
	}

	@Override
	public void removeLine(int lineNumber) {
		editorPanel.remove(lineNumber);
	}

	@Override
	public void clearWindow() {
		loadingImage.setVisible(false);
		editorPanel.clear();
	}

	@Override
	public void showLoading(String path) {
		editorPanel.clear();
		loadingImage.setVisible(true);
	}
}
