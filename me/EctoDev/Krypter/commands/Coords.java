package me.EctoDev.Krypter.commands;

import me.EctoDev.Krypter.Krypter;
import me.EctoDev.Krypter.command.Command;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Coords extends Command {

	@Override
	public String getAlias() {
		return "coords";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getSyntax() {
		return "coords player-name";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		for (Object o : Wrapper.getInstance().getWorld().loadedEntityList) {
			Entity e = (Entity) o;

			if (e instanceof EntityLivingBase && !e.isDead && !(e instanceof EntityPlayerSP)) {
				Krypter.addChatMessage(e.getName() + " " + e.posX + " " + e.posY + " " + e.posY);
			}
		}
	}

}
