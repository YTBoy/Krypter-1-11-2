package me.EctoDev.Krypter.module;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.network.play.server.SPacketChat;

public class Module {
	public enum Category {
		COMBAT, GUI, RENDER, MOVEMENT, PLAYER
	}

	private String name;
	private String[] bypasses;
	private Category category;
	int color, bind;
	private boolean isEnabled;

	public Module(String name, int color, int bind, Category category, String[] bypasses) {
		this.name = name;
		this.color = color;
		this.bind = bind;
		this.category = category;
		this.bypasses = bypasses;
	}

	public int getBind() {
		return bind;
	}

	public void setBind(int bind) {
		this.bind = bind;
	}

	public String getName() {
		return name;
	}

	public String[] getBypasses() {
		return bypasses;
	}

	public Category getCategory() {
		return category;
	}

	public int getColor() {
		return color;
	}

	public boolean isActive() {
		return isEnabled;
	}

	public void toggleModule() {
		this.setActive(!this.isActive());
	}

	public void onToggle() {
	}

	public void onEnable() {
		Random randomColor = new Random();
		StringBuilder sb = new StringBuilder();
		sb.append("0x");
		while (sb.length() < 10) {
			sb.append(Integer.toHexString(randomColor.nextInt()));
		}
		sb.setLength(8);
		this.color = Integer.decode(sb.toString()).intValue();
	}

	public void onDisable() {
	}

	public final boolean isCategory(Category c) {
		if (c == category) {
			return true;
		}
		return false;
	}

	public void setActive(boolean a) {
		this.isEnabled = a;
		onToggle();
		if (isEnabled) {
			onEnable();
		} else {
			onDisable();
		}
		// TODO: file manager
	}
	public void setName(String name){
		this.name = name;
		
	}
	public boolean onSendChatMessage(String s){
		return true;
	}
	
	public boolean onRecieveChatMessage(SPacketChat packet){
		return true;
	}
	
}
