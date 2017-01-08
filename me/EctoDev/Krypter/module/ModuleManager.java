package me.EctoDev.Krypter.module;

import java.util.ArrayList;

import me.EctoDev.Krypter.module.modules.ESP;
import me.EctoDev.Krypter.module.modules.ElytraFly;
import me.EctoDev.Krypter.module.modules.Killaura;
import me.EctoDev.Krypter.module.modules.NoSlowdown;
import me.EctoDev.Krypter.module.modules.Nuker;
import me.EctoDev.Krypter.module.modules.Speed;
import me.EctoDev.Krypter.module.modules.TomaterHop;
import me.EctoDev.Krypter.module.modules.Velocity;
import me.EctoDev.Krypter.ui.Hud;

public class ModuleManager {

	public static ArrayList<Module> mods = new ArrayList<Module>();

	public void addModules() {
		this.mods.add(new Hud());
		this.mods.add(new Nuker());
		this.mods.add(new Speed());
		this.mods.add(new Killaura());
		this.mods.add(new ElytraFly());
this.mods.add(new TomaterHop());
this.mods.add(new Velocity());
this.mods.add(new NoSlowdown());
this.mods.add(new ESP());
	}

	public static ArrayList<Module> getMods() {
		return mods;
	}

	public Module getModule(Class<? extends Module> clas) {
		for (Module m : getMods()) {
			if (m.getClass() == clas) {
				return m;
			}
		}
		return null;
	}

	public void setModuleState(String modName, boolean state) {
		for (Module m : getMods()) {
			if (m.getName().equalsIgnoreCase(modName.trim())) {
				m.setActive(state);
			}
		}
	}
}
