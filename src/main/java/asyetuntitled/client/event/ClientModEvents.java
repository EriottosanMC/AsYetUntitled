package asyetuntitled.client.event;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.particle.SpookyEyesParticle;
import asyetuntitled.client.render.entity.ShadowCreatureRenderer;
import asyetuntitled.client.render.model.ShadowChickenModel;
import asyetuntitled.client.render.model.ShadowSpiderModel;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.entity.ShadowChicken;
import asyetuntitled.common.entity.ShadowSpider;
import asyetuntitled.common.particle.ParticlesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(ClientResourceLocations.SHADOW_SPIDER_MODEL_LAYER, ShadowSpiderModel::createBodyLayer);
		event.registerLayerDefinition(ClientResourceLocations.SHADOW_CHICKEN_MODEL_LAYER, ShadowChickenModel::createBodyLayer);
	}
	
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
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
	}
	
	@SubscribeEvent
    public static void registerTextures(final TextureStitchEvent.Pre evt) {
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
}
