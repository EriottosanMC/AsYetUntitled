package asyetuntitled.common.entity;

import asyetuntitled.AsYetUntitled;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
	
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, AsYetUntitled.MODID);
	
	public static final RegistryObject<EntityType<ShadowSpider>> SHADOW_SPIDER = ENTITIES.register("shadow_spider", 
			() -> EntityType.Builder.<ShadowSpider>of(ShadowSpider::new, MobCategory.MONSTER)
			.sized(0.8F, 0.8F)
			.clientTrackingRange(10)
			.build("shadow_spider"));
	
	public static final RegistryObject<EntityType<ShadowChicken>> SHADOW_CHICKEN = ENTITIES.register("shadow_chicken", 
			() -> EntityType.Builder.<ShadowChicken>of(ShadowChicken::new, MobCategory.MONSTER)
			.sized(0.8F, 0.8F)
			.clientTrackingRange(10)
			.build("shadow_chicken"));
	
	public static void registerEntities(IEventBus bus) 
	{
		ENTITIES.register(bus);
	}
	
	
}
