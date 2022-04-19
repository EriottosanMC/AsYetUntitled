package asyetuntitled.common.event;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.entity.ShadowChicken;
import asyetuntitled.common.entity.ShadowSpider;
import asyetuntitled.common.util.capability.PlayerDarkness;
import asyetuntitled.common.util.capability.PlayerSanity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.MOD)
public class ModEventsHandler {
	
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event)
	{
		event.put(EntityRegistry.SHADOW_SPIDER.get(), ShadowSpider.createAttributes().build());
		event.put(EntityRegistry.SHADOW_CHICKEN.get(), ShadowChicken.createAttributes().build());
	}
	
	@SubscribeEvent
	public static void registerCaps(RegisterCapabilitiesEvent event)
	{
		event.register(PlayerDarkness.class);
		event.register(PlayerSanity.class);
	}
}
