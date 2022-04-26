package asyetuntitled.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ColumnBlock extends Block implements SimpleWaterloggedBlock
{
    public int LEVELS;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected final VoxelShape[] shapeByIndex;

    public ColumnBlock(Properties prop, int levels)
    {
        super(prop);
        this.LEVELS = levels;
        this.shapeByIndex = makeShapes();
    }
    
    protected VoxelShape[] makeShapes() 
    {
        VoxelShape[] buffer = new VoxelShape[8];
        
        VoxelShape base = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
        VoxelShape crown =  Block.box(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        double l = (double) this.LEVELS;
        double l2 = l * 2.0D;
        double l3 = l + 2.0D;
                
        for(int i = 1 ; i <= l ; i++)
        {
            base = Shapes.or(base, Block.box(l, l2 + 1.0D, l, 16.0D - l, l2 + 3.0D, 16.0D - l));
            crown = Shapes.or(crown, Block.box(l, 13.0D - l2, l, 16.0D - l, 15.0D - l2, 16.0D - l));
        }
        
        l += 2.0D;
        
        for(boolean up : UP.getPossibleValues())
        {
            for(boolean down : DOWN.getPossibleValues())
            {
                int ind = 0;
                if(up) ind += 4;
                if(down) ind += 2;
                double bottom = down ? l2 + 3.0D : 0.0D;
                double top = up ? 13.0D - l2 : 16.0D;
                VoxelShape main = Block.box(l3, bottom, l3, 16.0D - l3, top, 16.0D - l3);
                main = join(main, crown, up);
                main = join(main, base, down);
                buffer[ind] = main;
                buffer[ind + 1] = main;
            }
        }
        
        return buffer;
    }
    
    private VoxelShape join(VoxelShape original, VoxelShape addition, boolean add)
    {
        if(add)
        {
            return Shapes.or(original, addition);
        }
        else
        {
            return original;
        }
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(UP, DOWN, WATERLOGGED);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Block blockAbove = level.getBlockState(pos.above()).getBlock();
        Block blockBelow = level.getBlockState(pos.below()).getBlock();
        FluidState fluidstate = level.getFluidState(pos);
        return this.defaultBlockState().setValue(UP, Boolean.valueOf(!(blockAbove instanceof ColumnBlock))).setValue(DOWN, Boolean.valueOf(!(blockBelow instanceof ColumnBlock))).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int i = 0;
        if(state.getValue(UP).booleanValue()) i += 4;
        if(state.getValue(DOWN).booleanValue()) i += 2;
        if(state.getValue(WATERLOGGED).booleanValue()) i += 1;
        return this.shapeByIndex[i];
    }
    
    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborstate, LevelAccessor level,
            BlockPos pos, BlockPos neighbor)
    {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
         }
        
        if(pos.below().getY() == neighbor.getY())
        {
            state = state.setValue(DOWN, Boolean.valueOf(!(level.getBlockState(neighbor).getBlock() instanceof ColumnBlock)));
        }
        if(pos.above().getY() == neighbor.getY())
        {
            state = state.setValue(UP, Boolean.valueOf(!(level.getBlockState(neighbor).getBlock() instanceof ColumnBlock)));
        }
        return state;
    }
    
    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
}
