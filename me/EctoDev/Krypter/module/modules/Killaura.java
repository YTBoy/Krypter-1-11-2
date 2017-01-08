package me.EctoDev.Krypter.module.modules;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EctoDev.Krypter.events.EventUpdate;
import me.EctoDev.Krypter.module.Module;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

public class Killaura extends Module {
	int delay;

	static String[] var1 = { "\247aNCP", "\247cAAC" };

	public Killaura() {
		super("Killaura", 0, Keyboard.KEY_R, Category.COMBAT, var1);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (!this.isActive()) {
			return;
		}
		for (Object o : Wrapper.getInstance().getWorld().loadedEntityList) {
			Entity e = (Entity) o;

			if (e instanceof EntityLivingBase && !e.isDead && !(e instanceof EntityPlayerSP)
					&& ((EntityLivingBase) e).hurtTime < 0.0000000000000000000000000000001) {

				if (Wrapper.getInstance().getPlayer().getDistanceToEntity(e) < 8) {

					float rotations[] = getRotationsNeeded(e);
					float yaw = rotations[0];
					float pitch = rotations[1];

					if (Wrapper.getInstance().getPlayer().swingProgress < 0.9) {
						

								Wrapper.getInstance().getPlayer().connection
										.sendPacket(new CPacketPlayer.Position(e.posX, e.posY + 3, e.posZ, true));
								Wrapper.getInstance().getPlayer().connection
										.sendPacket(new CPacketUseEntity(e, EnumHand.MAIN_HAND));
								Wrapper.getInstance().getPlayerContoller()
										.attackEntity(Wrapper.getInstance().getPlayer(), e);
								Wrapper.getInstance().getPlayer().swingArm(EnumHand.MAIN_HAND);
								// mc.thePlayer.swingArm(EnumHand.OFF_HAND);
								delay = 0;

							
						
					}

				}
			}
		}

	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
	}

	@Override
	public void onToggle() {
	}

	public static synchronized void faceEntity(EntityLivingBase entity) {
		final float[] rotations = getRotationsNeeded(entity);

		if (rotations != null) {
			Wrapper.getInstance().getPlayer().rotationYaw = rotations[0];
			Wrapper.getInstance().getPlayer().rotationPitch = rotations[1] + 1.0F;// 14
		}
	}

	public static float[] getRotationsNeeded(Entity entity) {
		if (entity == null) {
			return null;
		}

		final double diffX = entity.posX - Wrapper.getInstance().getPlayer().posX;
		final double diffZ = entity.posZ - Wrapper.getInstance().getPlayer().posZ;
		double diffY;

		if (entity instanceof EntityLivingBase) {
			final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight()
					- (Wrapper.getInstance().getPlayer().posY + Wrapper.getInstance().getPlayer().getEyeHeight());
		} else {
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
					- (Wrapper.getInstance().getPlayer().posY + Wrapper.getInstance().getPlayer().getEyeHeight());
		}

		final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[] {
				Wrapper.getInstance().getPlayer().rotationYaw
						+ MathHelper.wrapAngleTo180_float(yaw - Wrapper.getInstance().getPlayer().rotationYaw),
				Wrapper.getInstance().getPlayer().rotationPitch
						+ MathHelper.wrapAngleTo180_float(pitch - Wrapper.getInstance().getPlayer().rotationPitch) };
	}

}
