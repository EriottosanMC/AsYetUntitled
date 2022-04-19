package asyetuntitled.common.item;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.block.BlocksRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegistry 
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AsYetUntitled.MODID);
	
	public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(AsYetUntitled.MODID) {
	        @Override
	        public ItemStack makeIcon() {
	            return new ItemStack(ItemsRegistry.BLANK_SLOT.get());
	        }
	};
	
	public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ITEM_GROUP);
	
	public static final RegistryObject<Item> BLANK_SLOT = ITEMS.register("blank_slot", () -> new ItemBlankSlot(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));
	public static final RegistryObject<Item> BACKPACK_BASE = ITEMS.register("backpack_base", () -> new ItemBackpack(new Item.Properties().tab(ITEM_GROUP).stacksTo(1), 9));
	public static final RegistryObject<Item> LIVING_CHEST_STAFF = ITEMS.register("living_chest_staff", () -> new ItemStaff(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));
	
	public static final RegistryObject<Item> BACKPACK_MAIN = ITEMS.register("backpack/main", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BACKPACK_MAIN_BUTTONS = ITEMS.register("backpack/main_buttons", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BACKPACK_TOP = ITEMS.register("backpack/top", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BACKPACK_TOP_BUTTONS = ITEMS.register("backpack/top_buttons", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BACKPACK_FRONT = ITEMS.register("backpack/front", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BACKPACK_FRONT_BUTTONS = ITEMS.register("backpack/front_buttons", () -> new Item(new Item.Properties()));
	
	public static final RegistryObject<Item> DUMMY_RUNE = ITEMS.register("dummy_rune", () -> new ItemRune(new Item.Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<Item> DUMMY_RUNE_BLANK = ITEMS.register("dummy_rune_blank", () -> new ItemRune(new Item.Properties().tab(ITEM_GROUP)));
	
	public static final RegistryObject<BlockItem> TOUCHSTONE_ITEM = ITEMS.register("touchstone", () -> new BlockItem(BlocksRegistry.TOUCHSTONE.get(), new Item.Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> GRASS = ITEMS.register("usefulgrass", () -> new BlockItem(BlocksRegistry.GRASSUSEFUL.get(), new Item.Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> TALLGRASS = ITEMS.register("usefultallgrass", () -> new BlockItem(BlocksRegistry.TALLGRASSUSEFUL.get(), new Item.Properties().tab(ITEM_GROUP)));

	public static final RegistryObject<Item> GRASS_ITEM = ITEMS.register("grass", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));

	
	
	
	public static void registerItems(IEventBus bus) 
	{
		ITEMS.register(bus);
	}

}
