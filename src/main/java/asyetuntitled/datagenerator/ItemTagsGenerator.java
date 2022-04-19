package asyetuntitled.datagenerator;

import asyetuntitled.AsYetUntitled;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagsGenerator extends ItemTagsProvider {

    public ItemTagsGenerator(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, AsYetUntitled.MODID, helper);
    }

    @Override
    protected void addTags() {
//        tag(Tags.Items.ORES)
//                .add(UMMItems.MYSTERIOUS_ORE_OVERWORLD_ITEM.get())
//                .add(Registration.MYSTERIOUS_ORE_NETHER_ITEM.get())
//                .add(Registration.MYSTERIOUS_ORE_END_ITEM.get())
//                .add(Registration.MYSTERIOUS_ORE_DEEPSLATE_ITEM.get());
    }

    @Override
    public String getName() {
        return "UMM Tags";
    }
}
