package ttftcuts.morvyblox;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MorvyBlox.MOD_ID, name = "MorvyBlox", version = "$GRADLEVERSION")
public class MorvyBlox {

	public static Item saw;
	public static Item part;
	public static Block block;
	
    public static final String MOD_ID = "morvyblox";
    public static final Logger logger = LogManager.getLogger(MOD_ID);
       
    @Mod.Instance
    public static MorvyBlox instance;
    
    @SidedProxy(serverSide="ttftcuts.morvyblox.CommonProxy", clientSide="ttftcuts.morvyblox.ClientProxy")
    public static CommonProxy proxy;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	proxy.preInit(event);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }
}
