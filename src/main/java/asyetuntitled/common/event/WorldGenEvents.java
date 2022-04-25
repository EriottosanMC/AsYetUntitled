package asyetuntitled.common.event;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.world.worldgen.ChangedFeature;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.FORGE)
public class WorldGenEvents {

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public static void onEvent(BiomeLoadingEvent event)
	{
		event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION).replaceAll(new ChangedFeature());
	}  
	
}
