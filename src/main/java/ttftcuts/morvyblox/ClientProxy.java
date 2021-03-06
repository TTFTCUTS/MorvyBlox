package ttftcuts.morvyblox;

import net.minecraftforge.client.MinecraftForgeClient;
import ttftcuts.morvyblox.block.BlockPartRenderer;
import ttftcuts.morvyblox.item.ItemPartRenderer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		RenderingRegistry.registerBlockHandler(new BlockPartRenderer(RenderingRegistry.getNextAvailableRenderId()));
		MinecraftForgeClient.registerItemRenderer(MorvyBlox.part, new ItemPartRenderer());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
