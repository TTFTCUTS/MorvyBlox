package ttftcuts.morvyblox.crafting;

import ttftcuts.morvyblox.api.IMorvyBlockSaw;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CuttingRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting grid, World world) {
		ItemStack saw = null;
		int sawloc = -1;
		ItemStack cut = null;
		int cutloc = -1;
		
		for (int i=0; i<grid.getSizeInventory(); i++) {
			ItemStack stack = grid.getStackInSlot(i);
			if (stack == null) { continue; }
			Item item = stack.getItem();
			
			if (item instanceof IMorvyBlockSaw) {
				if (saw == null) {
					saw = stack;
					sawloc = i;
				} else {
					return false;
				}
			}
			else if (item instanceof ItemBlock) { // todo: or a cuttable part block
				if (cut == null) {
					cut = stack;
					cutloc = i;
				} else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		
		if (saw == null || cut == null) { return false; }
		
		ItemBlock iblock = (ItemBlock)(cut.getItem());
		if (iblock == null) { return false; }
		Block block = iblock.field_150939_a;
		if (block == null) { return false; }
		int meta = cut.getItemDamage();
		
		IMorvyBlockSaw isaw = (IMorvyBlockSaw)(saw.getItem());
		
		if (isaw.canSawBlock(block, meta, saw)) {
			int size = grid.getSizeInventory();
			int stride = (int)Math.round(Math.sqrt(size));
			int height = size/stride;
			
			int sx = sawloc % stride;
			int sy = sawloc / stride;
			
			int bx = cutloc % stride;
			int by = cutloc / stride;
			
			if (this.isACut(cut, stride, height, sx, sy, bx, by)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting grid) {
		ItemStack saw = null;
		int sawloc = -1;
		ItemStack cut = null;
		int cutloc = -1;
		
		for (int i=0; i<grid.getSizeInventory(); i++) {
			ItemStack stack = grid.getStackInSlot(i);
			if (stack == null) { continue; }
			Item item = stack.getItem();
			
			if (item instanceof IMorvyBlockSaw) {
				saw = stack;
				sawloc = i;
			}
			else if (item instanceof ItemBlock) { // todo: or a cuttable part block
				cut = stack;
				cutloc = i;
			}
		}
		
		if (saw == null || cut == null) { return null; }
		
		int size = grid.getSizeInventory();
		int stride = (int)Math.round(Math.sqrt(size));
		int height = size/stride;
		
		int sx = sawloc % stride;
		int sy = sawloc / stride;
		
		int bx = cutloc % stride;
		int by = cutloc / stride;
		
		if (this.isHorizontalCut(stride, height, sx,sy,bx,by)) {
			return this.doCut(cut, false);
		} else if (this.isVerticalCut(stride, height, sx,sy,bx,by)) {
			return this.doCut(cut, true);
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

	protected boolean isACut(ItemStack toCut, int xsize, int ysize, int sx, int sy, int bx, int by) {
		return (isVerticalCut(xsize, ysize, sx, sy, bx, by) && canCutVertical(toCut)) || (isHorizontalCut(xsize, ysize, sx, sy, bx, by) && canCutHorizontal(toCut));
	}
	
	protected boolean isVerticalCut(int xsize, int ysize, int sx, int sy, int bx, int by) {
		if(sx == bx && ((sy == by+1 && sy < ysize) || (sy == by-1 && sy >= 0))) {
			return true;
		}
		return false;
	}
	
	protected boolean canCutVertical(ItemStack toCut) {
		return true;
	}
	
	protected boolean canCutHorizontal(ItemStack toCut) {
		return true;
	}
	
	protected boolean isHorizontalCut(int xsize, int ysize, int sx, int sy, int bx, int by) {
		if(sy == by && ((sx == bx+1 && sx < xsize) || (sx == bx-1 && sx >= 0))) {
			return true;
		}
		return false;
	}
	
	protected ItemStack doCut(ItemStack toCut, boolean vertical) {
		return vertical ? new ItemStack(Items.diamond) : new ItemStack(Items.emerald);
	}
}
