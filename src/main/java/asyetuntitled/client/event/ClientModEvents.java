package asyetuntitled.client.event;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.particle.SpookyEyesParticle;
import asyetuntitled.client.render.RendererRegistryEvents;
import asyetuntitled.client.render.item.ItemRenderer;
import asyetuntitled.client.sanity.ClientSanityData;
import asyetuntitled.client.util.ClientReflectionHelper;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.block.BlocksRegistry;
import asyetuntitled.common.menu.MenusRegistry;
import asyetuntitled.common.particle.ParticlesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent.Block;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents 
{

    @SubscribeEvent
    public static void setupEvent(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            
            ClientReflectionHelper.init();
            RendererRegistryEvents.registerItemBlockRenderers();
            MenusRegistry.registerScreens();
            ItemRenderer.registerAll();
        });
    }
    
	
	//TODO Work out how completable future works to add resource reload
	@SubscribeEvent
	public static void clientReloadEvent(RegisterClientReloadListenersEvent event)
	{
//	        event.registerReloadListener(new ReloadListener());
	}
	
	//This is a hacky way of achieving what the above should achieve cleanly
	@SubscribeEvent
	public static void textureStitchEvent(TextureStitchEvent.Post event)
	{
	    ClientSanityData.forceUpdate();
	}
	
	@SubscribeEvent
    public static void registerTextures(final TextureStitchEvent.Pre evt)
	{
      TextureAtlas map = evt.getAtlas();

      if (map.location() == InventoryMenu.BLOCK_ATLAS) 
      {
          evt.addSprite(ClientResourceLocations.BLANK_TEXTURE);
      }
    }
	
	@SubscribeEvent
	public static void registerParticles(ParticleFactoryRegisterEvent event)
	{
		Minecraft mc = Minecraft.getInstance();
		mc.particleEngine.register(ParticlesRegistry.SPOOKY_EYES.get(), SpookyEyesParticle.Provider::new);
	}
	
	@SubscribeEvent
	public static void registerColors(Block event)
	{
		event.getBlockColors().register((p_92641_, tintG, pos, p_92644_) -> {
	         return tintG != null && pos != null ? BiomeColors.getAverageGrassColor(tintG, pos) : GrassColor.get(0.5D, 1.0D);
	      }, BlocksRegistry.GRASSUSEFUL.get(), BlocksRegistry.TALLGRASSUSEFUL.get());
	}
	
}
