package asyetuntitled.common.util;

import asyetuntitled.AsYetUntitled;
import net.minecraft.resources.ResourceLocation;

public class CommonResourceLocations {

	//Capabilities
	public static ResourceLocation LEVEL_CHEST_CAPABILITY = new ResourceLocation(AsYetUntitled.MODID, "levelchests");
	public static ResourceLocation LEVEL_SPAWNS_CAPABILITY = new ResourceLocation(AsYetUntitled.MODID, "levelspawns");
	public static ResourceLocation PLAYER_DARKNESS_CAPABILITY = new ResourceLocation(AsYetUntitled.MODID, "playerdarkness");
	public static ResourceLocation PLAYER_SANITY_CAPABILITY = new ResourceLocation(AsYetUntitled.MODID, "playersanity");
	
	//Messages
	public static ResourceLocation MESSAGES = new ResourceLocation(AsYetUntitled.MODID, "messages");
	
	//Tags
	public static ResourceLocation DIGGABLE_BY_HAND = new ResourceLocation(AsYetUntitled.MODID, "diggable_by_hand");

	//Sounds (Sounds get passed from the server sometimes so need to be common)
    public static ResourceLocation CHARLIE_MOOD = new ResourceLocation(AsYetUntitled.MODID, "charlie_mood");
    public static ResourceLocation CHARLIE_SCARE = new ResourceLocation(AsYetUntitled.MODID, "charlie_scare");
    public static ResourceLocation CHARLIE_ATTACK = new ResourceLocation(AsYetUntitled.MODID, "charlie_attack");
    public static ResourceLocation OMINOUS_WHISPER = new ResourceLocation(AsYetUntitled.MODID, "ominous_whisper");

    public static ResourceLocation SHADOW_SPIDER_AMBIENT = new ResourceLocation(AsYetUntitled.MODID, "shadow_spider_ambient");
    public static ResourceLocation SHADOW_SPIDER_HURT = new ResourceLocation(AsYetUntitled.MODID, "shadow_spider_hurt");
    public static ResourceLocation SHADOW_SPIDER_DEATH = new ResourceLocation(AsYetUntitled.MODID, "shadow_spider_death");
    public static ResourceLocation SHADOW_SPIDER_STEP = new ResourceLocation(AsYetUntitled.MODID, "shadow_spider_step");
    public static ResourceLocation SHADOW_CHICKEN_AMBIENT = new ResourceLocation(AsYetUntitled.MODID, "shadow_chicken_ambient");
    public static ResourceLocation SHADOW_CHICKEN_HURT = new ResourceLocation(AsYetUntitled.MODID, "shadow_chicken_hurt");
    public static ResourceLocation SHADOW_CHICKEN_DEATH = new ResourceLocation(AsYetUntitled.MODID, "shadow_chicken_death");
    public static ResourceLocation SHADOW_CHICKEN_STEP = new ResourceLocation(AsYetUntitled.MODID, "shadow_chicken_step");

}
