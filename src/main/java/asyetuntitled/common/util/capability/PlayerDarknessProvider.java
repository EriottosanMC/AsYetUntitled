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

public class PlayerDarknessProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
	public static Capability<PlayerDarkness> PLAYER_DARKNESS = CapabilityManager.get(new CapabilityToken<>() {});
	
	private PlayerDarkness playerDarkness = null;
	private final LazyOptional<PlayerDarkness> opt = LazyOptional.of(this::createPlayerDarkness);
	
	@Nonnull
	private PlayerDarkness createPlayerDarkness() {
		if (playerDarkness == null)
		{
			playerDarkness = new PlayerDarkness();
		}
		return playerDarkness;
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
	{
		if (cap == PLAYER_DARKNESS) 
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
        createPlayerDarkness().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) 
    {
        createPlayerDarkness().loadNBTData(nbt);
    }

}
