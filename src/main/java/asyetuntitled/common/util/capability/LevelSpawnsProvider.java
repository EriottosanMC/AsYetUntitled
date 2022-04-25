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

public class LevelSpawnsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
	public static Capability<LevelSpawns> LEVEL_SPAWNS = CapabilityManager.get(new CapabilityToken<>() {});
	
	private LevelSpawns levelSpawns = null;
	private final LazyOptional<LevelSpawns> opt = LazyOptional.of(this::createLevelSpawns);
	
	@Nonnull
	private LevelSpawns createLevelSpawns() {
		if (levelSpawns == null)
		{
			levelSpawns = new LevelSpawns();
		}
		return levelSpawns;
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
	{
		if (cap == LEVEL_SPAWNS) 
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
//        createLevelSpawns().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) 
    {
//        createLevelSpawns().loadNBTData(nbt);
    }

}
