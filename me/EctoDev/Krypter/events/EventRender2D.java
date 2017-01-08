package me.EctoDev.Krypter.events;

import com.darkmagician6.eventapi.events.Event;

public class EventRender2D implements Event{
	public int width,height;
	
	public EventRender2D(final int width,final int height){//TODO: guiingame ln 185 -> 187
		this.width = width;
		this.height = height;
	}
}
