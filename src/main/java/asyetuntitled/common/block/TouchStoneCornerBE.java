package asyetuntitled.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TouchStoneCornerBE extends BlockEntity
{

    public TouchStoneCornerBE(BlockPos pos, BlockState state)
    {
        super(BlocksRegistry.TOUCHSTONECORNERBE.get(), pos, state);
    }

}
