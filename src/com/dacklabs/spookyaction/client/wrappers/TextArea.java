package com.dacklabs.spookyaction.client.wrappers;

import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

@ImplementedBy(TextAreaWrapper.class)
public interface TextArea extends IsWidget {

	int getCursorPos();

	void setText(String content);

	<H extends EventHandler> void addDomHandler(H editorListener, Type<H> type);

	void setStyleName(String styleName);
}
