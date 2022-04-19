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

	public static final RegistryObject<SoundEvent> CHARLIE_MOOD = SOUNDS.register("charlie_mood", () -> new SoundEvent(ClientResourceLocations.CHARLIE_MOOD));
	public static final RegistryObject<SoundEvent> CHARLIE_SCARE = SOUNDS.register("charlie_scare", () -> new SoundEvent(ClientResourceLocations.CHARLIE_SCARE));
	public static final RegistryObject<SoundEvent> CHARLIE_ATTACK = SOUNDS.register("charlie_attack", () -> new SoundEvent(ClientResourceLocations.CHARLIE_ATTACK));
	public static final RegistryObject<SoundEvent> OMINOUS_WHISPER = SOUNDS.register("ominous_whisper", () -> new SoundEvent(ClientResourceLocations.OMINOUS_WHISPER));
	
	public static void register(IEventBus bus) 
	{
		SOUNDS.register(bus);
	}
	
}
