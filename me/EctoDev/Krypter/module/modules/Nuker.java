package me.EctoDev.Krypter.module.modules;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EctoDev.Krypter.events.EventUpdate;
import me.EctoDev.Krypter.module.Module;
import me.EctoDev.Krypter.wrapper.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class Nuker extends Module {
	static int radius = 9;
	int xPos;
	int yPos;
	int zPos;

	static String var1[] = { "nothing" };

	public Nuker() {
		super("Eggaura", 0, Keyboard.KEY_V, Category.PLAYER, var1);
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
		if (isActive()) {
			for (int x = (int) -5.2; x <  5.2; x++) {
			for (int y = (int) 5.2;  y > -5.2; y--) {
			for (int z = (int) -5.2; z <  5.2; z++) {
						this.xPos = (int) (Wrapper.getInstance().getPlayer().posX + x);
						this.yPos = (int) (Wrapper.getInstance().getPlayer().posY + y);
						this.zPos = (int) (Wrapper.getInstance().getPlayer().posZ + z);
					


						BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
						Block block = Wrapper.getInstance().getMinecraft().world.getBlockState(blockPos).getBlock();
						if (block.getMaterial(null) != Material.DRAGON_EGG  && block.getMaterial(null) != Material.CACTUS)
							continue;
						Wrapper.getInstance().getPlayer().connection.sendPacket(
								new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
						Wrapper.getInstance().getPlayer().connection.sendPacket(
								new CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
//						Wrapper.getInstance().getPlayer().connection
//						.sendPacket(new CPacketPlayer.Position(blockPos.getX(), blockPos.getY() + 2, blockPos.getZ(), true));
						Wrapper.getInstance().getPlayer().swingArm(EnumHand.MAIN_HAND);
					//	Wrapper.getInstance().getPlayer().setPosition(blockPos.getX(), blockPos.getY() + 2, blockPos.getZ());
//						Wrapper.getInstance().getPlayer().motionX = 0;
//						Wrapper.getInstance().getPlayer().motionY = 0;
//						Wrapper.getInstance().getPlayer().motionZ = 0;
						// Wrapper.getInstance().sendPackets(new
						// CPacketPlayerDigging(Action.START_DESTROY_BLOCK,
						// blockPos, EnumFacing.NORTH));
						// Wrapper.getInstance().sendPackets(new
						// CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK,
						// blockPos, EnumFacing.NORTH));
						setName("Eggaura" + " [Cubecraft]");
						
					}
				}
			}
		}

	}
}