package asyetuntitled.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class CustomBundleTooltip implements TooltipComponent {
    private final NonNullList<ItemStack> items;
    private final int weight;
    private final int size;
    
    public CustomBundleTooltip(NonNullList<ItemStack> items, int weight, int size)
    {
       this.items = items;
       this.weight = weight;
       this.size = size;
    }

    public NonNullList<ItemStack> getItems() 
    {
       return this.items;
    }

    public int getWeight() 
    {
       return this.weight;
    }
    
    public int getSize() 
    {
       return this.size;
    }
 }