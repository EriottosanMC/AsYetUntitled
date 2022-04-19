package asyetuntitled.common.block;

import javax.annotation.Nullable;

import asyetuntitled.common.block.entity.TouchStoneBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

public class BlockTouchStone extends Block implements EntityBlock 
{

    public BlockTouchStone() {
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
        		boolean b = !touchstone.hasPlayerTouched(player);
        		touchstone.setplayerTouched(player, b);
        	}
        	else
        	{
//        		touchstone.changeRune(this.RANDOM.nextFloat());
        	}
        }
        return InteractionResult.SUCCESS;
	}
}
