package asyetuntitled.common.item;

import java.util.Optional;
import java.util.stream.Stream;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CustomBundleItem extends BundleItem
{
    private int size;
    
    public CustomBundleItem(Properties prop, int size)
    {
        super(prop);
        this.size = size;
    }
    
    @Override  
    public boolean overrideStackedOnOther(ItemStack bundle, Slot slot, ClickAction action, Player player) 
    {
        ItemStack itemIn = slot.getItem();
        boolean isSpace = getContents(bundle).anyMatch(stack -> stack.getItem() == itemIn.getItem()) || (2 - getContents(bundle).mapToInt(stack -> {return 1;}).sum()) > 0;
        if(!itemIn.isEmpty() && !isSpace)
        {
            return false;
        }
        else
        {
            return super.overrideStackedOnOther(bundle, slot, action, player);
        }
    }

    @Override  
    public boolean overrideOtherStackedOnMe(ItemStack bundle, ItemStack itemIn, Slot slot, ClickAction action, Player player, SlotAccess slotAccess) {
        boolean isSpace = getContents(bundle).anyMatch(stack -> stack.getItem() == itemIn.getItem()) || 2 - getContents(bundle).mapToInt(stack -> {return 1;}).sum() > 0;
        if(!isSpace)
        {
            return false;
        }
        else
        {
            return super.overrideOtherStackedOnMe(bundle, itemIn, slot, action, player, slotAccess);
        }
    }
    
    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        Optional<TooltipComponent> ret = super.getTooltipImage(stack);
        ret.get();
        NonNullList<ItemStack> nonnulllist = NonNullList.create();
        getContents(stack).forEach(nonnulllist::add);
        return Optional.of(new CustomBundleTooltip(nonnulllist, getContentWeight(stack), this.size));
     }
    
    private static Stream<ItemStack> getContents(ItemStack bundle) {
        CompoundTag compoundtag = bundle.getTag();
        if (compoundtag == null) {
           return Stream.empty();
        } else {
           ListTag listtag = compoundtag.getList("Items", 10);
           return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
        }
     }
    
    private static int getContentWeight(ItemStack p_150779_) {
        return getContents(p_150779_).mapToInt((p_186356_) -> {
           return getWeight(p_186356_) * p_186356_.getCount();
        }).sum();
     }
    
    private static int getWeight(ItemStack p_150777_) {
        if (p_150777_.is(Items.BUNDLE)) {
           return 4 + getContentWeight(p_150777_);
        } else {
           if ((p_150777_.is(Items.BEEHIVE) || p_150777_.is(Items.BEE_NEST)) && p_150777_.hasTag()) {
              CompoundTag compoundtag = BlockItem.getBlockEntityData(p_150777_);
              if (compoundtag != null && !compoundtag.getList("Bees", 10).isEmpty()) {
                 return 64;
              }
           }

           return 64 / p_150777_.getMaxStackSize();
        }
     }

}
