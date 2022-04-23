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
	
	//Item Properties (predicates for rendering)
	public static ResourceLocation ALPHABET = new ResourceLocation(AsYetUntitled.MODID, "alphabet");
	
	//Screens
	public static ResourceLocation LIVING_CHEST_SCREEN = new ResourceLocation(AsYetUntitled.MODID, "textures/gui/living_chest.gui");
	
	//Shaders
	public static ResourceLocation SANITY_SHADER = new ResourceLocation(AsYetUntitled.MODID, "shaders/sanity.json");
	
	//Entity Models
	public static ResourceLocation STAFF_MODEL = new ResourceLocation(AsYetUntitled.MODID, "staff");
	public static ResourceLocation LIVING_CHEST_MODEL= new ResourceLocation(AsYetUntitled.MODID,  "living_chest");
	public static ResourceLocation BACKPACK_MODEL = new ResourceLocation(AsYetUntitled.MODID, "backpack");
	private static ResourceLocation SHADOW_CHICKEN_MODEL = new ResourceLocation(AsYetUntitled.MODID, "shadow_chicken");
	public static ResourceLocation SHADOW_SPIDER_MODEL  = new ResourceLocation(AsYetUntitled.MODID,  "shadow_spider");
	public static ResourceLocation BACKPACK_LAYER = new ResourceLocation(AsYetUntitled.MODID, "backpack");

	//Entity Textures
	public static ResourceLocation BACKPACK_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/models/armor/backpack.png");
	public static ResourceLocation STAFF_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/entities/staff_closed.png");
	public static ResourceLocation SHADOW_CHICKEN_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/entities/shadow_chicken.png");
	public static ResourceLocation SHADOW_SPIDER_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/entities/shadow_spider.png");
	public static ResourceLocation LIVING_CHEST_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/entities/living_chest.png");

	//Entity Model Layers
	public static ModelLayerLocation WEARABLE_BACKPACK_MODEL_LAYER = new ModelLayerLocation(ClientResourceLocations.BACKPACK_LAYER, "backpack");
	public static ModelLayerLocation STAFF_MODEL_LAYER = new ModelLayerLocation(ClientResourceLocations.STAFF_MODEL, "staff");
	public static ModelLayerLocation SHADOW_SPIDER_MODEL_LAYER = new ModelLayerLocation(ClientResourceLocations.SHADOW_SPIDER_MODEL, "shadow_spider");
	public static ModelLayerLocation SHADOW_CHICKEN_MODEL_LAYER = new ModelLayerLocation(ClientResourceLocations.SHADOW_CHICKEN_MODEL, "shadow_chicken");
	public static ModelLayerLocation LIVING_CHEST_MODEL_LAYER = new ModelLayerLocation(ClientResourceLocations.LIVING_CHEST_MODEL, "living_chest");
	public static ModelLayerLocation BACKPACK_MODEL_LAYER = new ModelLayerLocation(ClientResourceLocations.BACKPACK_MODEL, "main");

	//ItemStack Loaders
	public static ResourceLocation BACKPACK_RENDER_LOADER = new ResourceLocation(AsYetUntitled.MODID, "backpack_loader");
	
	//Is this needed?
//	public static ResourceLocation BLANK_SHADOW_SPIDER_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "/textures/entities/shadow_spider_blank.png");
//	public static ResourceLocation BACKPACK_TEXTURE_BLANK = new ResourceLocation(AsYetUntitled.MODID, "textures/entities/backpack.png");
//	public static ResourceLocation BACKPACK_ITEM_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "item/backpack_simple");
//	public static ResourceLocation STAFF_OPEN = new ResourceLocation(AsYetUntitled.MODID, "textures/entities/staff_open.png");
	
	//Blank
	public static ResourceLocation BLANK_TEXTURE = new ResourceLocation(AsYetUntitled.MODID, "textures/blank.png");
}
