package ttftcuts.morvyblox.crafting;

import ttftcuts.morvyblox.shape.BlockMeta;
import ttftcuts.morvyblox.shape.PartShape;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class JoiningRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting grid, World world) {
		BlockMeta blockmeta = null;
		PartShape shape = null;
		int count = 0;
		
		for (int i=0; i<grid.getSizeInventory(); i++) {
			ItemStack stack = grid.getStackInSlot(i);
			if (stack == null) { continue; }
			
			PartShape slotshape = PartShape.getShapeFromStack(stack);
			if (slotshape == null || slotshape.join == null) {
				return false;
			} else {
				BlockMeta sbm = BlockMeta.getBlockMetaFromStack(stack);
				if (sbm == null) { return false; }
				
				if (shape == null) {
					blockmeta = sbm;
					shape = slotshape;
				} else {
					if (!(blockmeta.block == sbm.block && blockmeta.meta == sbm.meta && shape == slotshape)) {
						return false;
					}
				}
				count++;
			}
		}
		
		if (count == 2) {
			return true;
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting grid) {
		BlockMeta blockmeta = null;
		PartShape shape = null;
		int count = 0;
		
		for (int i=0; i<grid.getSizeInventory(); i++) {
			ItemStack stack = grid.getStackInSlot(i);
			if (stack == null) { continue; }
			
			PartShape slotshape = PartShape.getShapeFromStack(stack);
			if (slotshape == null || slotshape.join == null) {
				return null;
			} else {
				BlockMeta sbm = BlockMeta.getBlockMetaFromStack(stack);
				if (sbm == null) { return null; }
				
				if (shape == null) {
					blockmeta = sbm;
					shape = slotshape;
				} else {
					if (!(blockmeta.block == sbm.block && blockmeta.meta == sbm.meta && shape == slotshape)) {
						return null;
					}
				}
				count++;
			}
		}
		
		if (count == 2) {
			return shape.join.getStack(blockmeta.block, blockmeta.meta, 1);
		}
		
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
