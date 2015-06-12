package ttftcuts.morvyblox.shape;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class Raytracer {

	public static MovingObjectPosition traceBlock(World world, EntityPlayer player, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		Vec3 start = getHeadVector(player);
		Vec3 end = getReachVector(player);
		return block.collisionRayTrace(world, x, y, z, start, end);
	}
	
	public static MovingObjectPosition traceIntoMorvyBlox(World world, EntityPlayer player, int x, int y, int z) {
		return null;
	}
	
	public static Vec3 getHeadVector(EntityPlayer player) {
		Vec3 pos = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
		
		if (player.worldObj.isRemote) {
			pos.yCoord += player.getEyeHeight() - player.getDefaultEyeHeight();
		} else {
			pos.yCoord += player.getEyeHeight();
			if (player instanceof EntityPlayerMP && player.isSneaking()) {
				pos.yCoord -= 0.08;
			}
		}
		return pos;
	}
	
	public static Vec3 getReachVector(EntityPlayer player) {
		Vec3 pos = getHeadVector(player);
		Vec3 look = player.getLook(1.0f);
		double reach = getReachLength(player);
		return pos.addVector(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach);
	}
	
	public static double getReachLength(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return Minecraft.getMinecraft().playerController.getBlockReachDistance();
		} else {
			if (player instanceof EntityPlayerMP) {
				return ((EntityPlayerMP)player).theItemInWorldManager.getBlockReachDistance();
			}
		}
		return 5.0;
	}
}
