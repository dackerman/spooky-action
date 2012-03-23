package com.dacklabs.spookyaction.client.notifications;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class NotificationSystemView extends Composite implements NotificationSystem.Display {

	private static NotificationSystemViewUiBinder uiBinder = GWT.create(NotificationSystemViewUiBinder.class);

	interface NotificationSystemViewUiBinder extends UiBinder<Widget, NotificationSystemView> {
	}

	public interface NotificationStyle extends CssResource {
		String info();

		String error();
	}

	@UiField HTMLPanel notificationBar;
	@UiField NotificationStyle style;
	@UiField Label text;
	@UiField HTML details;
	@UiField Button detailsButton;
	@UiField Button closeButton;

	@Inject
	public NotificationSystemView() {
		initWidget(uiBinder.createAndBindUi(this));
		notificationBar.setVisible(false);
		details.setVisible(false);
	}

	@Override
	public void clear() {
		notificationBar.setVisible(false);
		details.setVisible(false);
		text.setText("");
		details.setText("");
	}

	@Override
	public void setMessage(String message) {
		notificationBar.setVisible(true);
		text.setText(message);
	}

	@Override
	public void setDetails(String details) {
		notificationBar.setVisible(true);
		this.details.setText("Details: " + details);
	}

	@Override
	public void setInfoStyle() {
		notificationBar.setVisible(true);
		notificationBar.removeStyleName(style.error());
		notificationBar.addStyleName(style.info());
	}

	@Override
	public void setErrorStyle() {
		notificationBar.setVisible(true);
		notificationBar.removeStyleName(style.info());
		notificationBar.addStyleName(style.error());
	}

	@Override
	public void showDetails() {
		details.setVisible(true);
	}

	@Override
	public void hideDetails() {
		details.setVisible(false);
	}

	@Override
	public HasClickHandlers closeButton() {
		return closeButton;
	}

	@Override
	public HasClickHandlers detailsButton() {
		return detailsButton;
	}
}
