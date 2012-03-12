package com.dacklabs.spookyaction.client.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CurrentDiffView extends Composite {

	private static CurrentDiffViewUiBinder uiBinder = GWT
			.create(CurrentDiffViewUiBinder.class);

	interface CurrentDiffViewUiBinder extends UiBinder<Widget, CurrentDiffView> {
	}
	
	@UiField DivElement diff;
	
	public CurrentDiffView() {
		uiBinder.createAndBindUi(this);
	}
	
	public void showNewDiff(String diffString) {
		diff.setInnerHTML(diffString);
	}
}
