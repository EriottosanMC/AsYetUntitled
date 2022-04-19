package asyetuntitled.common.util.capability;

import java.util.List;

import com.google.common.collect.Lists;

import asyetuntitled.common.entity.livingchest.LivingChest;
import net.minecraft.world.entity.player.Player;

public class LevelChests 
{
	private List<LivingChest> chests = Lists.newArrayList();
	
	public void addChest(LivingChest livingChest)
	{
		this.chests.add(livingChest);
	}
	
	public LivingChest getChest(String uuid)
	{
		for(LivingChest chest: chests)
		{
			if(uuid.contains(chest.getStringUUID()))
			{
				return chest;
			}
		}
		return null;
	}
	
	public void removeChest(LivingChest chest)
	{
		chests.removeIf(chest2 -> chest2.getStringUUID() == chest.getStringUUID());
	}
	
	public void printChests()
	{
		for(LivingChest chest: chests)
		{
			System.out.println(chest.getStringUUID() + ";" + chest.blockPosition());
		}
	}
	
	public LivingChest getPlayerChest(Player player)
	{
		for(LivingChest chest : chests)
		{
			if(chest.getOwnerUUID() == player.getUUID())
			{
				return chest;
			}
		}
		return null;
	}
	
}
