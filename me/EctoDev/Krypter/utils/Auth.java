package me.EctoDev.Krypter.utils;

import java.net.Proxy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class Auth {
	public static void login(Account account) {
		Auth.setSessionData(account.email, account.password);
	}

	public static int setSessionData(String user, String pass) {
		if (pass.length() != 0) {
			YggdrasilAuthenticationService authentication = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
			YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) authentication
					.createUserAuthentication(Agent.MINECRAFT);
			auth.setUsername(user);
			auth.setPassword(pass);
			try {
				auth.logIn();
				Minecraft.getMinecraft().session = new Session(auth.getSelectedProfile().getName(),
						auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "legacy");
				return 1;
			} catch (Exception var4_4) {
				return 0;
			}
		}
		Minecraft.getMinecraft().session = new Session(user, "0", "0", "legacy");
		return 2;
	}

	static class Account {
		public static  List<Account> accounts = new CopyOnWriteArrayList<Account>();
		public String email;
		public String password;
		public String username;
		public ResourceLocation head;
		public boolean authenticating;

		public Account(String email, String password) {
			this.email = email;
			this.password = password;
			if (!email.contains("@")) {
				this.loadHead(email);
			}
		}

		public void loadHead(String username) {
			this.username = username;
			if (this.head == null) {
				this.head = new ResourceLocation("heads/" + username);
				ThreadDownloadImageData textureHead = new ThreadDownloadImageData(null,
						String.format("https://minotar.net/helm/%s/64.png", username), null, null);
				Minecraft.getMinecraft().getTextureManager().loadTexture(this.head, textureHead);
			}
		}
	}

}
