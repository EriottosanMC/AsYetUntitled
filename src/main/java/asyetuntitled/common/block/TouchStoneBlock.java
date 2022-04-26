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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
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
import net.minecraftforge.server.ServerLifecycleHooks;

public class TouchStoneBlock extends Block implements EntityBlock 
{
    private static final ImmutableList<Vec3i> RESPAWN_HORIZONTAL_OFFSETS = ImmutableList.of(new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(-1, 0, -1), new Vec3i(1, 0, -1), new Vec3i(-1, 0, 1), new Vec3i(1, 0, 1));
    private static final ImmutableList<Vec3i> RESPAWN_OFFSETS = (new Builder<Vec3i>()).addAll(RESPAWN_HORIZONTAL_OFFSETS).addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::below).iterator()).addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::above).iterator()).add(new Vec3i(0, 1, 0)).build();

    public TouchStoneBlock() {
        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2.0f)
                .noOcclusion()
                .requiresCorrectToolForDrops()
            );
    }
    
    @Override
    public void setPlacedBy(Level level, BlockPos p_49848_, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack p_49851_) 
    {
    	 TouchStoneBE touchstone = (TouchStoneBE)level.getBlockEntity(p_49848_);
    	 ItemStack rune = touchstone.getRune();
    	 if(!level.isClientSide && !rune.getOrCreateTag().contains("Alphabet")) touchstone.changeRune(this.RANDOM.nextFloat());
    }
   
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) 
	{
		return new TouchStoneBE(pos, state);

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

//    @Override
//    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
//        list.add(new TranslatableComponent(MESSAGE_GENERATOR).withStyle(ChatFormatting.BLUE));
//    }

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
            System.out.println("done!");
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
