package ttftcuts.morvyblox;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ttftcuts.morvyblox.crafting.CuttingRecipe;
import ttftcuts.morvyblox.item.ItemPart;
import ttftcuts.morvyblox.item.ItemSaw;
import ttftcuts.morvyblox.shape.PartShape;

@Mod(modid = MorvyBlox.MOD_ID, name = "MorvyBlox", version = "$GRADLEVERSION")
public class MorvyBlox {

	public static Item saw;
	public static Item part;
	
    public static final String MOD_ID = "morvyblox";
    public static final Logger logger = LogManager.getLogger(MOD_ID);
       
    @Mod.Instance
    public static MorvyBlox instance;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	PartShape.Shapes.init();
    	
    	saw = new ItemSaw(Item.ToolMaterial.IRON);
    	GameRegistry.registerItem(saw, "saw");
    	
    	part = new ItemPart();
    	GameRegistry.registerItem(part, "part");
    	
    	GameRegistry.addRecipe(new CuttingRecipe());
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	
    }
}
