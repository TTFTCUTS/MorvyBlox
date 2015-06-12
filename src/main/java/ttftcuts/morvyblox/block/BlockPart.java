package ttftcuts.morvyblox.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPart extends BlockContainer {

	public BlockPart() {
		super(Material.rock);
		
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TilePart();
	}

	@Override
	public int getRenderType() {
		return BlockPartRenderer.renderID;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
