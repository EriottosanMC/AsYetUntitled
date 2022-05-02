package asyetuntitled.client.event;

import com.mojang.math.Vector3f;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.render.item.ItemRenderer;
import asyetuntitled.client.util.ClientReflectionHelper;
import asyetuntitled.client.util.LightMapGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ScreenEvent.DrawScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientRenderEventsHandler {
	
	//Gets the DynamicTexture used for render and updates the lightmap for the Overworld
	@SubscribeEvent
	public static void onRenderView(EntityViewRenderEvent event)
	{ 
		Minecraft mc = Minecraft.getInstance();
		Level level = mc.level;
		if(level != null) 
		{
			GameRenderer renderer = event.getRenderer();
			LightTexture lt = renderer.lightTexture();
			DynamicTexture dt = ClientReflectionHelper.getLightTexture(lt);
			LightMapGenerator.update(lt, mc, dt);
		}
	}
	
	//Turns the sky to black on dark nights
	@SubscribeEvent
	public static void onRenderFogColours(EntityViewRenderEvent.FogColors event)
	{ 
		Minecraft mc = Minecraft.getInstance();
		Level level = mc.level;

		float time = (float) (level.getSunAngle(1.0F) > Math.PI ? 2 * Math.PI - level.getSunAngle(1.0F) : level.getSunAngle(1.0F));
		if(level.dimension() == Level.OVERWORLD && time > Math.PI / 2)
		{
			Vector3f skyColour = new Vector3f(event.getRed(), event.getGreen(), event.getBlue());
			Vector3f darkColour = skyColour.copy();
			darkColour.mul(level.getMoonBrightness());
			float f = (float) (time >= 5 * Math.PI / 8 ? 1.0F : 4 * (2 * time - Math.PI)/Math.PI);
			skyColour.lerp(darkColour, f);
			event.setRed(skyColour.x());
			event.setGreen(skyColour.y());
			event.setBlue(skyColour.z());
		}
		
	}
	
	//Render Sanity over other elements
	@SubscribeEvent
	public static void postOverlayRender(RenderGameOverlayEvent.Post event) 
	{ 
		if(event.getType() == RenderGameOverlayEvent.ElementType.ALL)
		{
		    SanityRenderer.render(event.getWindow());
		    ThoughtsRenderer.render(event.getWindow(), event.getMatrixStack());
		}
	}
	
	@SubscribeEvent
	public static void renderOverlay(RenderGameOverlayEvent event) 
	{
		
	}
	
	@SubscribeEvent
	public static void renderScreen(DrawScreenEvent.Post event)
	{
		if(event.getScreen() instanceof InventoryScreen screen)
		{
		    ItemRenderer.renderDamage(screen);
		}
	}
	
	@SubscribeEvent
	public static void renderEntity(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event)
	{
	}
	
	@SubscribeEvent
	public static void renderEntity(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event)
	{
	}
	
}
