package asyetuntitled.common.world.worldgen;

import asyetuntitled.AsYetUntitled;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StructuresRegistry 
{
	private static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, AsYetUntitled.MODID);
	
	public static final RegistryObject<StructureFeature<JigsawConfiguration>> TOUCHSTONE = STRUCTURES.register("touchstone", TouchstoneStructure::new);
    public static void registerStructures(IEventBus bus) 
    {
        STRUCTURES.register(bus);
    }
}
