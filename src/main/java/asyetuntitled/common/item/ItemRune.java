package asyetuntitled.common.item;

import asyetuntitled.common.block.entity.TouchStoneBE;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ItemRune extends Item {

	public ItemRune(Properties prop) 
	{
		super(prop);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) 
	{
	    ItemStack stack = player.getMainHandItem();
	    stack.getOrCreateTag().putFloat("alphabet", 0.5F);
	    System.out.println(stack.getOrCreateTag().getFloat("alphabet"));
        return super.use(level, player, hand);
	    
	}


}
