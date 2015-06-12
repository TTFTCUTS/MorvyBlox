package ttftcuts.morvyblox;

import net.minecraft.item.Item;
import ttftcuts.morvyblox.block.BlockPart;
import ttftcuts.morvyblox.block.TilePart;
import ttftcuts.morvyblox.crafting.CuttingRecipe;
import ttftcuts.morvyblox.crafting.JoiningRecipe;
import ttftcuts.morvyblox.item.ItemPart;
import ttftcuts.morvyblox.item.ItemSaw;
import ttftcuts.morvyblox.shape.PartShape;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {		
    	PartShape.Shapes.init();
    	
    	MorvyBlox.saw = new ItemSaw(Item.ToolMaterial.IRON);
    	GameRegistry.registerItem(MorvyBlox.saw, "saw");
    	
    	MorvyBlox.part = new ItemPart();
    	GameRegistry.registerItem(MorvyBlox.part, "part");
    	
    	GameRegistry.addRecipe(new CuttingRecipe());
    	GameRegistry.addRecipe(new JoiningRecipe());
    	
    	MorvyBlox.block = new BlockPart();
    	GameRegistry.registerBlock(MorvyBlox.block, "block");
    	GameRegistry.registerTileEntity(TilePart.class, "morvyblox");
	}
	
	public void init(FMLInitializationEvent event) {

	}
	
	public void postInit(FMLPostInitializationEvent event) {

	}
}
