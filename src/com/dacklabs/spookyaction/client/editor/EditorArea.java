package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.ui.client.adapters.ValueBoxEditor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class EditorArea implements IsWidget {

	@ImplementedBy(TextArea.class)
	public interface Display extends IsWidget, IsEditor<ValueBoxEditor<String>> {
	}

	private final TextArea display;

	@Inject
	public EditorArea(TextArea display) {
		this.display = display;
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}

	public void setText(String content) {
		display.setText(content);
	}
}
