

package me.EctoDev.Krypter.ui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.EctoDev.Krypter.Krypter;
import me.EctoDev.Krypter.font.ttf.TTFManager;
import me.EctoDev.Krypter.module.Module;
import me.EctoDev.Krypter.module.Module.Category;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.client.gui.Gui;




public class TabGui extends Gui {

	public ArrayList<String> mods = new ArrayList();
	public ArrayList<String> category = new ArrayList();

	public int selectedMod, selectedTab;

	public int start, start1 = start + 14, end;
	public static int screen = 0;

	public TabGui() {
		Category[] arrayofCategorys;
		int j = (arrayofCategorys = Category.values()).length;

		for (int i = 0; i < j; i++) {
			Category category = arrayofCategorys[i];

			if (category.name().equalsIgnoreCase("Gui")) {
				continue;
			}

			this.category.add(category.toString().substring(0, 1)
					+ category.toString().substring(1, category.toString().length()).toLowerCase());
		}
	}

	public void drawTabGui() {

		int count = 0;

		for (Category c : Category.values()) {
			if (!c.name().equalsIgnoreCase("Gui")) {
				int y = 40 + (count * 15);
				int yZ = 52 + ((count + 1) * 10);
				drawRect(0, y, 102, y + 15,
						
						!c.name().equalsIgnoreCase(category.get(this.selectedTab)) ? 0x800d0d0d : 0xee1F46DF);
				count++;
			}
		}

		int categoryCount = 0;

		for (String s : this.category) {
			TTFManager.getInstance().getTabGuiFont().drawString(s, 1, 40 + categoryCount * 15, -1);
			categoryCount++;
		}

		if (screen == 1) {
			int modCount = 0;

			for (Module mod : getModsForCategory()) {

				String color;
				if (mod.isActive()) {
					color = "\247f";
				} else {
					color = "\2477";
				}
				int selectedcolor = 0x80000000;
				int unslelectedcolor = 0xee1F46DF;
				String name = color + mod.getName();
				int y = 40 + (modCount * 15);
				int yZ = 62 + ((modCount + 1) * 13);
				drawRect(102, y, 150 + this.getLongestModWidth(), y + 15,
						!mod.getName().equalsIgnoreCase(this.getModsForCategory().get(this.selectedMod).getName())
								? selectedcolor : unslelectedcolor);
				
															// 0xeeF8253F
				// GuiUtils.drawBorderRect(102, y, 130 +
				// this.getLongestModWidth(), y + 15,
				// !mod.getName().equalsIgnoreCase(category.get(this.selectedTab))
				// ? 0x800d0d0d : 0x800d0d0d,
				// !mod.getName().equalsIgnoreCase(category.get(this.selectedTab))
				// ? 0xeeF8253F : 0x90000000, 1);
				TTFManager.getInstance().getTabGuiFont().drawString(name, 105, y + 4, 0xFFFFFFFF);

				modCount++;
			}
		}

	}

	private void up() {
		if (screen == 0) {
			if (this.selectedTab <= 0) {
				this.selectedTab = this.category.size();
			}
			this.selectedTab -= 1;
		} else if (screen == 1) {
			if (this.selectedMod <= 0) {
				this.selectedMod = getModsForCategory().size();
			}

			this.selectedMod -= 1;
		}
	}

	private void down() {
		if (screen == 0) {
			if (this.selectedTab >= this.category.size() - 1) {
				this.selectedTab = -1;
			}
			this.selectedTab += 1;
		} else if (screen == 1) {
			if (this.selectedMod >= getModsForCategory().size() - 1) {
				this.selectedMod = -1;
			}

			this.selectedMod += 1;
		}
	}

	private void left() {
		if (screen == 1) {
			screen = 0;
		}
	}

	private void right() {
		if (screen == 1) {
			((Module) getModsForCategory().get(this.selectedMod)).toggleModule();
		} else {
			if (screen == 0) {
				screen = 1;
				this.selectedMod = 0;
			}
		}
	}

	private void enter() {
		if (screen == 1) {
			((Module) getModsForCategory().get(this.selectedMod)).toggleModule();
		}
	}

	public void actionEvent(final int key) {

		if (key == Keyboard.KEY_UP) {
			this.up();
		}

		if (key == Keyboard.KEY_DOWN) {
			this.down();
		}

		if (key == Keyboard.KEY_LEFT) {
			this.left();
		}

		if (key == Keyboard.KEY_RIGHT) {
			this.right();
		}

		if (key == Keyboard.KEY_RETURN) {
			this.enter();
		}
	}

	private ArrayList<Module> getModsForCategory() {
		ArrayList<Module> modss = new ArrayList<Module>();

		for (Module mod : Krypter.getKrypter().getModuleManager().getMods()) {
			if (mod.getCategory() == Category.valueOf((String) this.category.get(this.selectedTab).toUpperCase())) {
				modss.add(mod);
			}
		}

		return modss;
	}

	private int getLongestModWidth() {
		int longest = 0;

		for (Module mod : getModsForCategory()) {
			if (TTFManager.getInstance().getTabGuiFont().getWidth(mod.getName()) + 3 > longest) {
				longest = (int) TTFManager.getInstance().getTabGuiFont().getWidth(mod.getName()) + 3;
			}
		}

		return longest;
	}

}