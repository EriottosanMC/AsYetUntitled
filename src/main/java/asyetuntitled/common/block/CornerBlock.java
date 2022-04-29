package asyetuntitled.common.block;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CornerBlock extends Block implements EntityBlock, SimpleWaterloggedBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected final Map<Direction, VoxelShape> shapes;

    public CornerBlock(Properties prop)
    {
        super(prop);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.shapes = makeShapes();
    }


    private Map<Direction, VoxelShape> makeShapes()
    {
        ImmutableMap.Builder<Direction, VoxelShape> builder = ImmutableMap.builder();    
        
        for(Direction direction : FACING.getPossibleValues())
        {
            VoxelShape corner = null;
            double d0 = 0.0D;
            double d1 = 16.0D;
            double d2 = 0.0D;
            for(int j = 0; j < 4; j++)
            {
                if(corner == null)
                {
                    corner = getNextShape(d0, d1, d2, direction);
                }
                else
                {
                    corner = Shapes.or(corner, getNextShape(d0, d1, d2, direction));
                }

                d1 -= 4D;
                d2 += 4D;
            }
            builder.put(direction, corner);
        }
        
        return builder.build();
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return this.shapes.get(state.getValue(FACING));
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(WATERLOGGED);
    }


    private VoxelShape getNextShape(double d0, double d1, double d2, Direction direction)
    {
        double d3 = d2 + 4D;
        
        if(direction == Direction.SOUTH)
        {
            d0 = 16D - d3;
            d1 = 16D;
        }
        else if(direction == Direction.EAST || direction == Direction.WEST)
        {
            d0 = d2;
            d1 = d3;
            if(direction == Direction.EAST) d2 = 0D;
            else d3 = 16D;
        }
        
        return Block.box(d0, 0.0D, d2, d1, 8D, d3);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new TouchStoneCornerBE(pos, state);
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidstate = level.getFluidState(pos);
        return this.defaultBlockState().setValue(FACING, Direction.valueOf("NORTH")).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if (!level.isClientSide()) 
        {
            Direction direction = state.getValue(FACING);
            System.out.println(direction);
        }
        return InteractionResult.SUCCESS;
    }
    
    
    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborstate, LevelAccessor level,
            BlockPos pos, BlockPos neighbor)
    {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
         }
        
        return state;
    }
    
    
    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
    
}
