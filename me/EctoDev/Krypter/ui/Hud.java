package me.EctoDev.Krypter.ui;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EctoDev.Krypter.Krypter;
import me.EctoDev.Krypter.events.EventRender2D;
import me.EctoDev.Krypter.font.ttf.TTFManager;
import me.EctoDev.Krypter.module.Module;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;

public class Hud extends Module { 
static String[] var1 = {"Everything"};
	public Hud() {
		super("hud", -1, Keyboard.KEY_MINUS, Category.RENDER,var1);
	}
	public static Color rainbow(float offset){
		float hue = ((float) System.nanoTime() + offset) / 1.0e10F % 8.0f;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0f, 1.001f)).intValue()),16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0f,c.getGreen() / 255.0f,c.getBlue() / 255.0f,c.getAlpha() / 255.0f);
	}
	@Override
	public void onEnable() {
		EventManager.register(this);
		super.onEnable();
	}
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
	@EventTarget
	public void onRender2D(EventRender2D event){
		ScaledResolution sr = new ScaledResolution(Wrapper.getInstance().getMinecraft());
		boolean fps = false,coords = true,info = true, mods = true,tabgui = true;
		if(fps)
		drawFPS();
		if(coords)
		drawCoords(sr);
		if(info)
		drawInfo();
		if(mods)
		mods();
		if(tabgui)
		Krypter.getKrypter().getTabGui().drawTabGui();
		int count = 100;
		for (Object o : Wrapper.getInstance().getWorld().loadedEntityList) {
			EntityLivingBase e = (EntityLivingBase) o;

			if (e instanceof EntityLivingBase && !e.isDead && !(e instanceof EntityPlayerSP) && Wrapper.getInstance().getPlayer().getDistanceToEntity(e) < 10) {
				TTFManager.getInstance().getModListFont().drawString(Math.round(Wrapper.getInstance().getPlayer().getDistanceToEntity(e)) + " \2477-" + e.getName() + "  \247a"+ Math.round(e.posX) + " " + Math.round(e.posY) + " " + Math.round(e.posZ) + " \247c" + e.getHealth() + "/" + e.getMaxHealth(),1, count, -1);
				count+=10;
			}
		}
		
	}
	
	void drawFPS(){
		String FPS = "" + Wrapper.getInstance().getMinecraft().debugFPS;
		Display.setTitle("Krypter FPS: " + FPS);
//		TTFManager.getInstance().getStandardFont().drawString(FPS, 40, 1, -1);
	}
	void drawCoords(ScaledResolution p_175180_1){
		int s = Wrapper.getInstance().getCurrentDisplyedGuiScreen() instanceof GuiChat ? 25:10; 
		double[] coords = {Math.round(Wrapper.getInstance().getPlayer().posX),Math.round(Wrapper.getInstance().getPlayer().posY),Math.round(Wrapper.getInstance().getPlayer().posZ)};
	for(int i = 0; i < coords.length; i++){
		
		TTFManager.getInstance().getStandardFont().drawString(String.valueOf(coords[i]),1,p_175180_1.getScaledHeight() -s-10* i, -1);
		}
	}
	void drawInfo(){
		TTFManager.getInstance().getLogoMainFont().drawStringWithShadow("Krypter", 0, 1, 0xcc316ADC);
	}
	void mods(){
		int yCount = Wrapper.getInstance().getPlayer().getActivePotionEffects() != null ? 25 : 2;
		ScaledResolution var1 = new ScaledResolution(Wrapper.getInstance().getMinecraft());
		for(Module m : Krypter.getKrypter().getModuleManager().getMods()){
			int right = var1.getScaledWidth();
			if(m.isActive() && !m.isCategory(Category.GUI)){
				TTFManager.getInstance().getModListFont().drawStringWithShadow(m.getName(), right - TTFManager.getInstance().getModListFont().getWidth(m.getName()), yCount, rainbow(yCount * 9000000).hashCode());
				yCount+=10;
			}
		}
	}
	

}
