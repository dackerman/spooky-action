package com.dacklabs.spookyaction.client.editor;

import com.dacklabs.spookyaction.client.wrappers.TextArea;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * The raw Editor
 * 
 * @author "David Ackerman (david.w.ackerman@gmail.com)"
 */
public class EditorArea implements IsWidget {

	private final TextArea display;

	@Inject
	public EditorArea(TextArea display, CommandGenerator editorListener) {
		this.display = display;

		display.addDomHandler(editorListener, KeyDownEvent.getType());
		display.addDomHandler(editorListener, ClickEvent.getType());
		editorListener.setEditor(this);
	}

	public void setStyleName(String styleName) {
		display.setStyleName(styleName);
	}

	public int cursorLocation() {
		return display.getCursorPos();
	}

	public void setText(String content) {
		display.setText(content);
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
