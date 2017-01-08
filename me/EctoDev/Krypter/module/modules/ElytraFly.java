package me.EctoDev.Krypter.module.modules;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EctoDev.Krypter.events.EventUpdate;
import me.EctoDev.Krypter.module.Module;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.util.math.MathHelper;

public class ElytraFly extends Module {

	public ElytraFly() {
		super("ElytraFly", 0, Keyboard.KEY_G, Category.MOVEMENT, new String[] { "Vanilla" });
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
	public void onUpdate(EventUpdate event) {
		float speed = 7;
		if (isActive()) {
			
			if (Wrapper.getInstance().getPlayer().isElytraFlying()) {
				
				if (Wrapper.getInstance().getPlayer().rotationPitch < 90) {
					Wrapper.getInstance().getPlayer().motionY = (-Wrapper.getInstance().getPlayer().rotationPitch / 70);
		            float f = Wrapper.getInstance().getPlayer().rotationYaw * 0.017453292F;
		            Wrapper.getInstance().getPlayer().motionX -= (double)(MathHelper.sin(f) * (speed / 60));
		            Wrapper.getInstance().getPlayer().motionZ += (double)(MathHelper.cos(f) * (speed / 60));
				}
//				else{
//					
//				}
			}
		}
	}
}
