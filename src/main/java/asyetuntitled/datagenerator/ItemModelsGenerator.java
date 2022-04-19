package asyetuntitled.datagenerator;

import asyetuntitled.AsYetUntitled;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelsGenerator extends ItemModelProvider {

    public ItemModelsGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AsYetUntitled.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        withExistingParent(Registration.MYSTERIOUS_ORE_OVERWORLD_ITEM.get().getRegistryName().getPath(), modLoc("block/mysterious_ore_overworld"));
//        withExistingParent(Registration.MYSTERIOUS_ORE_NETHER_ITEM.get().getRegistryName().getPath(), modLoc("block/mysterious_ore_nether"));
//        withExistingParent(Registration.MYSTERIOUS_ORE_END_ITEM.get().getRegistryName().getPath(), modLoc("block/mysterious_ore_end"));
//        withExistingParent(Registration.MYSTERIOUS_ORE_DEEPSLATE_ITEM.get().getRegistryName().getPath(), modLoc("block/mysterious_ore_deepslate"));
//    	withExistingParent(UMMItems.DUMMY_RUNE.get().getRegistryName().getPath(), modLoc("item/dummy_rune"));
    	
//    	singleTexture(UMMItems.DUMMY_RUNE.get().getRegistryName().getPath(),
//                mcLoc("item/generated"),
//                "layer0", modLoc("item/dummy_rune"));
//    	singleTexture(UMMItems.BACKPACK_SIMPLE.get().getRegistryName().getPath(),
//                 mcLoc("item/generated"),
//                 "layer0", modLoc("item/backpack_nether"));
//    	 singleTexture(UMMItems.BLANK_SLOT.get().getRegistryName().getPath(),
//                 mcLoc("item/generated"),
//                 "layer0", modLoc("item/blank_slot"));
    	
//    	withExistingParent(UMMItems.DUMMY_RUNE.get().getRegistryName().getPath(), modLoc("item/dummy_rune"));
    	
//    	registerSingleTexture(UMMItems.BLANK_SLOT.get());
//    	String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
//    			"v", "w", "x", "y", "z"};
//    	for(String a : letters)
//    	{
//    		singleTexture("dummy_rune_" + a, mcLoc("item/generated"), "layer0", modLoc("item/" + "sga_" + a));
//    	}

    }
    
    @SuppressWarnings("unused")
	private void registerSingleTexture(Item item)
    {
    	singleTexture(item.getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + item.getRegistryName().getPath()));
    }
}