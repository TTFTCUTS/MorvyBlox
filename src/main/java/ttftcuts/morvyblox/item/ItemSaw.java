package ttftcuts.morvyblox.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import ttftcuts.morvyblox.MorvyBlox;
import ttftcuts.morvyblox.api.IMorvyBlockSaw;

public class ItemSaw extends ItemTool implements IMorvyBlockSaw {
	
	private static Set<Block> effective = Sets.newHashSet(new Block[] {});
	
	public ItemSaw(Item.ToolMaterial material) {
		super(0.0f, material, effective);
		this.setMaxDamage(5);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName("morvyblox.saw");
        this.setTextureName(MorvyBlox.MOD_ID+":saw");
	}

	@Override
	public boolean canSawBlock(Block block, int meta, ItemStack saw) {
		return true;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		if (!this.isDamageable() || stack.getItemDamage() == this.getMaxDamage(stack)) {
			return false;
		}
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if (this.isDamageable()) {
			return new ItemStack(stack.getItem(), 1, stack.getItemDamage()+1);
		}
		return stack;
	}
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}
}
