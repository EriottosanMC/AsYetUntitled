package asyetuntitled.common.item;


import java.util.function.Consumer;

import asyetuntitled.client.render.item.BackpackItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.util.NonNullLazy;

public class ItemBackpack extends ArmorItem implements DyeableLeatherItem
{
	private final int SIZE;

	public ItemBackpack(Properties prop, int size)
	{
		super(ArmorMaterialInit.BACKPACK_SIMPLE, EquipmentSlot.CHEST, prop);
		this.SIZE = size;
	}
	
	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer)
	{
		consumer.accept(new IItemRenderProperties() {
			private final NonNullLazy<BlockEntityWithoutLevelRenderer> ister = NonNullLazy.of(() -> new BackpackItemStackRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));
			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {

				return ister.get();
			}
		});
	}
	
	public int getSize()
	{
		return this.SIZE;
	}
	
	public NonNullList<ItemStack> getParts(ItemStack stack)
	{
		CompoundTag tag = stack.getOrCreateTag();
		int partsSize = tag.getInt("partsSize");
		NonNullList<ItemStack> parts = NonNullList.withSize(partsSize, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag.getCompound("parts"), parts);
		if(partsSize == 0)
		{
			parts = NonNullList.withSize(7, ItemStack.EMPTY);
			ItemStack chest = new ItemStack(Items.DIAMOND_CHESTPLATE);
			chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
			chest.setDamageValue(40);
			parts.set(0, chest);
			parts.set(1, new ItemStack(ItemsRegistry.BACKPACK_MAIN.get()));
			parts.set(2, new ItemStack(ItemsRegistry.BACKPACK_TOP.get()));
			parts.set(3, new ItemStack(ItemsRegistry.BACKPACK_FRONT.get()));
			parts.set(4, new ItemStack(ItemsRegistry.BACKPACK_MAIN_BUTTONS.get()));
			parts.set(5, new ItemStack(ItemsRegistry.BACKPACK_TOP_BUTTONS.get()));
			parts.set(6, new ItemStack(ItemsRegistry.BACKPACK_FRONT_BUTTONS.get()));
		}
		return parts;
	}
	
	public void setParts(ItemStack stack, NonNullList<ItemStack> parts)
	{
		CompoundTag compound = stack.getOrCreateTag();
		CompoundTag tag = new CompoundTag();
		tag.putInt("partsSize", parts.size());
		ContainerHelper.saveAllItems(tag, parts);
		compound.put("parts", tag);
	}
	
	public boolean addPart(ItemStack backpack, ItemStack newPart)
	{
		NonNullList<ItemStack> parts = this.getParts(backpack);
		boolean flag = false;
		for(int i = 0; i < parts.size() && !flag; i++)
		{
			ItemStack part = parts.get(i);
			if(part.isEmpty())
			{
				parts.set(0, newPart);
				this.setParts(backpack, parts);
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean isSameBackpackButHurtBestGuess(ItemStack backpackOne, ItemStack backpackTwo)
	{
		if(!backpackTwo.isDamaged())
		{
			return false;
		}
		else
		{
			ItemStack tmp = backpackTwo.copy();
			tmp.setDamageValue(tmp.getDamageValue() - 1);
			return tmp.equals(backpackOne, true);
		}
	}
}
