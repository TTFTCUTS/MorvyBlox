package ttftcuts.morvyblox.shape;

import ttftcuts.morvyblox.MorvyBlox;
import ttftcuts.morvyblox.shape.Raytracer.PartHit;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class PartPlacer {

	public static void findPlacement(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, MovingObjectPosition hit) {
		Block block = world.getBlock(x, y, z);
		if (block == MorvyBlox.block) {
			PartHit parthit = Raytracer.traceIntoMorvyBlox(world, player, x, y, z);
			
			if (parthit == null) { return; }
			hit = parthit.pos;
			
			MorvyBlox.logger.info(parthit.part);
		} else {
			MorvyBlox.logger.info(block.getLocalizedName());
		}
	}
}
