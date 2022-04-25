package asyetuntitled.client.event;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.sanity.ClientSanityData;
import asyetuntitled.client.util.ClientReflectionHelper;
import asyetuntitled.client.util.RendererChanger;
import asyetuntitled.common.item.ItemBackpack;
import asyetuntitled.common.messages.ClientThoughtsData;
import asyetuntitled.client.util.ClientResourceLocations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
			if(ClientThoughtsData.displayTime > 0)
			{
		         renderThoughts(event.getWindow(), event.getMatrixStack());
			}
		}
	}
	
	private static void fillRect(BufferBuilder p_115153_, int p_115154_, int p_115155_, int p_115156_, int p_115157_, int p_115158_, int p_115159_, int p_115160_, int p_115161_) {
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		p_115153_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		p_115153_.vertex((double)(p_115154_ + 0), (double)(p_115155_ + 0), 0.0D).color(p_115158_, p_115159_, p_115160_, p_115161_).endVertex();
		p_115153_.vertex((double)(p_115154_ + 0), (double)(p_115155_ + p_115157_), 0.0D).color(p_115158_, p_115159_, p_115160_, p_115161_).endVertex();
		p_115153_.vertex((double)(p_115154_ + p_115156_), (double)(p_115155_ + p_115157_), 0.0D).color(p_115158_, p_115159_, p_115160_, p_115161_).endVertex();
		p_115153_.vertex((double)(p_115154_ + p_115156_), (double)(p_115155_ + 0), 0.0D).color(p_115158_, p_115159_, p_115160_, p_115161_).endVertex();
		p_115153_.end();
		BufferUploader.end(p_115153_);
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
	
	//Renders thoughts
    private static void renderThoughts(Window window, PoseStack stack)
    {
        Minecraft mc = Minecraft.getInstance();
        List<TranslatableComponent> messages = ClientThoughtsData.getMessageSplit();
        float f = ClientThoughtsData.displayTime > 10 ? 1.0F : ClientThoughtsData.displayTime / 10F;
      
        for(int i = 0 ; i < messages.size() ; i++)
        {
            TranslatableComponent message = messages.get(i);
            float width = mc.font.width(message.getVisualOrderText()) / 2;
            int mid = window.getGuiScaledWidth() / 2;
            int alpha = Math.round(f * 255);
            
            stack.pushPose();
            stack.translate(0.0D, 0.0D, 50.0D);
            RenderSystem.enableBlend();
            mc.font.drawShadow(stack, message, mid - width, 40 + 10*i, 16777215 + (alpha << 24));
            RenderSystem.disableBlend();
            stack.popPose();    
        }
        ClientThoughtsData.displayTime--;
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
			Minecraft mc = Minecraft.getInstance();
			Player player = mc.player;
			ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
			if(chest.getItem() instanceof ItemBackpack backpack)
			{
				List<ItemStack> parts = backpack.getParts(chest);
				for(ItemStack part : parts)
				{
					if(part.isBarVisible())
					{
						RenderSystem.disableDepthTest();
						RenderSystem.disableTexture();
						RenderSystem.disableBlend();
						Tesselator tesselator = Tesselator.getInstance();
						BufferBuilder bufferbuilder = tesselator.getBuilder();
						int i = part.getBarWidth();
						int j = part.getBarColor();
						int x = screen.getGuiLeft() + 10;
						int y = screen.getGuiTop() + 40;
						fillRect(bufferbuilder, x, y, 13, 2, 0, 0, 0, 255);
						fillRect(bufferbuilder, x, y, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
						RenderSystem.enableBlend();
						RenderSystem.enableTexture();
						RenderSystem.enableDepthTest();
					}
				}
			}
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
