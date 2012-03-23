package com.dacklabs.spookyaction.client.notifications;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class SyncStatusView extends Composite implements SyncStatus.Display {

	private static SyncStatusViewUiBinder uiBinder = GWT.create(SyncStatusViewUiBinder.class);

	interface SyncStatusViewUiBinder extends UiBinder<Widget, SyncStatusView> {
	}

	public interface Style extends CssResource {

		String statusContainer();

		String healthy();

		String gettingBehind();

		String unhealthy();
	}

	@UiField HTMLPanel statusContainer;
	@UiField SpanElement statusText;
	@UiField Style style;

	public SyncStatusView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setText(String text) {
		statusText.setInnerText(text);
	}

	@Override
	public void setHealthy() {
		statusContainer.setStyleName(style.statusContainer());
		statusContainer.addStyleName(style.healthy());
	}

	@Override
	public void setWarning() {
		statusContainer.setStyleName(style.statusContainer());
		statusContainer.addStyleName(style.gettingBehind());
	}

	@Override
	public void setUnhealthy() {
		statusContainer.setStyleName(style.statusContainer());
		statusContainer.addStyleName(style.unhealthy());
	}
}
