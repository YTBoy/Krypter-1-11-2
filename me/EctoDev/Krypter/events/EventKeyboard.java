package me.EctoDev.Krypter.events;

import com.darkmagician6.eventapi.events.Event;

public class EventKeyboard implements Event {
	public int key;

	public EventKeyboard(final int key) {
		this.key = key;

	}

	public int getKey() {
		return key;
	}
	
}
