package asyetuntitled.common.container;

import asyetuntitled.common.entity.livingchest.LivingChest;
import asyetuntitled.common.item.ItemsRegistry;
import asyetuntitled.common.menu.MenusRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class LivingChestContainer extends AbstractContainerMenu {
	
	private final ItemStackHandler inventory;
	private final InvWrapper playerInvWrapper;
	private LivingChest chest;
	
	public LivingChestContainer(int windowId, Inventory inv, LivingChest chest)
	{
		super(MenusRegistry.LIVING_CHEST_CONTAINER.get(), windowId);
		this.inventory = chest.getInventory();
		this.playerInvWrapper = new InvWrapper(inv);
		int size = chest.getInventory().getSlots() / 3;
		this.chest = chest;
		
		int u = size == 3 ? 62 : 53;
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < size; ++j) {
				//j+i*3
				this.addSlot(new SlotItemHandler(inventory, i + j * 3, u + j * 18, 17 + i * 18)
						{

							public boolean mayPlace(ItemStack p_40231_) {
									return p_40231_.getItem() == ItemsRegistry.BLANK_SLOT.get() ? false : super.mayPlace(p_40231_);
								}
						});
			}
		}

		for(int k = 0; k < 3; ++k) {
			for(int i1 = 0; i1 < 9; ++i1) {
				this.addSlot(new Slot(inv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
			}
		}

		for(int l = 0; l < 9; ++l) {
			this.addSlot(new Slot(inv, l, 8 + l * 18, 142));
		}
	}

	@Override
	public boolean stillValid(Player p_39440_) {
		return this.playerInvWrapper.getInv().stillValid(p_39440_);
	}

	//Deals with shift clicking
   @Override
   public ItemStack quickMoveStack(Player p_39444_, int p_39445_) {
	   ItemStack itemstack = ItemStack.EMPTY;
	   Slot slot = this.slots.get(p_39445_);
	   if (slot != null && slot.hasItem()) {
		   ItemStack itemstack1 = slot.getItem();
		   itemstack = itemstack1.copy();
		   if (p_39445_ < 9) {
			   if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
				   return ItemStack.EMPTY;
			   }
		   } else if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
			   return ItemStack.EMPTY;
		   }

		   if (itemstack1.isEmpty()) {
			   slot.set(ItemStack.EMPTY);
		   } else {
			   slot.setChanged();
		   }

		   if (itemstack1.getCount() == itemstack.getCount()) {
			   return ItemStack.EMPTY;
		   }

		   slot.onTake(p_39444_, itemstack1);
	   }

	   return itemstack;
   	}

   @Override
   public void removed(Player player) {
	   super.removed(player);
	   this.playerInvWrapper.getInv().stopOpen(player);
	   if(chest.getOwnerUUID() == player.getUUID())
	   {
		   chest.setOrderedToSit(false);
	   }
   }
}
