package ttftcuts.morvyblox.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ttftcuts.morvyblox.MorvyBlox;
import ttftcuts.morvyblox.block.TilePart.Part;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockPart extends BlockContainer {

	public static Block renderBlock = null;
	public static int renderMeta = 0;
	protected Random random;
	
	public BlockPart() {
		super(Material.rock);
		this.setBlockTextureName(MorvyBlox.MOD_ID+":blank");
		this.random = new Random();
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
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int fortune) {
		TileEntity wtile = world.getTileEntity(x, y, z);
		
		if (wtile instanceof TilePart) {
			TilePart tile = (TilePart)wtile;
			
			for (Part part : tile.parts) {
				ItemStack stack = part.getStack();
				
				if (stack != null) {
					float ox = random.nextFloat() * 0.8F + 0.1F;
					float oy = random.nextFloat() * 0.8F + 0.1F;
					float oz = random.nextFloat() * 0.8F + 0.1F;
					
					EntityItem entityitem = new EntityItem(world, x+ox, y+oy, z+oz, stack);
					float mult = 0.05f;
					
					entityitem.motionX = (float)random.nextGaussian() * mult;
					entityitem.motionY = (float)random.nextGaussian() * mult + 0.2f;
					entityitem.motionZ = (float)random.nextGaussian() * mult;
					
					world.spawnEntityInWorld(entityitem);
				}
			}
			
			world.func_147453_f(x, y, z, block);
		}
		
		super.breakBlock(world, x, y, z, block, fortune);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if (renderBlock != null) {
			return renderBlock.getIcon(side, renderMeta);
		}
        return this.blockIcon;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB ebb, List list, Entity entity) {
		TileEntity wtile = world.getTileEntity(x, y, z);
		if (wtile instanceof TilePart) {
			TilePart tile = (TilePart)wtile;
			
			for (Part p : tile.parts) {
				AxisAlignedBB aabb = p.aabb.copy().offset(x, y, z);
				if (aabb.intersectsWith(ebb)) {
					list.add(aabb);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean canRenderInPass(int pass)
    {
		BlockPartRenderer.pass = pass;
		return true;
    }
}
