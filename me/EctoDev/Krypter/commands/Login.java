package me.EctoDev.Krypter.commands;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.jna.platform.win32.Advapi32Util.Account;

import me.EctoDev.Krypter.command.Command;
import me.EctoDev.Krypter.utils.Auth;
import net.minecraft.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

public class Login extends Command {

	@Override
	public String getAlias() {
		return "login";
	}

	@Override
	public String getDescription() {
		return "Logs into another account";
	}

	@Override
	public String getSyntax() {
		return "login <username> <password> || mcleaks <mcleaks token>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		String[] split = args[0].split(":");
		response(Auth.setSessionData(split[0], split[1]), args[0]);
	}
//		if (args.length > 0)
//			this.response(Auth.setSessionData(args[0], args.length == 2 ? args[1] : ""), args[0]);
//	}

	private void response(int code, String user) {
		String message = "";
		switch (code) {
		case 0: {
			message = "Authentication failed";
			break;
		}
		case 1: {
			message = "Authentication successful";
			break;
		}
		case 2: {
			message = "Username changed ( no legacy )";
		}
		}

	}

	public String slice_range(String s, int startIndex, int endIndex) {
		if (startIndex < 0)
			startIndex = s.length() + startIndex;
		if (endIndex < 0)
			endIndex = s.length() + endIndex;
		return s.substring(startIndex, endIndex);
	}

}
