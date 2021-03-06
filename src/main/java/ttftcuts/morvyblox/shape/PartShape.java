package ttftcuts.morvyblox.shape;

import java.util.HashMap;
import java.util.Map;

import ttftcuts.morvyblox.MorvyBlox;
import ttftcuts.morvyblox.crafting.CuttingRecipe;
import ttftcuts.morvyblox.item.ItemPart;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class PartShape {
	public final String id;
	public final double dx;
	public final double dy;
	public final double dz;
	
	public PartShape verticalCut = null;
	public PartShape horizontalCut = null;
	public PartShape join = null;

	public PartShape(String id, double dx, double dy, double dz) {
		this.id = id;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		Shapes.registry.put(id, this);
	}
	
	public ItemStack getStack(Block block, int id) {
		return getStack(block, id, 1);
	}
	
	public ItemStack getStack(Block block, int meta, int stacksize) {
		ItemStack stack = new ItemStack(MorvyBlox.part, stacksize, 0);
		
		ItemPart.SetPartData(stack, this, block, meta);
		
		return stack;
	}
	
	public ItemStack getStack(BlockMeta bm, int stacksize) {
		return getStack(bm.block, bm.meta, stacksize);
	}
	
	public ItemStack getStack(BlockMeta bm) {
		return getStack(bm,1);
	}
	
	public static PartShape getShapeFromStack(ItemStack stack) {
		Item item = stack.getItem();
		
		if (item == null) { return null; }
		
		if (item instanceof ItemBlock) {
			ItemBlock ib = (ItemBlock)item;
			if (CuttingRecipe.isBlockCuttable(ib.field_150939_a, stack.getItemDamage())) {
				return Shapes.block;
			}
		}
		else if (item instanceof ItemPart) {
			return ItemPart.getShapeFromStack(stack);
		}
		
		return null;
	}
	
	public static abstract class Shapes {
		public static Map<String, PartShape> registry;
		
		public static PartShape block; // full block
		
		public static PartShape slab; // 1/2 block
		public static PartShape panel; // 1/4 block
		public static PartShape cover; // 1/8 block
		
		public static PartShape column; // 1/2 block column
		public static PartShape panelStrip; // 1/4 block column
		public static PartShape coverStrip; // 1/8 block column
		
		public static PartShape notch; // 1/2 block corner
		public static PartShape corner; // 1/4 block corner
		public static PartShape nook; // 1/8 block corner
		
		public static void init() {
			registry = new HashMap<String, PartShape>();
			
			block = new PartShape("b",1.0,1.0,1.0) {
				@Override
				public ItemStack getStack(Block block, int id, int stacksize) {
					return new ItemStack(block, stacksize, id);
				}
			};

			slab = new PartShape("s8",1.0/2,1.0,1.0);
			panel = new PartShape("s4",1.0/4,1.0,1.0);
			cover = new PartShape("s2",1.0/8,1.0,1.0);
			
			column = new PartShape("p8",1.0/2,1.0,1.0/2);
			panelStrip = new PartShape("p4",1.0/4,1.0,1.0/4);
			coverStrip = new PartShape("p2",1.0/8,1.0,1.0/8);
			
			notch = new PartShape("c8",1.0/2,1.0/2,1.0/2);
			corner = new PartShape("c4",1.0/4,1.0/4,1.0/4);
			nook = new PartShape("c2",1.0/8,1.0/8,1.0/8);
			
			// relations
			block.verticalCut = slab;
			
			slab.verticalCut = panel;
			slab.horizontalCut = column;
			slab.join = block;
			
			panel.verticalCut = cover;
			panel.horizontalCut = panelStrip;
			panel.join = slab;
			
			cover.horizontalCut = coverStrip;
			cover.join = panel;
			
			column.verticalCut = panelStrip;
			column.horizontalCut = notch;
			column.join = slab;
			
			panelStrip.verticalCut = coverStrip;
			panelStrip.horizontalCut = corner;
			panelStrip.join = panel;
			
			coverStrip.horizontalCut = nook;
			coverStrip.join = cover;
			
			notch.verticalCut = corner;
			notch.join = column;
			
			corner.verticalCut = nook;
			corner.join = panelStrip;
			
			nook.join = coverStrip;
		}
	}
}
