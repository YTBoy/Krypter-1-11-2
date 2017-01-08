package me.EctoDev.Krypter.module.modules;

import com.darkmagician6.eventapi.EventTarget;

import me.EctoDev.Krypter.events.EventPostMotionUpdates;
import me.EctoDev.Krypter.events.EventPreMotionUpdates;
import me.EctoDev.Krypter.module.Module;
import me.EctoDev.Krypter.module.Module.Category;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.optifine.MathUtils;

public class TomaterHop extends Module {
	private double speed;
	private int stage;
	private boolean disabling;
	private boolean stopMotionUntilNext;
	private double moveSpeed;
	private boolean spedUp;
	public static boolean canStep;
	private double lastDist;
	public static double yOffset;
	private boolean cancel;
	Wrapper wrapper = new Wrapper();

	public TomaterHop() {
		super("Hop", 0, 0, Category.MOVEMENT, new String[] { "Nufin" });
		this.speed = 0.07999999821186066D;
	}

	public void onEnable() {
		net.minecraft.util.Timer.timerSpeed = 1.0F;
		this.cancel = false;
		this.stage = 1;
		this.moveSpeed = (wrapper.mc.player == null ? 0.2873D : getBaseMoveSpeed());
		if (!this.disabling) {
			super.onEnable();
		}
	}

	@EventTarget
	public void onPreMotion(EventPostMotionUpdates event) {
		net.minecraft.util.Timer.timerSpeed = 1.0F;
		if (Math.round(wrapper.mc.player.posY - (int) wrapper.mc.player.posY) == Math.round(0.138D)) {
			EntityPlayerSP thePlayer = wrapper.mc.player;
			event.y -= 0.09316090325960147D;
			EntityPlayerSP thePlayer2 = wrapper.mc.player;
			thePlayer2.posY -= 0.09316090325960147D;
		}
		if ((this.stage == 1)
				&& ((wrapper.mc.player.moveForward != 0.0F) || (wrapper.mc.player.moveStrafing != 0.0F))) {
			this.stage = 2;
			this.moveSpeed = (1.38D * getBaseMoveSpeed() - 0.01D);
		} else if (this.stage == 2) {
			this.stage = 3;
			wrapper.mc.player.motionY = 0.399399995803833D;
			event.y = 0.399399995803833D;
			this.moveSpeed *= 2.149D;
		} else if (this.stage == 3) {
			this.stage = 4;
			double difference = 0.66D * (this.lastDist - getBaseMoveSpeed());
			this.moveSpeed = (this.lastDist - difference);
		} else {

			this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
		}
		this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
		MovementInput movementInput = wrapper.mc.player.movementInput;
		float forward = movementInput.moveForward;
		float strafe = movementInput.moveStrafe;
		float yaw = Minecraft.getMinecraft().player.rotationYaw;
		if ((forward == 0.0F) && (strafe == 0.0F)) {
		} else if (forward != 0.0F) {
			if (strafe >= 1.0F) {
				yaw += (forward > 0.0F ? -45 : 45);
				strafe = 0.0F;
			} else if (strafe <= -1.0F) {
				yaw += (forward > 0.0F ? 45 : -45);
				strafe = 0.0F;
			}
			if (forward > 0.0F) {
				forward = 1.0F;
			} else if (forward < 0.0F) {
				forward = -1.0F;
			}
		}
		double mx = Math.cos(Math.toRadians(yaw + 90.0F));
		double mz = Math.sin(Math.toRadians(yaw + 90.0F));
		double motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
		double motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
		// event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed *
		// mz);
		// event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed *
		// mx);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates pre) {
		String currentMode = "";

		double xDist = wrapper.mc.player.posX - wrapper.mc.player.prevPosX;
		double zDist = wrapper.mc.player.posZ - wrapper.mc.player.prevPosZ;
		this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
	}

	private double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		return baseSpeed;
	}

	public void onDisable() {
		net.minecraft.util.Timer.timerSpeed = 1.0F;
		this.moveSpeed = getBaseMoveSpeed();

		yOffset = 0.0D;
		this.stage = 0;
		this.disabling = false;
		super.onDisable();
	}
}
