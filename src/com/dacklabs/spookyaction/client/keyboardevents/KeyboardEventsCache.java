package com.dacklabs.spookyaction.client.keyboardevents;

import java.util.ArrayDeque;
import java.util.Queue;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

public class KeyboardEventsCache implements KeyDownHandler {

	Queue<String> cache = new ArrayDeque<String>();

	@Override
	public void onKeyDown(KeyDownEvent event) {
		cache.add(String.valueOf(event.getNativeKeyCode()));
	}

	public Iterable<String> keysTyped() {
		return cache;
	}
}
