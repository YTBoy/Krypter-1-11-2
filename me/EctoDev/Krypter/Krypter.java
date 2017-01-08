package me.EctoDev.Krypter;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EctoDev.Krypter.command.CommandManager;
import me.EctoDev.Krypter.events.EventKeyboard;
import me.EctoDev.Krypter.module.Module;
import me.EctoDev.Krypter.module.ModuleManager;
import me.EctoDev.Krypter.ui.TabGui;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class Krypter {

	// info
	private final String CLIENT_NAME = "Krypter";
	private final double CLIENT_VERSION = 0.1;
	private final String[] CLIENT_DEVS = { "YTBoy" };
	//
	private static ModuleManager moduleManager = null;
	private static TabGui tabGui = null;
	private static CommandManager cmdManager = null;
    public GuiIngame ingameGUI = new GuiIngame(Wrapper.getInstance().getMinecraft());
	GuiNewChat guinewchat = ingameGUI.getChatGUI();

	// getters and setters
	public String getCLIENT_NAME() {
		return CLIENT_NAME;
	}

	public GuiChat getGuiChat() {
		return getGuiChat();
	}

	public double getCLIENT_VERSION() {
		return CLIENT_VERSION;
	}

	public String[] getCLIENT_DEVS() {
		return CLIENT_DEVS;
	}

	// instances
	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public TabGui getTabGui() {
		return tabGui;
	}

	private static Krypter theClient = new Krypter();

	public static Krypter getKrypter() {
		return theClient;
	}

	// methods
	public void initClient() {
		cmdManager = new CommandManager();
		EventManager.register(this);
		moduleManager = new ModuleManager();
		moduleManager.addModules();
		tabGui = new TabGui();
		if (Wrapper.getInstance().getWorld() != null) {
			moduleManager.setModuleState("Hud", false);
		} else {
			moduleManager.setModuleState("Hud", true);
		}

	}

	@EventTarget
	public void onKeyboard(EventKeyboard event) {
		tabGui.actionEvent(event.getKey());
		for (Module m : this.getModuleManager().getMods()) {
			if (event.getKey() == m.getBind()) {
				m.toggleModule();
			}
		}
	}

	public static void addChatMessage(String s) {
		GuiNewChat guinewchat = Krypter.getKrypter().ingameGUI.getChatGUI();
		// Minecraft.getMinecraft().thePlayer.addChatMessage(new
		// ChatComponentText("Spyke: " + s));
        guinewchat.printChatMessage(new TextComponentTranslation(s));

	}

	public static boolean onSendChatMessage(String s) {// EntityPlayerSP
		if (s.startsWith(".")) {
			cmdManager.callCommand(s.substring(1));
			return false;
		}
		for (Module m : Krypter.getKrypter().getModuleManager().mods) {
			if (m.isActive()) {
				return m.onSendChatMessage(s);
			}
		}
		return true;
	}

	public static boolean onRecieveChatMessage(SPacketChat packet) {
		for (Module m : Krypter.getKrypter().getModuleManager().getMods()) {
			if (m.isActive()) {
				return m.onRecieveChatMessage(packet);
			}
		}
		return true;
	}

}
