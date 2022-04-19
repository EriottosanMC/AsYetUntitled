package asyetuntitled.datagenerator;

import asyetuntitled.AsYetUntitled;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new RecipesGenerator(generator));
//            generator.addProvider(new LootTablesGenerator(generator));
            BlockTagsGenerator blockTags = new BlockTagsGenerator(generator, event.getExistingFileHelper());
            generator.addProvider(blockTags);
            generator.addProvider(new ItemTagsGenerator(generator, blockTags, event.getExistingFileHelper()));
        }
        if (event.includeClient()) {
            generator.addProvider(new BlockStatesGenerator(generator, event.getExistingFileHelper()));
            generator.addProvider(new ItemModelsGenerator(generator, event.getExistingFileHelper()));
            generator.addProvider(new LanguageProviderGenerator(generator, "en_us"));
        }
    }
}