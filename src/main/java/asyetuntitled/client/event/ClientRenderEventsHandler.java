package asyetuntitled.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.sanity.ClientSanityData;
import asyetuntitled.client.util.ClientReflectionHelper;
import asyetuntitled.client.util.RendererChanger;
import asyetuntitled.client.util.ClientResourceLocations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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
			RendererChanger.update(lt, mc, dt);
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
			renderSanity(event.getWindow());
		}
	}

	//Renders the sanity aspects
	private static void renderSanity(Window window)
	{
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		if(!player.isSpectator())
		{
			float playerSanity = ClientSanityData.getPlayerSanity();
			int size = 7;
		
			int i = window.getGuiScaledWidth() / 2;
			int j = window.getGuiScaledHeight() / 2;
//			double j2 =  j + 2 + (2 * j / 3);
			
			double j2 = window.getGuiScaledHeight() - 43;
			if(player.isCreative()) j2 += 13;
			double d1 = i + size;
			double d2 = j2 + size;
			double d3 = i - size;
			double d4 = j2 - size;
			
			
			RenderSystem.enableBlend();
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.defaultBlendFunc();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			
			//Renders zombie sanity
			RenderSystem.setShaderTexture(0, ClientResourceLocations.MIDSANE);
			Tesselator tesselator = Tesselator.getInstance();
			BufferBuilder bufferbuilder = tesselator.getBuilder();
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.vertex(d3, d2, -90.0D).uv(0.0F, 1.0F).endVertex();
			bufferbuilder.vertex(d1, d2, -90.0D).uv(1.0F, 1.0F).endVertex();
			bufferbuilder.vertex(d1, d4, -90.0D).uv(1.0F, 0.0F).endVertex();
			bufferbuilder.vertex(d3, d4, -90.0D).uv(0.0F, 0.0F).endVertex();
			tesselator.end();
			
			//Determines if you are more sane or insane and assigns a texture
			float sanityModifier = 2 * (playerSanity - 0.5F);
			if(sanityModifier > 0)
			{
				RenderSystem.setShaderTexture(0, ClientResourceLocations.SANE);
			}	
			else
			{
				RenderSystem.setShaderTexture(0, ClientResourceLocations.INSANE);
				sanityModifier *= -1;
			}
		
			//Sets your sanity alpha level and renders your sanity face
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, sanityModifier);
			tesselator = Tesselator.getInstance();
			bufferbuilder = tesselator.getBuilder();
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.vertex(d3, d2, -90.0D).uv(0.0F, 1.0F).endVertex();
			bufferbuilder.vertex(d1, d2, -90.0D).uv(1.0F, 1.0F).endVertex();
			bufferbuilder.vertex(d1, d4, -90.0D).uv(1.0F, 0.0F).endVertex();
			bufferbuilder.vertex(d3, d4, -90.0D).uv(0.0F, 0.0F).endVertex();
			tesselator.end();
			

			//Renders arrows if you are increasing/decreasing in sanity
			size = 3;
			i += 6;
			float arrowStrength = ClientSanityData.getArrowStrength();
			if(ClientSanityData.isIncreasing())
			{
				j2 -= 4;
				d1 = i + size;
				d2 = j2 + size;
				d3 = i - size;
				d4 = j2 - size;
				RenderSystem.setShaderColor(1.0F, 0.415F, 0.0F, arrowStrength);
				RenderSystem.setShaderTexture(0, ClientResourceLocations.ARROW);
				tesselator = Tesselator.getInstance();
				bufferbuilder = tesselator.getBuilder();
				bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
				bufferbuilder.vertex(d3, d2, -90.0D).uv(0.0F, 1.0F).endVertex();
				bufferbuilder.vertex(d1, d2, -90.0D).uv(1.0F, 1.0F).endVertex();
				bufferbuilder.vertex(d1, d4, -90.0D).uv(1.0F, 0.0F).endVertex();
				bufferbuilder.vertex(d3, d4, -90.0D).uv(0.0F, 0.0F).endVertex();
				tesselator.end();
			}
			else if(ClientSanityData.isDecreasing())
			{
				j2 += 4;
				d1 = i + size;
				d2 = j2 + size;
				d3 = i - size;
				d4 = j2 - size;
				RenderSystem.setShaderColor(0.0F, 0.58F, 1.0F, arrowStrength);
				RenderSystem.setShaderTexture(0, ClientResourceLocations.ARROW);
				tesselator = Tesselator.getInstance();
				bufferbuilder = tesselator.getBuilder();
				bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
				bufferbuilder.vertex(d3, d2, -90.0D).uv(0.0F, 0.0F).endVertex();
				bufferbuilder.vertex(d1, d2, -90.0D).uv(1.0F, 0.0F).endVertex();
				bufferbuilder.vertex(d1, d4, -90.0D).uv(1.0F, 1.0F).endVertex();
				bufferbuilder.vertex(d3, d4, -90.0D).uv(0.0F, 1.0F).endVertex();
				tesselator.end();
			}
		      
			
			//Renders your insanity halo if you are insane
			if(playerSanity <= 0.5F)
			{
				sanityModifier = 1.0F - 2 * playerSanity;
				i = window.getGuiScaledWidth();
				j = window.getGuiScaledHeight();
				double d0 = Mth.lerp((double)sanityModifier, 2.25D, 1.3D);
				float f1 = 0.2F * sanityModifier;
				float f = 0.6F * sanityModifier;
				float f2 = 0.2F * sanityModifier;
				d1 = (double)i * d0;
				d2 = (double)j * d0;
				d3 = ((double)i - d1) / 2.0D;
				d4 = ((double)j - d2) / 2.0D;
				RenderSystem.enableBlend();
				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
				RenderSystem.setShaderColor(f, f1, f2, 1F);
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, ClientResourceLocations.INSANITY_OVERLAY);
				tesselator = Tesselator.getInstance();
				bufferbuilder = tesselator.getBuilder();
				bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
				bufferbuilder.vertex(d3, d4 + d2, -90.0D).uv(0.0F, 1.0F).endVertex();
				bufferbuilder.vertex(d3 + d1, d4 + d2, -90.0D).uv(1.0F, 1.0F).endVertex();
				bufferbuilder.vertex(d3 + d1, d4, -90.0D).uv(1.0F, 0.0F).endVertex();
				bufferbuilder.vertex(d3, d4, -90.0D).uv(0.0F, 0.0F).endVertex();
				tesselator.end();
			}
			//Returns things to normal so we don't mess other stuff up
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
		}	
	}	
}
