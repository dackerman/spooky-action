package com.dacklabs.spookyaction.client.stats;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StatsViewerView extends Composite implements StatsViewer.Display {

	private static StatsViewerViewUiBinder uiBinder = GWT.create(StatsViewerViewUiBinder.class);

	interface StatsViewerViewUiBinder extends UiBinder<Widget, StatsViewerView> {
	}

	@UiField SpanElement numCommands;
	@UiField SpanElement offset;
	@UiField SpanElement type;
	@UiField SpanElement data;
	@UiField SpanElement repeated;
	@UiField SpanElement line;

	public StatsViewerView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setNumberOfCommandsInQueue(int number) {
		set(this.numCommands, String.valueOf(number));
	}

	@Override
	public void setCommand(int offset, String type, String data, int repeated, int line) {
		set(this.offset, String.valueOf(offset));
		set(this.type, type);
		set(this.data, data);
		set(this.repeated, String.valueOf(repeated));
		set(this.line, String.valueOf(line));
	}

	private void set(SpanElement element, String data) {
		element.setInnerText(data);
	}
}
