package com.dacklabs.spookyaction.client.notifications;

import com.dacklabs.spookyaction.client.events.ErrorEvent;
import com.dacklabs.spookyaction.client.events.ErrorEventHandler;
import com.dacklabs.spookyaction.client.events.InfoEvent;
import com.dacklabs.spookyaction.client.events.InfoEventHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

public class NotificationSystem implements ErrorEventHandler, InfoEventHandler, IsWidget {

	private static final String GENERIC_ERROR = "An error occurred. Sorry, that's all the information I have.";

	private final Display display;

	@ImplementedBy(NotificationSystemView.class)
	public interface Display extends IsWidget {

		/**
		 * Sets an informational message in the notification area.
		 */
		void setMessage(String message);

		/**
		 * Sets the details to be displayed.
		 */
		void setDetails(String secondaryText);

		/**
		 * Shows the details text.
		 */
		void showDetails();

		/**
		 * Hides the details text.
		 */
		void hideDetails();

		/**
		 * Sets the current style to info.
		 */
		void setInfoStyle();

		/**
		 * Sets the current style to error.
		 */
		void setErrorStyle();

		/**
		 * Clears any existing notifications.
		 */
		void clear();

		HasClickHandlers closeButton();

		HasClickHandlers detailsButton();
	}

	private boolean detailsShowing = false;

	@Inject
	NotificationSystem(Display display, EventBus eventBus) {
		this.display = display;

		eventBus.addHandler(ErrorEvent.TYPE, this);
		eventBus.addHandler(InfoEvent.TYPE, this);

		display.closeButton().addClickHandler(new OnCloseHandler());
		display.detailsButton().addClickHandler(new OnDetailsHandler());
	}

	@Override
	public void onInfo(InfoEvent info) {
		display.clear();
		display.setMessage(info.message());
		display.setInfoStyle();
	}

	@Override
	public void onError(ErrorEvent error) {
		display.clear();
		if (error.hasMessage()) {
			display.setMessage(error.message());
		} else {
			display.setMessage(GENERIC_ERROR);
		}
		if (error.hasThrowable()) {
			display.setDetails(error.throwable().getMessage());
		}
		display.setErrorStyle();
	}

	/**
	 * Handles the clicking of the "details" button.
	 */
	private class OnDetailsHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (detailsShowing) {
				display.hideDetails();
			} else {
				display.showDetails();
			}
			detailsShowing = !detailsShowing;
		}
	}

	/**
	 * Handles the clicking of the "X" button.
	 */
	private class OnCloseHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			display.clear();
		}
	}

	@Override
	public Widget asWidget() {
		return display.asWidget();
	}
}
