package asyetuntitled.common.particle;

import asyetuntitled.AsYetUntitled;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticlesRegistry 
{
	private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AsYetUntitled.MODID);

	public static final RegistryObject<ParticleType<SimpleParticleType>> SPOOKY_EYES = PARTICLES.register("spookyeyes", () -> new SimpleParticleType(true));

	
	public static void register(IEventBus modBus) 
	{
		PARTICLES.register(modBus);
	}
}
