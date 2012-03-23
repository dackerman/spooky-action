package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SaveButtonView extends Composite implements SaveButton.Display {

	private static SaveButtonViewUiBinder uiBinder = GWT.create(SaveButtonViewUiBinder.class);

	interface SaveButtonViewUiBinder extends UiBinder<Widget, SaveButtonView> {
	}

	interface ButtonCss extends CssResource {
		String greenButton();

		String yellowButton();

		String redButton();
	}

	@UiField ButtonCss style;
	@UiField Button saveButton;

	public SaveButtonView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setGreenText(String text) {
		saveButton.setStyleName(style.greenButton());
		saveButton.setText(text);
	}

	@Override
	public void setYellowText(String text) {
		saveButton.setStyleName(style.yellowButton());
		saveButton.setText(text);
	}

	@Override
	public void setRedText(String text) {
		saveButton.setStyleName(style.redButton());
		saveButton.setText(text);
	}

	@Override
	public void setClickHandler(ClickHandler handler) {
		saveButton.addClickHandler(handler);
	}

	@Override
	public void setEnabled(boolean enabled) {
		saveButton.setEnabled(enabled);
	}
}
