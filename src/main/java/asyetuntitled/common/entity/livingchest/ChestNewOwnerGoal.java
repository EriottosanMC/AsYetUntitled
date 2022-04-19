package asyetuntitled.common.entity.livingchest;

import asyetuntitled.common.item.ItemStaff;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ChestNewOwnerGoal extends LookAtPlayerGoal {

	public ChestNewOwnerGoal(Mob mob, Class<? extends LivingEntity> lookat, float distance, float prob,
			boolean onlyHorizontal) {
		super(mob, lookat, distance, prob, onlyHorizontal);
	}

	@Override
	public void tick() {
	     super.tick();
	     if(this.lookAt instanceof Player player && player.isAlive())
	     {

	    	 Inventory inv = player.getInventory();
	    	 for(int i = 0; i < inv.getContainerSize(); i++)
	    	 {

	    		 ItemStack stack = inv.getItem(i);
	    		 if(stack.getItem() instanceof ItemStaff staff)
	    		 {
	    			 if(staff.getChestUUID(stack) == this.mob.getStringUUID())
	    			 {		     

	    				 ((LivingChest) mob).setOwnerUUID(player.getUUID());
	    				 ((LivingChest) mob).setTame(true);
	    			 }
	    		 }
	    	 }
	     }
	     
	}
}
