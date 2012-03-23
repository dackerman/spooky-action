package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;

/**
 * The GWT components that make up a single line in the editor window.
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class EditorLineView extends TextBox implements EditorLine.Display {

	@Inject
	public EditorLineView() {
		super.setStyleName("textBoxLine");
	}

	@Override
	public String getHTML() {
		return super.getText();
	}

	@Override
	public void setHTML(String html) {
		super.setText(html);
	}
}
