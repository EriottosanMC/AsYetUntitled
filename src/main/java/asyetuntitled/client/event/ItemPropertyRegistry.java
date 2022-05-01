package asyetuntitled.client.event;

import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.item.ItemsRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;

public class ItemPropertyRegistry
{

    public static void register()
    {
        ItemProperties.register(ItemsRegistry.DUMMY_RUNE.get(), ClientResourceLocations.ALPHABET, (stack, level, living, id) -> {
            CompoundTag tag = stack.getOrCreateTag();
            float f = 0.0F;
            if(tag.contains("alphabet"))
            {
                f = tag.getFloat("alphabet");
            }
            return f;
        });        
    }

}
