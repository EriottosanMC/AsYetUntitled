package asyetuntitled.common.item;

import asyetuntitled.common.armor.BaseArmorMaterial;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public final class ArmorMaterialInit {

		private ArmorMaterialInit() {
			
		}
		
		public static final ArmorMaterial BACKPACK_SIMPLE = new BaseArmorMaterial(0,
				new int[] {0, 0, 50, 0}, new int[] {0, 0, 0, 0}, 0f, 1f, 
				"backpack_simple", SoundEvents.ARMOR_EQUIP_LEATHER, 
				() -> Ingredient.of(ItemsRegistry.BACKPACK_BASE.get()));
}
