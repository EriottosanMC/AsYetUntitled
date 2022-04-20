package asyetuntitled.client.util;

import asyetuntitled.AsYetUntitled;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@OnlyIn(Dist.CLIENT)
public class SoundsRegistry {
	
	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AsYetUntitled.MODID);

	//Sanity Sounds
	public static final RegistryObject<SoundEvent> CHARLIE_MOOD = SOUNDS.register("charlie_mood", () -> new SoundEvent(ClientResourceLocations.CHARLIE_MOOD));
	public static final RegistryObject<SoundEvent> CHARLIE_SCARE = SOUNDS.register("charlie_scare", () -> new SoundEvent(ClientResourceLocations.CHARLIE_SCARE));
	public static final RegistryObject<SoundEvent> CHARLIE_ATTACK = SOUNDS.register("charlie_attack", () -> new SoundEvent(ClientResourceLocations.CHARLIE_ATTACK));
	public static final RegistryObject<SoundEvent> OMINOUS_WHISPER = SOUNDS.register("ominous_whisper", () -> new SoundEvent(ClientResourceLocations.OMINOUS_WHISPER));
	
	//Mob Sounds
	public static final RegistryObject<SoundEvent> SHADOW_SPIDER_AMBIENT = SOUNDS.register("shadow_spider_ambient", () -> new SoundEvent(ClientResourceLocations.SHADOW_SPIDER_AMBIENT));
	public static final RegistryObject<SoundEvent> SHADOW_SPIDER_HURT = SOUNDS.register("shadow_spider_hurt", () -> new SoundEvent(ClientResourceLocations.SHADOW_SPIDER_HURT));
    public static final RegistryObject<SoundEvent> SHADOW_SPIDER_DEATH = SOUNDS.register("shadow_spider_death", () -> new SoundEvent(ClientResourceLocations.SHADOW_SPIDER_DEATH));
    public static final RegistryObject<SoundEvent> SHADOW_SPIDER_STEP = SOUNDS.register("shadow_spider_step", () -> new SoundEvent(ClientResourceLocations.SHADOW_SPIDER_STEP));
    public static final RegistryObject<SoundEvent> SHADOW_CHICKEN_AMBIENT = SOUNDS.register("shadow_chicken_ambient", () -> new SoundEvent(ClientResourceLocations.SHADOW_CHICKEN_AMBIENT));
    public static final RegistryObject<SoundEvent> SHADOW_CHICKEN_HURT= SOUNDS.register("shadow_chicken_hurt", () -> new SoundEvent(ClientResourceLocations.SHADOW_CHICKEN_HURT));
    public static final RegistryObject<SoundEvent> SHADOW_CHICKEN_DEATH = SOUNDS.register("shadow_chicken_death", () -> new SoundEvent(ClientResourceLocations.SHADOW_CHICKEN_DEATH));
    public static final RegistryObject<SoundEvent> SHADOW_CHICKEN_STEP = SOUNDS.register("shadow_chicken_step", () -> new SoundEvent(ClientResourceLocations.SHADOW_CHICKEN_STEP));

	
	public static void register(IEventBus bus) 
	{
		SOUNDS.register(bus);
	}
	
}
