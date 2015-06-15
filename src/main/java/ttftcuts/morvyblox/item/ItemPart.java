package ttftcuts.morvyblox.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ttftcuts.morvyblox.MorvyBlox;
import ttftcuts.morvyblox.block.TilePart;
import ttftcuts.morvyblox.shape.BlockMeta;
import ttftcuts.morvyblox.shape.PartPlacer;
import ttftcuts.morvyblox.shape.PartShape;
import ttftcuts.morvyblox.shape.Raytracer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemPart extends Item {
	public static final String PARTTAG = "MorvyBloxPart";
	
	public ItemPart() {
		this.setUnlocalizedName("morvyblox.part");
		this.setTextureName("brick");
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		BlockMeta bm = BlockMeta.getBlockMetaFromStack(stack);
		PartShape shape = PartShape.getShapeFromStack(stack);
		if (bm != null && shape != null) {
			ItemStack bstack = new ItemStack(bm.block, 1, bm.meta);
			
			return bstack.getDisplayName() + " " + StatCollector.translateToLocal(this.getUnlocalizedName()+"."+shape.id);
		}
		
		return super.getItemStackDisplayName(stack);
	}
	
	public static void SetPartData(ItemStack stack, PartShape shape, Block block, int meta) {
		NBTTagCompound tag;
		if (!stack.hasTagCompound()) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		} else {
			tag = stack.getTagCompound();
		}
		
		NBTTagCompound parttag = new NBTTagCompound();
		parttag.setString("s", shape.id);
		parttag.setInteger("b", Block.getIdFromBlock(block));
		parttag.setInteger("m", meta);
		
		tag.setTag(PARTTAG, parttag);
	}
	
	public static PartShape getShapeFromStack(ItemStack stack) {
		if (stack.getItem() != MorvyBlox.part) { return null; }
		if (!stack.hasTagCompound()) { return null; }
		NBTTagCompound tag = stack.getTagCompound();
		if (!tag.hasKey(PARTTAG)) { return null; }
		NBTTagCompound parttag = tag.getCompoundTag(PARTTAG);
		
		String id = parttag.getString("s");
		if (PartShape.Shapes.registry.containsKey(id)) {
			return PartShape.Shapes.registry.get(id);
		}
		
		return null;
	}
	
	public static BlockMeta getBlockMetaFromStack(ItemStack stack) {
		if (stack.getItem() != MorvyBlox.part) { return null; }
		if (!stack.hasTagCompound()) { return null; }
		NBTTagCompound tag = stack.getTagCompound();
		if (!tag.hasKey(PARTTAG)) { return null; }
		NBTTagCompound parttag = tag.getCompoundTag(PARTTAG);
				
		BlockMeta bm = new BlockMeta(Block.getBlockById(parttag.getInteger("b")), parttag.getInteger("m"));
		
		return bm;
	}
	
	@SideOnly(Side.CLIENT)
    public int getSpriteNumber()
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
    	BlockMeta bm = BlockMeta.getBlockMetaFromStack(stack);
    	if (bm != null) {
    		return bm.block.getIcon(0, bm.meta);
    	}
        return this.getIconFromDamage(0);
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if (stack.stackSize == 0)
        {
            return false;
        }
		
        Block block = world.getBlock(x, y, z);
        
        MovingObjectPosition hit = Raytracer.traceBlock(world, player, x, y, z);
        
        //MorvyBlox.logger.info(hit);
        
        if (hit != null && hit.typeOfHit == MovingObjectType.BLOCK) {
        	
        	//MorvyBlox.logger.info("hit 2");
        	
        	int hx = x;//hit.blockX;
        	int hy = y;//hit.blockY;
        	int hz = z;//hit.blockZ;
        	
        	if (block != MorvyBlox.block) {
        		if (block == Blocks.snow_layer && (world.getBlockMetadata(hx, hy, hz) & 7) < 1)
                {
                    side = 1;
                }
        		else if (!block.isReplaceable(world, hx, hy, hz)) {
        			switch(side) {
        			case 0:
        				hy -= 1;
        				break;
        			case 1:
        				hy += 1;
        				break;
        			case 2:
        				hz -= 1;
        				break;
        			case 3:
        				hz += 1;
        				break;
        			case 4:
        				hx -= 1;
        				break;
        			case 5:
        				hx += 1;
        				break;
        			}
        		}
        	}
        		
            if (!player.canPlayerEdit(hx, hy, hz, side, stack))
            {
            	//MorvyBlox.logger.info("bail edit");
                return false;
            }
            else if (hy >= 255)
            {
            	//MorvyBlox.logger.info("bail height");
                return false;
            }
            else //if (world.canPlaceEntityOnSide(MorvyBlox.block, hx, hy, hz, false, side, player, stack))
            {
            	//MorvyBlox.logger.info("place?");
        		if (!world.checkNoEntityCollision(AxisAlignedBB.getBoundingBox(hx, hy, hz, hx+1, hy+1, hz+1))) {
        			//MorvyBlox.logger.info("bail collision");
        			return false;
        		}

        		PartPlacer.findPlacement(stack, player, world, hx, hy, hz, hit);

                return true;
            }
        }
        
        return false;
    }
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z)
    {
		if (!world.setBlock(x, y, z, MorvyBlox.block, 0, 3))
		{
		    return false;
		}
		
		if (world.getBlock(x, y, z) == MorvyBlox.block)
		{
			MorvyBlox.block.onBlockPlacedBy(world, x, y, z, player, stack);
			MorvyBlox.block.onPostBlockPlaced(world, x, y, z, 0);
		}
		
		return true;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.block;
    }
}
