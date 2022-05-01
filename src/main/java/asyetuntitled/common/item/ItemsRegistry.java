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
	
	public static final RegistryObject<Item> DUMMY_RUNE = ITEMS.register("dummy_rune", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
	
	public static final RegistryObject<BlockItem> TOUCHSTONE = ITEMS.register("touchstone", () -> new BlockItem(BlocksRegistry.TOUCHSTONE.get(), new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<BlockItem> TOUCHSTONE_BASE= ITEMS.register("touchstone_base", () -> new BlockItem(BlocksRegistry.TOUCHSTONE_BASE.get(), new Item.Properties().tab(ITEM_GROUP)));

	public static final RegistryObject<BlockItem> GRASS = ITEMS.register("usefulgrass", () -> new BlockItem(BlocksRegistry.GRASSUSEFUL.get(), new Item.Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> TALLGRASS = ITEMS.register("usefultallgrass", () -> new BlockItem(BlocksRegistry.TALLGRASSUSEFUL.get(), new Item.Properties().tab(ITEM_GROUP)));

	public static final RegistryObject<Item> GRASS_ITEM = ITEMS.register("grass", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));

	public static final RegistryObject<Item> GRASS_BUNDLE_WEARABLE = ITEMS.register("grass_bundle_wearable", () -> new CustomBundleWearableItem(ArmorMaterialInit.BACKPACK_SIMPLE, new Item.Properties().stacksTo(1).tab(ITEM_GROUP)));
	public static final RegistryObject<Item> GRASS_BUNDLE = ITEMS.register("grass_bundle", () -> new CustomBundleItem(new Item.Properties().stacksTo(1).tab(ITEM_GROUP), 2));

	public static final RegistryObject<BlockItem> COLUMN0 = ITEMS.register("column0", () -> new BlockItem(BlocksRegistry.TOUCHSTONE_COLUMN0.get(), new Item.Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> COLUMN1 = ITEMS.register("column1", () -> new BlockItem(BlocksRegistry.TOUCHSTONE_COLUMN1.get(), new Item.Properties().tab(ITEM_GROUP)));
	public static final RegistryObject<BlockItem> COLUMN2 = ITEMS.register("column2", () -> new BlockItem(BlocksRegistry.TOUCHSTONE_COLUMN2.get(), new Item.Properties().tab(ITEM_GROUP)));

	   public static final RegistryObject<BlockItem> CORNER = ITEMS.register("corner", () -> new BlockItem(BlocksRegistry.TOUCHSTONE_CORNER.get(), new Item.Properties().tab(ITEM_GROUP)));

	public static void registerItems(IEventBus bus) 
	{
		ITEMS.register(bus);
	}

}
