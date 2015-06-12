package ttftcuts.morvyblox.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ttftcuts.morvyblox.MorvyBlox;
import ttftcuts.morvyblox.shape.BlockMeta;
import ttftcuts.morvyblox.shape.PartShape;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemPart extends Item {
	public static final String PARTTAG = "MorvyBloxPart";
	
	public ItemPart() {
		this.setUnlocalizedName("morvyblox.part");
	}
	
	/*@SuppressWarnings({ "rawtypes" , "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
		PartShape shape = getShapeFromStack(stack);
		BlockMeta blockmeta = getBlockMetaFromStack(stack);
		list.add("Shape: "+shape.id);
		list.add("Block: "+new ItemStack(blockmeta.block, 1, blockmeta.meta).getDisplayName());
	}*/
	
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
}
