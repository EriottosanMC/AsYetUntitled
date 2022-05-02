package asyetuntitled.common.block;

import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import asyetuntitled.common.block.entity.TouchStoneBE;
import asyetuntitled.common.player.spawn.SpawnPoint;
import asyetuntitled.common.util.capability.LevelSpawnsProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.server.ServerLifecycleHooks;

public class TouchStoneBlock extends Block implements EntityBlock 
{
    private static final ImmutableList<Vec3i> RESPAWN_HORIZONTAL_OFFSETS = ImmutableList.of(new Vec3i(-1, 0, -2), new Vec3i(0, 0, -2), new Vec3i(1, 0, -2), new Vec3i(-2, 0, -1), new Vec3i(-2, 0, 0), new Vec3i(-2, 0, 1), new Vec3i(-1, 0, 2), new Vec3i(0, 0, 2), new Vec3i(1, 0, 2), new Vec3i(2, 0, -1), new Vec3i(2, 0, 0), new Vec3i(2, 0, 1));
    private static final ImmutableList<Vec3i> RESPAWN_OFFSETS = (new Builder<Vec3i>()).addAll(RESPAWN_HORIZONTAL_OFFSETS).addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::below).iterator()).addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::above).iterator()).add(new Vec3i(0, 1, 0)).build();
    private final VoxelShape shape;
    
    public TouchStoneBlock() {
        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2.0f)
                .noOcclusion()
                .requiresCorrectToolForDrops()
            );
        this.shape = makeShape();
    }
    
	private VoxelShape makeShape()
    {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }

    @Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) 
	{
		return new TouchStoneBE(pos, state);
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
	{
	    return this.shape;
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if (!level.isClientSide()) {
			return (lvl, pos, stt, te) -> {
				if (te instanceof TouchStoneBE touchstone) touchstone.tickServer();
			};
		}
		return null;
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
	{
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof TouchStoneBE touchstone) 
        {
        	if(player.getItemInHand(hand).isEmpty())
        	{
        	    ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getCapability(LevelSpawnsProvider.LEVEL_SPAWNS).ifPresent(spawns -> {
        	        spawns.addPlayerSpawn((ServerPlayer) player, new SpawnPoint(pos, level.dimension()));
        	    });
        	}
        	else
        	{
//        		touchstone.changeRune(this.RANDOM.nextFloat());
        	}
        }
        return InteractionResult.SUCCESS;
	}
	
	@Override
	public void destroy(LevelAccessor level, BlockPos pos, BlockState state) 
	{
	    if(!level.isClientSide() && level instanceof ServerLevel s)
	    ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getCapability(LevelSpawnsProvider.LEVEL_SPAWNS).ifPresent(spawns -> {
            spawns.destroySpawnForAll(new SpawnPoint(pos, s.dimension()));
        });
	}
	
	public static Optional<Vec3> findStandUpPosition(EntityType<?> p_55840_, CollisionGetter p_55841_, BlockPos p_55842_) {
	       Optional<Vec3> optional = findStandUpPosition(p_55840_, p_55841_, p_55842_, true);
	       return optional.isPresent() ? optional : findStandUpPosition(p_55840_, p_55841_, p_55842_, false);
	}
	
	private static Optional<Vec3> findStandUpPosition(EntityType<?> p_55844_, CollisionGetter p_55845_, BlockPos p_55846_, boolean p_55847_) {
	    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	    for(Vec3i vec3i : RESPAWN_OFFSETS) {
	        blockpos$mutableblockpos.set(p_55846_).move(vec3i);
	        Vec3 vec3 = DismountHelper.findSafeDismountLocation(p_55844_, p_55845_, blockpos$mutableblockpos, p_55847_);
	        if (vec3 != null) {
                return Optional.of(vec3);
	        }
	    }

	    return Optional.empty();
	}

}
