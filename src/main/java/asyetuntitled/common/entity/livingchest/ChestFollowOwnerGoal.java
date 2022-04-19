package asyetuntitled.common.entity.livingchest;
import java.util.List;

import asyetuntitled.common.item.ItemStaff;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ChestFollowOwnerGoal extends FollowOwnerGoal {

	private LivingChest chest;
   public ChestFollowOwnerGoal(LivingChest animal, double speed, float start, float stop, boolean fly) {
	   super(animal, speed, start, stop, fly);
	   this.chest = animal;
   }

   @Override
   public boolean canUse() {
	   LivingEntity livingentity = this.chest.getOwner();
	   if(livingentity instanceof Player && !this.hasStaff((Player) livingentity))
	   {
		   return false;
	   }
	   else
	   {
		   return super.canUse();
	   }
   }

   @Override
   public boolean canContinueToUse() {
	   LivingEntity livingentity = this.chest.getOwner();
	   if(livingentity instanceof Player player)
	   {
		   return this.hasStaff(player);
	   } 
	   else
	   {
		   return super.canContinueToUse();
	   }
   }

   private boolean hasStaff(Player player) 
   {
	   Inventory inv = player.getInventory();
	   
	   for(int i = 0; i < inv.getContainerSize(); i++)
	   {
		   ItemStack stack = inv.getItem(i);
		   if(stack.getItem() instanceof ItemStaff staff)
		   {
			   String chestUUID = staff.getChestUUID(stack);
			   if(chestUUID == this.chest.getStringUUID())
			   {
				   return true;
			   }
		   }
	   }
	   List<Player> list = this.chest.level.getNearbyPlayers(TargetingConditions.forNonCombat().ignoreLineOfSight().range(16.0D), this.chest, this.chest.getBoundingBox().inflate(16.0D));
	   for(Player p : list)
	   {
		   Inventory inv2 = p.getInventory();
		   
		   for(int i = 0; i < inv.getContainerSize(); i++)
		   {
			   ItemStack stack = inv2.getItem(i);
			   if(stack.getItem() instanceof ItemStaff)
			   {
				   String chestUUID = ((ItemStaff) stack.getItem()).getChestUUID(stack);
				   if(chestUUID == this.chest.getStringUUID())
				   {
					   this.chest.setOwnerUUID(p.getUUID());
					   return true;
				   }
			   }
		   }
	   }
	   return false;
   }

}