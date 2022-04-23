package asyetuntitled.common.util.capability;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.sanity.ClientSanityData;
import asyetuntitled.common.messages.MessagesRegistry;
import asyetuntitled.common.messages.ClientboundPacketSyncPlayerDarkness;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerSanity
{
	private int playerSanity = AsYetUntitled.MAX_SANITY;

	public void changeSanity(Player player, int amount)
	{
		this.setSanity(player, playerSanity + amount);
	}
	
	public void changeSanity(Player player, int amount, boolean force)
	{
		this.setSanity(player, playerSanity + amount, force);
	}
	
	public void setSanity(Player player, int sanity)
	{
		if(sanity > AsYetUntitled.MAX_SANITY) sanity = AsYetUntitled.MAX_SANITY;
		else if(sanity < 0) sanity = 0;
		
		this.setSanity(player, sanity, false);
	}
	
	public void setSanity(Player player, int sanity, boolean forceUpdate) 
	{
		this.playerSanity = sanity;
		if(player instanceof ServerPlayer serverPlayer)
		{
			MessagesRegistry.sendToPlayer(new ClientboundPacketSyncPlayerDarkness(this.playerSanity, forceUpdate), serverPlayer);
		}		
	}
	
	public void resetSanity(Player player)
	{
		this.setSanity(player, AsYetUntitled.MAX_SANITY);
	}
	
	public int getSanityRaw()
	{
		return this.playerSanity;
	}
	
	public float getSanity()
	{
		return this.playerSanity / (float) AsYetUntitled.MAX_SANITY;
	}
	
	public void debugCycleSanity(Player player)
	{
	    int i = ClientSanityData.getPlayerSanityRaw();
		if(i >= AsYetUntitled.MAX_SANITY)
		{
			this.setSanity(player, 0);
		}
		else if(i <= 0)
		{
			this.setSanity(player, AsYetUntitled.MAX_SANITY);
		}
	}
	
    public void saveNBTData(CompoundTag compound) {
        compound.putInt("sanity", playerSanity);
    }

    public void loadNBTData(CompoundTag compound) {
        playerSanity = compound.getInt("sanity");
    }
}
