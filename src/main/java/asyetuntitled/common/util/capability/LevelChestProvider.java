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

public class LevelChestProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
	public static Capability<LevelChests> LEVEL_CHESTS = CapabilityManager.get(new CapabilityToken<>() {});
	
	private LevelChests levelChests = null;
	private final LazyOptional<LevelChests> opt = LazyOptional.of(this::createLevelChests);
	
	@Nonnull
	private LevelChests createLevelChests() {
		if (levelChests == null)
		{
			levelChests = new LevelChests();
		}
		return levelChests;
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
	{
		if (cap == LEVEL_CHESTS) 
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
//        createLevelChests().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) 
    {
//        createLevelChests().loadNBTData(nbt);
    }

}
