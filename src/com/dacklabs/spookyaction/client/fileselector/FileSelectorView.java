package com.dacklabs.spookyaction.client.fileselector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FileSelectorView extends Composite implements FileSelector.Display {

	private static FileSelectorViewUiBinder uiBinder = GWT.create(FileSelectorViewUiBinder.class);

	interface FileSelectorViewUiBinder extends UiBinder<Widget, FileSelectorView> {
	}

	@UiField InputElement pathSelector;
	@UiField Button submitButton;
	@UiField SpanElement loadingText;

	public FileSelectorView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String currentPathText() {
		return pathSelector.getValue();
	}

	@Override
	public void onFileRequested(ClickHandler clickHandler) {
		submitButton.addClickHandler(clickHandler);
	}

	@Override
	public void showLoadingMessage() {
		loadingText.setAttribute("style", "display:inherit");
	}

	@Override
	public void hideLoadingMessage() {
		loadingText.setAttribute("style", "display:none");
	}
}
