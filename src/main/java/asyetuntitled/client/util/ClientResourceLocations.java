package asyetuntitled.client.util;

import asyetuntitled.AsYetUntitled;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientResourceLocations 
{

	//Sanity Textures
	public static ResourceLocation SANE = new ResourceLocation(AsYetUntitled.MODID, "textures/effect/sane.png");
	public static ResourceLocation MIDSANE = new ResourceLocation(AsYetUntitled.MODID, "textures/effect/midsane.png");
	public static ResourceLocation INSANE = new ResourceLocation(AsYetUntitled.MODID, "textures/effect/insane.png");
	public static ResourceLocation INSANITY_OVERLAY = new ResourceLocation(AsYetUntitled.MODID, "textures/effect/insanity.png");
	public static ResourceLocation ARROW = new ResourceLocation(AsYetUntitled.MODID, "textures/effect/arrow.png");
	
	//Sounds
	public static ResourceLocation CHARLIE_MOOD = new ResourceLocation(AsYetUntitled.MODID, "charlie_mood");
	public static ResourceLocation CHARLIE_SCARE = new ResourceLocation(AsYetUntitled.MODID, "charlie_scare");
	public static ResourceLocation CHARLIE_ATTACK = new ResourceLocation(AsYetUntitled.MODID, "charlie_attack");
	public static ResourceLocation OMINOUS_WHISPER = new ResourceLocation(AsYetUntitled.MODID, "ominous_whisper");

	//Shaders
	public static ResourceLocation SANITY_SHADER = new ResourceLocation(AsYetUntitled.MODID, "shaders/sanity.json");
	
	//Entity Models
	private static ResourceLocation SHADOW_CHICKEN_MODEL = new ResourceLocation(AsYetUntitled.MODID, "shadow_chicken");
	public static ResourceLocation SHADOW_SPIDER_MODEL  = new ResourceLocation(AsYetUntitled.MODID,  "shadow_spider");

	//Entity Textures
	public static ResourceLocation SHADOW_CHICKEN_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/entities/shadow_chicken.png");
	public static ResourceLocation SHADOW_SPIDER_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/entities/shadow_spider.png");

	//Entity Model Layers
	public static ModelLayerLocation SHADOW_SPIDER_MODEL_LAYER = new ModelLayerLocation(ClientResourceLocations.SHADOW_SPIDER_MODEL, "shadow_spider");
	public static ModelLayerLocation SHADOW_CHICKEN_MODEL_LAYER = new ModelLayerLocation(ClientResourceLocations.SHADOW_CHICKEN_MODEL, "shadow_chicken");

	//Blank
	public static ResourceLocation BLANK_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/blank.png");
}
