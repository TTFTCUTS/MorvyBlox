package ttftcuts.morvyblox.shape;

import ttftcuts.morvyblox.item.ItemPart;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockMeta {
	public final Block block;
	public final int meta;
	
	public BlockMeta(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}
	
	public static BlockMeta getBlockMetaFromStack(ItemStack stack) {
		Item item = stack.getItem();
		int meta = stack.getItemDamage();
		
		if (item == null) { return null; }
		
		if (item instanceof ItemBlock) {
			ItemBlock itemblock = (ItemBlock)item;
			
			if (itemblock.field_150939_a != null) {
				return new BlockMeta(itemblock.field_150939_a, meta);
			}
		}
		else if (item instanceof ItemPart) {
			return ItemPart.getBlockMetaFromStack(stack);
		}
		
		return null;
	}
}
