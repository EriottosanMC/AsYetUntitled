package asyetuntitled.common.entity;

import asyetuntitled.client.sanity.ClientSanityData;
import asyetuntitled.common.util.capability.PlayerSanity;
import asyetuntitled.common.util.capability.PlayerSanityProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ShadowCreature
{
	float getSanityThreshold();
	
	float getFullStrengthThreshold();
	
	default boolean canPlayerSee(Player player)
	{
		Level level = player.level;
		if(level.isClientSide)
		{
			return ClientSanityData.getPlayerSanity() < this.getSanityThreshold();
		}
		else
		{
			float sanity = player.getCapability(PlayerSanityProvider.PLAYER_SANITY).map(PlayerSanity::getSanity).get();
			return sanity < this.getSanityThreshold();
		}
	}
	
	default float getSanityByThreshold(Player player)
	{
		Level level = player.level;
		float sanity = 0.0F;
		if(level.isClientSide)
		{
			sanity = ClientSanityData.getPlayerSanity();
		}
		else
		{
			sanity = player.getCapability(PlayerSanityProvider.PLAYER_SANITY).map(PlayerSanity::getSanity).get();
		}
		
		float ret = (sanity - this.getFullStrengthThreshold()) / (this.getSanityThreshold() - this.getFullStrengthThreshold());
		if(ret > 1) ret = 1.0F;
		else if(ret < 0) ret = 0.0F;
		
		return ret;
	}
	
	default float getSanityByThresholdInverted(Player player)
	{
		return 1.0F - this.getSanityByThreshold(player);
	}
}
