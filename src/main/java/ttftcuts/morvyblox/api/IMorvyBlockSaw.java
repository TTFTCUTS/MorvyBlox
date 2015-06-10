package ttftcuts.morvyblox.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface IMorvyBlockSaw {
	
	/**
	 * Check a block to see if this saw is able to cut it.
	 * @param block - The type of block to be checked
	 * @param meta - The metadata of the block to be checked
	 * @param saw - ItemStack representing the saw
	 * @return Whether the saw is able to cut the checked block or not
	 */
	public boolean canSawBlock(Block block, int meta, ItemStack saw);
}
