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
		event.getGeneration().getFeatures(Decoration.LOCAL_MODIFICATIONS).forEach(feature -> System.out.println("Local: " + feature));
		event.getGeneration().getFeatures(Decoration.TOP_LAYER_MODIFICATION).forEach(feature -> System.out.println("Top Layer: " + feature));
		event.getGeneration().getFeatures(Decoration.RAW_GENERATION).forEach(feature -> System.out.println("Raw : " + feature));
		event.getGeneration().getFeatures(Decoration.SURFACE_STRUCTURES).forEach(feature -> System.out.println("Surface: " + feature));
		event.getGeneration().getFeatures(Decoration.UNDERGROUND_STRUCTURES).forEach(feature -> System.out.println("Underground structures: " + feature));
		event.getGeneration().getFeatures(Decoration.FLUID_SPRINGS).forEach(feature -> System.out.println("Fluid: " + feature));
		event.getGeneration().getFeatures(Decoration.LAKES).forEach(feature -> System.out.println("Lakes: " + feature));
		event.getGeneration().getFeatures(Decoration.STRONGHOLDS).forEach(feature -> System.out.println("Strongholds: " + feature));
		event.getGeneration().getFeatures(Decoration.UNDERGROUND_DECORATION).forEach(feature -> System.out.println("Underground decoration: " + feature));
		event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).forEach(feature -> System.out.println("Underground ores: " + feature));
	}  
	
}
