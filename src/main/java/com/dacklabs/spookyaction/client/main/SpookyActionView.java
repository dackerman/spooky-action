package com.dacklabs.spookyaction.client.main;

import com.dacklabs.spookyaction.client.editor.Editor;
import com.dacklabs.spookyaction.client.editor.SaveButton;
import com.dacklabs.spookyaction.client.fileselector.FileSelector;
import com.dacklabs.spookyaction.client.notifications.NotificationSystem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SpookyActionView extends Composite implements SpookyAction.Display {

	private static SpookyActionViewUiBinder uiBinder = GWT.create(SpookyActionViewUiBinder.class);

	interface SpookyActionViewUiBinder extends UiBinder<Widget, SpookyActionView> {
	}

	@UiField(provided = true) final FileSelector fileSelector;
	@UiField(provided = true) final Editor editor;
	@UiField(provided = true) final SaveButton saveButton;
	@UiField(provided = true) final NotificationSystem notificationSystem;

	@Inject
	public SpookyActionView(FileSelector fileSelector, Editor editor, SaveButton saveButton,
	    NotificationSystem notificationSystem) {
		this.fileSelector = fileSelector;
		this.editor = editor;
		this.saveButton = saveButton;
		this.notificationSystem = notificationSystem;

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Editor getEditor() {
		return editor;
	}

	@Override
	public void setFocusOnTextBox() {
		fileSelector.focus();
	}
}
