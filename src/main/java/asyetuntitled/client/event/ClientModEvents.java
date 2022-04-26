package asyetuntitled.client.event;

import java.util.Set;
import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.particle.SpookyEyesParticle;
import asyetuntitled.client.render.blockentity.TouchStoneBER;
import asyetuntitled.client.render.entity.BackpackRenderer;
import asyetuntitled.client.render.entity.LivingChestRenderer;
import asyetuntitled.client.render.entity.ShadowCreatureRenderer;
import asyetuntitled.client.render.entity.StaffRenderer;
import asyetuntitled.client.render.entity.layer.BackpackLayer;
import asyetuntitled.client.render.item.BackpackItemStackLoader;
import asyetuntitled.client.render.model.BackpackModel;
import asyetuntitled.client.render.model.LivingChestModel;
import asyetuntitled.client.render.model.ShadowChickenModel;
import asyetuntitled.client.render.model.ShadowSpiderModel;
import asyetuntitled.client.render.model.StaffModel;
import asyetuntitled.client.render.model.WearableBackpackModel;
import asyetuntitled.client.sanity.ClientSanityData;
import asyetuntitled.client.screen.ClientCustomBundleTooltip;
import asyetuntitled.client.screen.LivingChestScreen;
import asyetuntitled.client.util.ClientReflectionHelper;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.block.BlocksRegistry;
import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.entity.ShadowChicken;
import asyetuntitled.common.entity.ShadowSpider;
import asyetuntitled.common.item.CustomBundleTooltip;
import asyetuntitled.common.item.ItemsRegistry;
import asyetuntitled.common.menu.MenusRegistry;
import asyetuntitled.common.particle.ParticlesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent.Block;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    public static void init(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            ClientReflectionHelper.init();
            
            ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.GRASSUSEFUL.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.TALLGRASSUSEFUL.get(), RenderType.cutoutMipped());
            MenuScreens.register(MenusRegistry.LIVING_CHEST_CONTAINER.get(), LivingChestScreen::new);           // Attach our container to the screen
//          ItemBlockRenderTypes.setRenderLayer(Registration.POWERGEN.get(), RenderType.translucent()); // Set the render type for our power generator to translucent
            ItemProperties.register(ItemsRegistry.DUMMY_RUNE.get(), ClientResourceLocations.ALPHABET, (stack, level, living, id) -> {
                CompoundTag tag = stack.getOrCreateTag();
                float f = 0.0F;
                if(tag.contains("Alphabet"))
                {
                    f = tag.getFloat("Alphabet");
                }
                return f;
            });
           
            MinecraftForgeClient.registerTooltipComponentFactory(CustomBundleTooltip.class, ClientCustomBundleTooltip::new);
        });
    }
    
	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
	{
        event.registerLayerDefinition(ClientResourceLocations.BACKPACK_MODEL_LAYER, BackpackModel::createBodyLayer);
		event.registerLayerDefinition(ClientResourceLocations.LIVING_CHEST_MODEL_LAYER, LivingChestModel::createBodyLayer);
		event.registerLayerDefinition(ClientResourceLocations.STAFF_MODEL_LAYER, StaffModel::createBodyLayer);
		event.registerLayerDefinition(ClientResourceLocations.SHADOW_SPIDER_MODEL_LAYER, ShadowSpiderModel::createBodyLayer);
		event.registerLayerDefinition(ClientResourceLocations.SHADOW_CHICKEN_MODEL_LAYER, ShadowChickenModel::createBodyLayer);
	}
	
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerEntityRenderer(EntityRegistry.BACKPACK.get(), BackpackRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LIVING_CHEST.get(), LivingChestRenderer::new);
		event.registerEntityRenderer(EntityRegistry.STAFF.get(), StaffRenderer::new);
		
		event.registerEntityRenderer(EntityRegistry.SHADOW_SPIDER.get(), (context) -> new ShadowCreatureRenderer<ShadowSpider>(context, ClientResourceLocations.SHADOW_SPIDER_MODEL_LAYER, new ShadowSpiderModel(context.bakeLayer(ClientResourceLocations.SHADOW_SPIDER_MODEL_LAYER)), ClientResourceLocations.SHADOW_SPIDER_TEXTURE) {
			@Override
			   protected float getFlipDegrees(ShadowSpider p_116011_) {
			      return 180.0F;
			   }
		});
		event.registerEntityRenderer(EntityRegistry.SHADOW_CHICKEN.get(), (context) -> new ShadowCreatureRenderer<ShadowChicken>(context, ClientResourceLocations.SHADOW_CHICKEN_MODEL_LAYER, new ShadowChickenModel(context.bakeLayer(ClientResourceLocations.SHADOW_CHICKEN_MODEL_LAYER)), ClientResourceLocations.SHADOW_CHICKEN_TEXTURE) {
			@Override   
			protected float getBob(ShadowChicken chicken, float p_114001_) {
				      float f = Mth.lerp(p_114001_, chicken.oFlap, chicken.flap);
				      float f1 = Mth.lerp(p_114001_, chicken.oFlapSpeed, chicken.flapSpeed);
				      return (Mth.sin(f) + 1.0F) * f1;
				   }
		});

		event.registerBlockEntityRenderer(BlocksRegistry.TOUCHSTONEBE.get(), TouchStoneBER::new);
		event.registerBlockEntityRenderer(BlocksRegistry.TOUCHSTONECORNERBE.get(), TouchStoneCornerBER::new);

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
	public static void registerItemStackLoaders(ModelRegistryEvent event) 
	{
		ModelLoaderRegistry.registerLoader(ClientResourceLocations.BACKPACK_RENDER_LOADER,
            BackpackItemStackLoader.INSTANCE);
	}
	
	@SubscribeEvent
	public static void registerEntityLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ClientResourceLocations.WEARABLE_BACKPACK_MODEL_LAYER, WearableBackpackModel::createBodyLayer);
	}
	
	
	//Adds a backpack layer to any entities who can carry a backpack
	@SubscribeEvent
	public static void addLayersToEntities(final EntityRenderersEvent.AddLayers event) {
		
		Set<String> s = event.getSkins();
		for(String p : s)
		{
			PlayerRenderer player = event.getSkin(p);
			if(player != null)
			{
				player.addLayer(new BackpackLayer<>(player));
				
			}

		}
		
		ZombieRenderer zombie = event.getRenderer(EntityType.ZOMBIE);
		if(zombie != null)
		{
			zombie.addLayer(new BackpackLayer<>(zombie));
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
