package com.dacklabs.spookyaction.client.main;

import com.dacklabs.spookyaction.client.editor.Editor;
import com.dacklabs.spookyaction.client.fileselector.FileSelector;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class SpookyActionView extends Composite {

	private static SpookyActionViewUiBinder uiBinder = GWT.create(SpookyActionViewUiBinder.class);

	interface SpookyActionViewUiBinder extends UiBinder<Widget, SpookyActionView> {
	}

	@UiField(provided = true) FileSelector fileSelector;
	@UiField(provided = true) Editor editor;

	@Inject
	public SpookyActionView(FileSelector fileSelector, Editor editor) {
		this.fileSelector = fileSelector;
		this.editor = editor;

		initWidget(uiBinder.createAndBindUi(this));
	}
}
