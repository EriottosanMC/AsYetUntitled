package asyetuntitled.common.player.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class SpawnPoint
{
    private BlockPos pos;
    private ResourceKey<Level> dim;
    
    public SpawnPoint(int x, int y, int z, ResourceKey<Level> dim)
    {
        this(new BlockPos(x, y, z), dim);
    }

    public SpawnPoint(BlockPos blockPos, ResourceKey<Level> dim)
    {
        this.pos = blockPos;
        this.dim = dim;
    }
    
    public ResourceKey<Level> getDimension()
    {
        return this.dim;
    }
    
    public BlockPos getBlockPos()
    {
        return this.pos;
    }

    public boolean isSamePoint(SpawnPoint point)
    {
        return point.pos.getX() == this.pos.getX() && point.pos.getY() == this.pos.getY()
                && point.pos.getZ() == this.pos.getZ() && point.dim == this.dim;
    }
}
