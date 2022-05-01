package asyetuntitled.common.block.entity;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;

import asyetuntitled.common.block.BlocksRegistry;
import asyetuntitled.common.item.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TouchStoneBE extends BlockEntity 
{
	private final List<UUID> playerList = Lists.newArrayList();
	private final NonNullList<ItemStack> rune = NonNullList.withSize(1, new ItemStack(ItemsRegistry.DUMMY_RUNE.get()));
	
    public TouchStoneBE(BlockPos pos, BlockState state)
    {
        super(BlocksRegistry.TOUCHSTONEBE.get(), pos, state);
    }
    
    public boolean hasPlayerTouched(Player player)
    {
    	return playerList.contains(player.getUUID());
    }
    
    public void setplayerTouched(Player player, boolean b)
    {
    	if(b == !this.hasPlayerTouched(player))
    	{
    		if(b)
			{
    			addPlayerToList(player);
			}
    		else
    		{
    			removePlayerFromList(player);
    		}
    	}
    }
    
    private void removePlayerFromList(Player player)
    {
    	playerList.remove(player.getUUID());
		if(!level.isClientSide())
		{
	    	setChanged();
	    	level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
		}
    }

	private void addPlayerToList(Player player) 
    {
		playerList.add(player.getUUID());
		if(!level.isClientSide())
		{
	    	setChanged();
	    	level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
		}
    }

// Called by the block ticker
    public void tickServer() 
    {
        if(!level.isClientSide && !this.getRune().getOrCreateTag().contains("alphabet")) this.changeRune(this.level.random.nextFloat());
    }

    
    @Override
    public CompoundTag getUpdateTag()
    {
    	CompoundTag tag = super.getUpdateTag();
    	saveClientData(tag);
    	return tag;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
    	if(tag != null) 
    	{
        	loadClientData(tag);
    	}
    }
    
    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
    	return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet)
    {
    	List<UUID> oldPlayerList = playerList;
    	NonNullList<ItemStack> oldRune = rune;
    	CompoundTag tag = packet.getTag();
    	handleUpdateTag(tag);

    	if(oldPlayerList != playerList || oldRune != rune)
    	{
    		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    	}
    }
    
    @Override
    public void setRemoved()
    {
    	super.setRemoved();
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag)
    {
    	super.saveAdditional(tag);
    	int playerListSize = playerList.size();
    	tag.putInt("Player List", playerListSize);
    	for(int i = 0; i < playerListSize; i++)
    	{
    		tag.putUUID("Player " + i, playerList.get(i));
    	}
    	ContainerHelper.saveAllItems(tag, rune);
    	saveClientData(tag);
    }
    
    private void saveClientData(CompoundTag tag)
    {
    	int playerListSize = playerList.size();
    	tag.putInt("Client Player List", playerListSize);

    	for(int i = 0; i < playerListSize; i++)
    	{
    		tag.putUUID("Client Player " + i, playerList.get(i));
    	}
    	ContainerHelper.saveAllItems(tag, rune);
    }
    
    @Override
    public void load(CompoundTag tag)
    {
    	super.load(tag);
    	if(tag.contains("Player List"))
    	{
    		int playerListSize = tag.getInt("Player List");
    		for(int i = 0; i < playerListSize; i++)
    		{
    			playerList.add(tag.getUUID("Player " + i));
    		}
    	}
    	rune.clear();
    	ContainerHelper.loadAllItems(tag, rune);
    	loadClientData(tag);
    }
    
    private void loadClientData(CompoundTag tag)
    {
    	if(tag.contains("Client Player List"))
    	{
    		int playerListSize = tag.getInt("Client Player List");

    		playerList.clear();
    		for(int i = 0; i < playerListSize; i++)
    		{
    			UUID uuid = tag.getUUID("Client Player " + i);
    			if(!playerList.contains(uuid))
    			{
    				playerList.add(uuid);
    			}
    		}
    	}
    	rune.clear();
    	ContainerHelper.loadAllItems(tag, rune);
    }
    
    public void changeRune(float alphabet)
    {
    	rune.get(0).getOrCreateTag().putFloat("alphabet", alphabet);
    	setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }
    
    public ItemStack getRune()
    {
    	return rune.get(0);
    }

    public void setRuneNull()
    {
       rune.set(0, new ItemStack(ItemsRegistry.DUMMY_RUNE.get()));        
    }
}