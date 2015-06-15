package ttftcuts.morvyblox.shape;

import java.util.ArrayList;
import java.util.List;

import ttftcuts.morvyblox.block.TilePart;
import ttftcuts.morvyblox.block.TilePart.Part;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
	
	public static PartHit traceIntoMorvyBlox(World world, EntityPlayer player, int x, int y, int z) {
		TileEntity wtile = world.getTileEntity(x, y, z);
		TilePart tile = (TilePart)wtile;
		if (tile == null) { return null; }
		List<PartHit> hits = new ArrayList<PartHit>();
		
		Vec3 start = getHeadVector(player);
		Vec3 end = getReachVector(player);
		
		MovingObjectPosition pos;
		for(Part part : tile.parts) {
			pos = traceAABB(world,x,y,z, start, end, part.aabb);
			if (pos != null) {
				hits.add(new PartHit(pos, part));
			}
		}
		
		if (hits.size() == 0) {
			return null;
		}
		
		PartHit closest = null;
		double length = Double.MAX_VALUE;
		
		for (int i=0; i<hits.size(); i++) {
			PartHit p = hits.get(i);
			double dist = p.pos.hitVec.squareDistanceTo(start);
			
			if (dist < length) {
				length = dist;
				closest = p;
			}
		}
		
		if (closest != null) {
			return closest;
		}
		
		return null;
	}
	
	public static MovingObjectPosition traceAABB(World world, int x, int y, int z, Vec3 start, Vec3 end, AxisAlignedBB aabb)
    {
        start = start.addVector((double)(-x), (double)(-y), (double)(-z));
        end = end.addVector((double)(-x), (double)(-y), (double)(-z));
        Vec3 vminx = start.getIntermediateWithXValue(end, aabb.minX);
        Vec3 vmaxx = start.getIntermediateWithXValue(end, aabb.maxX);
        Vec3 vminy = start.getIntermediateWithYValue(end, aabb.minY);
        Vec3 vmaxy = start.getIntermediateWithYValue(end, aabb.maxY);
        Vec3 vminz = start.getIntermediateWithZValue(end, aabb.minZ);
        Vec3 vmaxz = start.getIntermediateWithZValue(end, aabb.maxZ);

        if (!vecInAABB(vminx, aabb, 1))
        {
            vminx = null;
        }

        if (!vecInAABB(vmaxx, aabb, 1))
        {
            vmaxx = null;
        }

        if (!vecInAABB(vminy, aabb, 2))
        {
            vminy = null;
        }

        if (!vecInAABB(vmaxy, aabb, 2))
        {
            vmaxy = null;
        }

        if (!vecInAABB(vminz, aabb, 3))
        {
            vminz = null;
        }

        if (!vecInAABB(vmaxz, aabb, 3))
        {
            vmaxz = null;
        }

        Vec3 nearest = null;

        if (vminx != null && (nearest == null || start.squareDistanceTo(vminx) < start.squareDistanceTo(nearest)))
        {
            nearest = vminx;
        }

        if (vmaxx != null && (nearest == null || start.squareDistanceTo(vmaxx) < start.squareDistanceTo(nearest)))
        {
            nearest = vmaxx;
        }

        if (vminy != null && (nearest == null || start.squareDistanceTo(vminy) < start.squareDistanceTo(nearest)))
        {
            nearest = vminy;
        }

        if (vmaxy != null && (nearest == null || start.squareDistanceTo(vmaxy) < start.squareDistanceTo(nearest)))
        {
            nearest = vmaxy;
        }

        if (vminz != null && (nearest == null || start.squareDistanceTo(vminz) < start.squareDistanceTo(nearest)))
        {
            nearest = vminz;
        }

        if (vmaxz != null && (nearest == null || start.squareDistanceTo(vmaxz) < start.squareDistanceTo(nearest)))
        {
            nearest = vmaxz;
        }

        if (nearest == null)
        {
            return null;
        }
        else
        {
            byte side = -1;

            if (nearest == vminx)
            {
                side = 4;
            }

            if (nearest == vmaxx)
            {
                side = 5;
            }

            if (nearest == vminy)
            {
                side = 0;
            }

            if (nearest == vmaxy)
            {
                side = 1;
            }

            if (nearest == vminz)
            {
                side = 2;
            }

            if (nearest == vmaxz)
            {
                side = 3;
            }

            return new MovingObjectPosition(x, y, z, side, nearest.addVector((double)x, (double)y, (double)z));
        }
    }
	
	public static boolean vecInAABB(Vec3 vec, AxisAlignedBB aabb) {
		return vecInAABB(vec,aabb,-1);
	}
	public static boolean vecInAABB(Vec3 vec, AxisAlignedBB aabb, int excludeaxis) {
		if (vec == null || aabb == null) { return false; }
		
		return (excludeaxis==1 || (vec.xCoord >= aabb.minX && vec.xCoord <= aabb.maxX)) && (excludeaxis==2 || (vec.yCoord >= aabb.minY && vec.yCoord <= aabb.maxY)) && (excludeaxis==3 || (vec.zCoord >= aabb.minZ && vec.zCoord <= aabb.maxZ));
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
	
	public static class PartHit {
		public final MovingObjectPosition pos;
		public final Part part;
		
		public PartHit(MovingObjectPosition pos, Part part) {
			this.pos = pos;
			this.part = part;
		}
	}
}
