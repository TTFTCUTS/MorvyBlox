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
import ttftcuts.morvyblox.item.ItemSaw;

@Mod(modid = MorvyBlox.MOD_ID, name = "MorvyBlox", version = "$GRADLEVERSION")
public class MorvyBlox {

	public Item saw;
	
    public static final String MOD_ID = "morvyblox";
    public static final Logger logger = LogManager.getLogger(MOD_ID);
       
    @Mod.Instance
    public static MorvyBlox instance;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	saw = new ItemSaw(Item.ToolMaterial.IRON);
    	GameRegistry.registerItem(saw, "saw");
    	
    	GameRegistry.addRecipe(new CuttingRecipe());
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	
    }
}
