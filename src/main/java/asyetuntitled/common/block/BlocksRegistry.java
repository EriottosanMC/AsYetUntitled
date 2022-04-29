package asyetuntitled.common.block;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.block.entity.TouchStoneBE;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlocksRegistry 
{
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AsYetUntitled.MODID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AsYetUntitled.MODID);
	 private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, AsYetUntitled.MODID);
	 
	public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops().speedFactor(0.8F);
	
	public static final RegistryObject<TouchStoneBlock> TOUCHSTONE = BLOCKS.register("touchstone", () -> new TouchStoneBlock());
	public static final RegistryObject<SlabBlock> TOUCHSTONE_BASE = BLOCKS.register("touchstone_base", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE)));
	public static final RegistryObject<BlockEntityType<TouchStoneBE>> TOUCHSTONEBE = BLOCK_ENTITIES.register("touchstone_be", () -> BlockEntityType.Builder.of(TouchStoneBE::new, TOUCHSTONE.get()).build(null));
	
	public static final RegistryObject<Block> GRASSUSEFUL = BLOCKS.register("grassuseful", () -> new TallGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().noOcclusion().strength(0.2F, 0.0F)));
	public static final RegistryObject<Block> TALLGRASSUSEFUL = BLOCKS.register("tallgrassuseful", () -> new DoublePlantBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().sound(SoundType.GRASS).noOcclusion().strength(0.2F, 0.0F)));

	public static final RegistryObject<Block> OAK_STUMP = BLOCKS.register("oak_stump", () -> new StumpBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.2F, 0.0F).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> BIRCH_STUMP = BLOCKS.register("birch_stump", () -> new StumpBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.2F, 0.0F).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> SPRUCE_STUMP = BLOCKS.register("spruce_stump", () -> new StumpBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.2F, 0.0F).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> JUNGLE_STUMP = BLOCKS.register("jungle_stump", () -> new StumpBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.2F, 0.0F).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> ACACIA_STUMP = BLOCKS.register("acacia_stump", () -> new StumpBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.2F, 0.0F).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> DARK_OAK_STUMP = BLOCKS.register("dark_oak_stump", () -> new StumpBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.2F, 0.0F).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));

	public static final RegistryObject<Block> TOUCHSTONE_COLUMN0 = BLOCKS.register("touchstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F), 0));
    public static final RegistryObject<Block> TOUCHSTONE_COLUMN1 = BLOCKS.register("touchstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F), 1));
    public static final RegistryObject<Block> TOUCHSTONE_COLUMN2 = BLOCKS.register("touchstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F), 2));

    public static final RegistryObject<Block> TOUCHSTONE_CORNER = BLOCKS.register("touchstone_corner", () -> new CornerBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<BlockEntityType<TouchStoneCornerBE>> TOUCHSTONECORNERBE = BLOCK_ENTITIES.register("touchstonecorner_be", () -> BlockEntityType.Builder.of(TouchStoneCornerBE::new, TOUCHSTONE_CORNER.get()).build(null));

//	private static ResourceLocation fastestTagID = new ResourceLocation(UMM.MODID, "fastest");
//	public static Tag<Block> fastestTag = BlockTags.getAllTags().getTagOrEmpty(fastestTagID);
//	private static ResourceLocation fastTagID = new ResourceLocation(UMM.MODID, "fast");
//	public static Tag<Block> fastTag = BlockTags.getAllTags().getTagOrEmpty(fastTagID);
//	private static ResourceLocation slowTagID = new ResourceLocation(UMM.MODID, "slow");
//	public static Tag<Block> slowTag = BlockTags.getAllTags().getTagOrEmpty(slowTagID);
//	private static ResourceLocation slowestTagID = new ResourceLocation(UMM.MODID, "slowest");
//	public static Tag<Block> slowestTag = BlockTags.getAllTags().getTagOrEmpty(slowestTagID);
	
	public static void registerBlocks(IEventBus bus) 
	{
		BLOCKS.register(bus);
		BLOCK_ENTITIES.register(bus);
		CONTAINERS.register(bus);
	}
	
	

}
