package asyetuntitled.common.event;

import java.util.stream.Collectors;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.entity.ShadowChicken;
import asyetuntitled.common.entity.ShadowSpider;
import asyetuntitled.common.entity.livingchest.LivingChest;
import asyetuntitled.common.messages.MessagesRegistry;
import asyetuntitled.common.util.BlockChange;
import asyetuntitled.common.util.CommonReflectionHelper;
import asyetuntitled.common.util.capability.LevelChests;
import asyetuntitled.common.util.capability.LevelSpawns;
import asyetuntitled.common.util.capability.PlayerDarkness;
import asyetuntitled.common.util.capability.PlayerSanity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.MOD)
public class ModEventsHandler 
{

    // Common Setup Event.
    @SubscribeEvent
    public static void setupCommon(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            //Set up CommonReflectionHelper and BlockSpeeds
            MessagesRegistry.register();
            CommonReflectionHelper.init();
            BlockChange.init();
        });
        
   }

    @SubscribeEvent
    public static void enqueueIMC(InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("asyetuntitled", "helloworld", () -> { AsYetUntitled.LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    @SubscribeEvent
    public static void processIMC(InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        AsYetUntitled.LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }
    
    
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event)
	{
		event.put(EntityRegistry.LIVING_CHEST.get(), LivingChest.createAttributes().build());
		event.put(EntityRegistry.SHADOW_SPIDER.get(), ShadowSpider.createAttributes().build());
		event.put(EntityRegistry.SHADOW_CHICKEN.get(), ShadowChicken.createAttributes().build());
	}
	
	@SubscribeEvent
	public static void registerCaps(RegisterCapabilitiesEvent event)
	{
		event.register(LevelChests.class);
		event.register(LevelSpawns.class);
		event.register(PlayerDarkness.class);
		event.register(PlayerSanity.class);
	}
}
