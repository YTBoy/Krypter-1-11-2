package me.EctoDev.Krypter.module.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EctoDev.Krypter.events.EventUpdate;
import me.EctoDev.Krypter.module.Module;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.math.MathHelper;

public class Speed extends Module {

	public Speed() {
		super("speed", 0, 0, Category.MOVEMENT, new String[] { "none" });
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
		if (!this.isActive()) {
			return;
		}
		
		String mode = "onGround";
		setName("speed \2477 - " + mode + " ");
		// onGround
		if (mode == "onGround") {
			if (Wrapper.getInstance().getPlayer().onGround) {
				if (Wrapper.getInstance().getGameSettings().keyBindForward.isKeyDown()) {
					Wrapper.getInstance().getPlayer().jump();
					Wrapper.getInstance().getPlayer().motionY /= 8000000;
					  float f = Wrapper.getInstance().getPlayer().rotationYaw * 0.017453292F;
			            Wrapper.getInstance().getPlayer().motionX -= (double)(MathHelper.sin(f) * 4);
			            Wrapper.getInstance().getPlayer().motionZ += (double)(MathHelper.cos(f) * 4);
					}
			}
		}
		// bhop
		if(mode == "bhop" && Wrapper.getInstance().getPlayer().onGround){
				Wrapper.getInstance().getPlayer().jump();
				Wrapper.getInstance().getPlayer().jumpMovementFactor = 0.4f;
				
		}
	}
}
