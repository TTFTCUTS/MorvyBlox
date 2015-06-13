package ttftcuts.morvyblox.block;

import ttftcuts.morvyblox.MorvyBlox;
import ttftcuts.morvyblox.block.TilePart.Part;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockPartRenderer implements ISimpleBlockRenderingHandler {

	public static int pass = 0;
	public static int renderID;
	
	public BlockPartRenderer(int id) {
		renderID = id;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileEntity wtile = world.getTileEntity(x, y, z);
		if (wtile instanceof TilePart) {
			TilePart tile = (TilePart)wtile;
			
			boolean rendered = false;
			
			for (Part part : tile.parts) {
				MorvyBlox.logger.info("pass: "+pass);
				if (part.blockmeta.block.getRenderBlockPass() == pass) {
					boolean b = part.render(world, x, y, z);
					if (b) { rendered = true; }
					MorvyBlox.logger.info("draw: "+rendered);
				}
			}
			
			return rendered;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}

}
