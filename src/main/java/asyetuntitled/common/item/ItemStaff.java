package asyetuntitled.common.item;

import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.entity.livingchest.LivingChest;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemStaff extends Item
{
	public ItemStaff(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level level, Player player) 
	{
		this.summon(stack, level, player);
		
	}
	
	private void summon(ItemStack stack, Level level, Entity entity) 
	{
		if(!entity.level.isClientSide)
		{	LivingChest chest = new LivingChest(EntityRegistry.LIVING_CHEST.get(), level);
			chest.setPos(entity.getX() + level.random.nextInt(21) - 10, entity.getEyeY(), entity.getZ() + level.random.nextInt(21) -  10);
			if(entity instanceof Player)
			{
				chest.setOwnerUUID(((Player) entity).getUUID());
				chest.setTame(true);
			}
			entity.level.addFreshEntity(chest);
			chest.setRandomName();
			this.setChestUUID(stack, chest.getStringUUID());
		}
	}

	public String getChestUUID(ItemStack stack)
	{
		CompoundTag tag = stack.getOrCreateTagElement("chest");
		String uuid = tag.getString("uuid");
		
		return uuid;
	}
	
	public void setChestUUID(ItemStack stack, String uuid)
	{
		CompoundTag tag = stack.getOrCreateTagElement("chest");
		tag.putString("uuid", uuid);
	}
	
	@Override
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
//		System.out.println(this.getChestUUID(p_41404_));
	}
}
