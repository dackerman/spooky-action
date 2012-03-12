package com.dacklabs.spookyaction.client.wrappers;

import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class TextAreaWrapper implements TextArea {

	private final com.google.gwt.user.client.ui.TextArea wrapped;

	@Inject
	public TextAreaWrapper(com.google.gwt.user.client.ui.TextArea wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public int getCursorPos() {
		return wrapped.getCursorPos();
	}

	@Override
	public void setText(String content) {
		wrapped.setText(content);
	}

	@Override
	public <H extends EventHandler> void addDomHandler(H editorListener, Type<H> type) {
		wrapped.addDomHandler(editorListener, type);
	}

	@Override
	public void setStyleName(String styleName) {
		wrapped.setStyleName(styleName);
	}

	@Override
	public Widget asWidget() {
		return wrapped.asWidget();
	}
}
