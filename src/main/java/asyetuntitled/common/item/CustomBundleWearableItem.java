package asyetuntitled.common.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class CustomBundleWearableItem extends ArmorItem
{

    public CustomBundleWearableItem(ArmorMaterial mat, Properties prop)
    {
        super(mat, EquipmentSlot.CHEST, prop);
    }

}
