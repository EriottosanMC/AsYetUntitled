package asyetuntitled.common.util.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerSanityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
	public static Capability<PlayerSanity> PLAYER_SANITY = CapabilityManager.get(new CapabilityToken<>() {});
	
	private PlayerSanity playerSanity = null;
	private final LazyOptional<PlayerSanity> opt = LazyOptional.of(this::createPlayerSanity);
	
	@Nonnull
	private PlayerSanity createPlayerSanity() {
		if (playerSanity == null)
		{
			playerSanity = new PlayerSanity();
		}
		return playerSanity;
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
	{
		if (cap == PLAYER_SANITY) 
		{
            return opt.cast();
        }
        return LazyOptional.empty();
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) 
	{
		return getCapability(cap);
	}

    @Override
    public CompoundTag serializeNBT() 
    {
        CompoundTag nbt = new CompoundTag();
        createPlayerSanity().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) 
    {
        createPlayerSanity().loadNBTData(nbt);
    }

}
