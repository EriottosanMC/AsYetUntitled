package asyetuntitled.common.block;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.block.entity.TouchStoneBE;
import asyetuntitled.common.util.CommonReflectionHelper;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
    /*
     *  Registering blocks!
     *  Remember to give each block a speed.
     *  Remember to add blocks to the correct mining tag (including diggable by hand!)
     */
    
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

	public static final RegistryObject<Block> TOUCHSTONE_COLUMN0 = BLOCKS.register("column/touchstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F), 0));
    public static final RegistryObject<Block> TOUCHSTONE_COLUMN1 = BLOCKS.register("column/touchstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F), 1));
    public static final RegistryObject<Block> TOUCHSTONE_COLUMN2 = BLOCKS.register("column/touchstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F), 2));

    public static final RegistryObject<Block> TOUCHSTONE_CORNER = BLOCKS.register("touchstone_corner", () -> new CornerBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<BlockEntityType<TouchStoneCornerBE>> TOUCHSTONECORNERBE = BLOCK_ENTITIES.register("touchstonecorner_be", () -> BlockEntityType.Builder.of(TouchStoneCornerBE::new, TOUCHSTONE_CORNER.get()).build(null));

    
    
    
    
    public static final RegistryObject<Block> STONE_COLUMN0 = BLOCKS.register("column/stone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STONE), 0));
    public static final RegistryObject<Block> STONE_COLUMN1 = BLOCKS.register("column/stone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STONE), 1));
    public static final RegistryObject<Block> STONE_COLUMN2 = BLOCKS.register("column/stone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STONE), 2));
    public static final RegistryObject<Block> GRANITE_COLUMN0 = BLOCKS.register("column/granite_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE), 0));
    public static final RegistryObject<Block> GRANITE_COLUMN1 = BLOCKS.register("column/granite_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE), 1));
    public static final RegistryObject<Block> GRANITE_COLUMN2 = BLOCKS.register("column/granite_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE), 2));
    public static final RegistryObject<Block> POLISHED_GRANITE_COLUMN0 = BLOCKS.register("column/polished_granite_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_GRANITE), 0));
    public static final RegistryObject<Block> POLISHED_GRANITE_COLUMN1 = BLOCKS.register("column/polished_granite_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_GRANITE), 1));
    public static final RegistryObject<Block> POLISHED_GRANITE_COLUMN2 = BLOCKS.register("column/polished_granite_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_GRANITE), 2));
    public static final RegistryObject<Block> DIORITE_COLUMN0 = BLOCKS.register("column/diorite_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DIORITE), 0));
    public static final RegistryObject<Block> DIORITE_COLUMN1 = BLOCKS.register("column/diorite_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DIORITE), 1));
    public static final RegistryObject<Block> DIORITE_COLUMN2 = BLOCKS.register("column/diorite_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DIORITE), 2));
    public static final RegistryObject<Block> POLISHED_DIORITE_COLUMN0 = BLOCKS.register("column/polished_diorite_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE), 0));
    public static final RegistryObject<Block> POLISHED_DIORITE_COLUMN1 = BLOCKS.register("column/polished_diorite_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE), 1));
    public static final RegistryObject<Block> POLISHED_DIORITE_COLUMN2 = BLOCKS.register("column/polished_diorite_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE), 2));
    public static final RegistryObject<Block> ANDESITE_COLUMN0 = BLOCKS.register("column/andesite_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE), 0));
    public static final RegistryObject<Block> ANDESITE_COLUMN1 = BLOCKS.register("column/andesite_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE), 1));
    public static final RegistryObject<Block> ANDESITE_COLUMN2 = BLOCKS.register("column/andesite_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE), 2));
    public static final RegistryObject<Block> POLISHED_ANDESITE_COLUMN0 = BLOCKS.register("column/polished_andesite_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE), 0));
    public static final RegistryObject<Block> POLISHED_ANDESITE_COLUMN1 = BLOCKS.register("column/polished_andesite_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE), 1));
    public static final RegistryObject<Block> POLISHED_ANDESITE_COLUMN2 = BLOCKS.register("column/polished_andesite_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE), 2));
    public static final RegistryObject<Block> COBBLESTONE_COLUMN0 = BLOCKS.register("column/cobblestone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE), 0));
    public static final RegistryObject<Block> COBBLESTONE_COLUMN1 = BLOCKS.register("column/cobblestone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE), 1));
    public static final RegistryObject<Block> COBBLESTONE_COLUMN2 = BLOCKS.register("column/cobblestone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE), 2));
//    public static final RegistryObject<Block> STRIPPED_SPRUCE_LOG_COLUMN0 = BLOCKS.register("column/stripped_spruce_log_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_LOG), 0));
//    public static final RegistryObject<Block> STRIPPED_SPRUCE_LOG_COLUMN1 = BLOCKS.register("column/stripped_spruce_log_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_LOG), 1));
//    public static final RegistryObject<Block> STRIPPED_SPRUCE_LOG_COLUMN2 = BLOCKS.register("column/stripped_spruce_log_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_LOG), 2));
//    public static final RegistryObject<Block> STRIPPED_BIRCH_LOG_COLUMN0 = BLOCKS.register("column/stripped_birch_log_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_BIRCH_LOG), 0));
//    public static final RegistryObject<Block> STRIPPED_BIRCH_LOG_COLUMN1 = BLOCKS.register("column/stripped_birch_log_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_BIRCH_LOG), 1));
//    public static final RegistryObject<Block> STRIPPED_BIRCH_LOG_COLUMN2 = BLOCKS.register("column/stripped_birch_log_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_BIRCH_LOG), 2));
//    public static final RegistryObject<Block> STRIPPED_JUNGLE_LOG_COLUMN0 = BLOCKS.register("column/stripped_jungle_log_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG), 0));
//    public static final RegistryObject<Block> STRIPPED_JUNGLE_LOG_COLUMN1 = BLOCKS.register("column/stripped_jungle_log_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG), 1));
//    public static final RegistryObject<Block> STRIPPED_JUNGLE_LOG_COLUMN2 = BLOCKS.register("column/stripped_jungle_log_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG), 2));
//    public static final RegistryObject<Block> STRIPPED_ACACIA_LOG_COLUMN0 = BLOCKS.register("column/stripped_acacia_log_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_ACACIA_LOG), 0));
//    public static final RegistryObject<Block> STRIPPED_ACACIA_LOG_COLUMN1 = BLOCKS.register("column/stripped_acacia_log_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_ACACIA_LOG), 1));
//    public static final RegistryObject<Block> STRIPPED_ACACIA_LOG_COLUMN2 = BLOCKS.register("column/stripped_acacia_log_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_ACACIA_LOG), 2));
//    public static final RegistryObject<Block> STRIPPED_DARK_OAK_LOG_COLUMN0 = BLOCKS.register("column/stripped_dark_oak_log_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_LOG), 0));
//    public static final RegistryObject<Block> STRIPPED_DARK_OAK_LOG_COLUMN1 = BLOCKS.register("column/stripped_dark_oak_log_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_LOG), 1));
//    public static final RegistryObject<Block> STRIPPED_DARK_OAK_LOG_COLUMN2 = BLOCKS.register("column/stripped_dark_oak_log_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_LOG), 2));
//    public static final RegistryObject<Block> STRIPPED_OAK_LOG_COLUMN0 = BLOCKS.register("column/stripped_oak_log_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG), 0));
//    public static final RegistryObject<Block> STRIPPED_OAK_LOG_COLUMN1 = BLOCKS.register("column/stripped_oak_log_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG), 1));
//    public static final RegistryObject<Block> STRIPPED_OAK_LOG_COLUMN2 = BLOCKS.register("column/stripped_oak_log_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG), 2));
    public static final RegistryObject<Block> LAPIS_BLOCK_COLUMN0 = BLOCKS.register("column/lapis_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK), 0));
    public static final RegistryObject<Block> LAPIS_BLOCK_COLUMN1 = BLOCKS.register("column/lapis_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK), 1));
    public static final RegistryObject<Block> LAPIS_BLOCK_COLUMN2 = BLOCKS.register("column/lapis_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK), 2));
    public static final RegistryObject<Block> GOLD_BLOCK_COLUMN0 = BLOCKS.register("column/gold_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK), 0));
    public static final RegistryObject<Block> GOLD_BLOCK_COLUMN1 = BLOCKS.register("column/gold_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK), 1));
    public static final RegistryObject<Block> GOLD_BLOCK_COLUMN2 = BLOCKS.register("column/gold_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK), 2));
    public static final RegistryObject<Block> IRON_BLOCK_COLUMN0 = BLOCKS.register("column/iron_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 0));
    public static final RegistryObject<Block> IRON_BLOCK_COLUMN1 = BLOCKS.register("column/iron_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 1));
    public static final RegistryObject<Block> IRON_BLOCK_COLUMN2 = BLOCKS.register("column/iron_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 2));
    public static final RegistryObject<Block> DIAMOND_BLOCK_COLUMN0 = BLOCKS.register("column/diamond_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK), 0));
    public static final RegistryObject<Block> DIAMOND_BLOCK_COLUMN1 = BLOCKS.register("column/diamond_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK), 1));
    public static final RegistryObject<Block> DIAMOND_BLOCK_COLUMN2 = BLOCKS.register("column/diamond_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK), 2));
    public static final RegistryObject<Block> NETHERITE_BLOCK_COLUMN0 = BLOCKS.register("column/netherite_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK), 0));
    public static final RegistryObject<Block> NETHERITE_BLOCK_COLUMN1 = BLOCKS.register("column/netherite_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK), 1));
    public static final RegistryObject<Block> NETHERITE_BLOCK_COLUMN2 = BLOCKS.register("column/netherite_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK), 2));
    public static final RegistryObject<Block> SANDSTONE_COLUMN0 = BLOCKS.register("column/sandstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE), 0));
    public static final RegistryObject<Block> SANDSTONE_COLUMN1 = BLOCKS.register("column/sandstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE), 1));
    public static final RegistryObject<Block> SANDSTONE_COLUMN2 = BLOCKS.register("column/sandstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE), 2));
    public static final RegistryObject<Block> CHISELED_SANDSTONE_COLUMN0 = BLOCKS.register("column/chiseled_sandstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_SANDSTONE), 0));
    public static final RegistryObject<Block> CHISELED_SANDSTONE_COLUMN1 = BLOCKS.register("column/chiseled_sandstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_SANDSTONE), 1));
    public static final RegistryObject<Block> CHISELED_SANDSTONE_COLUMN2 = BLOCKS.register("column/chiseled_sandstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_SANDSTONE), 2));
    public static final RegistryObject<Block> CUT_SANDSTONE_COLUMN0 = BLOCKS.register("column/cut_sandstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE), 0));
    public static final RegistryObject<Block> CUT_SANDSTONE_COLUMN1 = BLOCKS.register("column/cut_sandstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE), 1));
    public static final RegistryObject<Block> CUT_SANDSTONE_COLUMN2 = BLOCKS.register("column/cut_sandstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE), 2));
    public static final RegistryObject<Block> SMOOTH_SANDSTONE_COLUMN0 = BLOCKS.register("column/smooth_sandstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE), 0));
    public static final RegistryObject<Block> SMOOTH_SANDSTONE_COLUMN1 = BLOCKS.register("column/smooth_sandstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE), 1));
    public static final RegistryObject<Block> SMOOTH_SANDSTONE_COLUMN2 = BLOCKS.register("column/smooth_sandstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE), 2));
    public static final RegistryObject<Block> BRICKS_COLUMN0 = BLOCKS.register("column/bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS), 0));
    public static final RegistryObject<Block> BRICKS_COLUMN1 = BLOCKS.register("column/bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS), 1));
    public static final RegistryObject<Block> BRICKS_COLUMN2 = BLOCKS.register("column/bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS), 2));
    public static final RegistryObject<Block> MOSSY_COBBLESTONE_COLUMN0 = BLOCKS.register("column/mossy_cobblestone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE), 0));
    public static final RegistryObject<Block> MOSSY_COBBLESTONE_COLUMN1 = BLOCKS.register("column/mossy_cobblestone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE), 1));
    public static final RegistryObject<Block> MOSSY_COBBLESTONE_COLUMN2 = BLOCKS.register("column/mossy_cobblestone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE), 2));
    public static final RegistryObject<Block> OBSIDIAN_COLUMN0 = BLOCKS.register("column/obsidian_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN), 0));
    public static final RegistryObject<Block> OBSIDIAN_COLUMN1 = BLOCKS.register("column/obsidian_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN), 1));
    public static final RegistryObject<Block> OBSIDIAN_COLUMN2 = BLOCKS.register("column/obsidian_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN), 2));
    public static final RegistryObject<Block> BASALT_COLUMN0 = BLOCKS.register("column/basalt_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.BASALT), 0));
    public static final RegistryObject<Block> BASALT_COLUMN1 = BLOCKS.register("column/basalt_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.BASALT), 1));
    public static final RegistryObject<Block> BASALT_COLUMN2 = BLOCKS.register("column/basalt_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.BASALT), 2));
    public static final RegistryObject<Block> POLISHED_BASALT_COLUMN0 = BLOCKS.register("column/polished_basalt_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT), 0));
    public static final RegistryObject<Block> POLISHED_BASALT_COLUMN1 = BLOCKS.register("column/polished_basalt_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT), 1));
    public static final RegistryObject<Block> POLISHED_BASALT_COLUMN2 = BLOCKS.register("column/polished_basalt_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BASALT), 2));
    public static final RegistryObject<Block> STONE_BRICKS_COLUMN0 = BLOCKS.register("column/stone_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS), 0));
    public static final RegistryObject<Block> STONE_BRICKS_COLUMN1 = BLOCKS.register("column/stone_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS), 1));
    public static final RegistryObject<Block> STONE_BRICKS_COLUMN2 = BLOCKS.register("column/stone_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS), 2));
    public static final RegistryObject<Block> MOSSY_STONE_BRICKS_COLUMN0 = BLOCKS.register("column/mossy_stone_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS), 0));
    public static final RegistryObject<Block> MOSSY_STONE_BRICKS_COLUMN1 = BLOCKS.register("column/mossy_stone_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS), 1));
    public static final RegistryObject<Block> MOSSY_STONE_BRICKS_COLUMN2 = BLOCKS.register("column/mossy_stone_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS), 2));
    public static final RegistryObject<Block> CRACKED_STONE_BRICKS_COLUMN0 = BLOCKS.register("column/cracked_stone_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS), 0));
    public static final RegistryObject<Block> CRACKED_STONE_BRICKS_COLUMN1 = BLOCKS.register("column/cracked_stone_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS), 1));
    public static final RegistryObject<Block> CRACKED_STONE_BRICKS_COLUMN2 = BLOCKS.register("column/cracked_stone_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS), 2));
    public static final RegistryObject<Block> CHISELED_STONE_BRICKS_COLUMN0 = BLOCKS.register("column/chiseled_stone_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS), 0));
    public static final RegistryObject<Block> CHISELED_STONE_BRICKS_COLUMN1 = BLOCKS.register("column/chiseled_stone_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS), 1));
    public static final RegistryObject<Block> CHISELED_STONE_BRICKS_COLUMN2 = BLOCKS.register("column/chiseled_stone_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS), 2));
    public static final RegistryObject<Block> NETHER_BRICKS_COLUMN0 = BLOCKS.register("column/nether_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS), 0));
    public static final RegistryObject<Block> NETHER_BRICKS_COLUMN1 = BLOCKS.register("column/nether_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS), 1));
    public static final RegistryObject<Block> NETHER_BRICKS_COLUMN2 = BLOCKS.register("column/nether_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS), 2));
    public static final RegistryObject<Block> EMERALD_BLOCK_COLUMN0 = BLOCKS.register("column/emerald_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK), 0));
    public static final RegistryObject<Block> EMERALD_BLOCK_COLUMN1 = BLOCKS.register("column/emerald_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK), 1));
    public static final RegistryObject<Block> EMERALD_BLOCK_COLUMN2 = BLOCKS.register("column/emerald_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK), 2));
    public static final RegistryObject<Block> QUARTZ_BLOCK_COLUMN0 = BLOCKS.register("column/quartz_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK), 0));
    public static final RegistryObject<Block> QUARTZ_BLOCK_COLUMN1 = BLOCKS.register("column/quartz_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK), 1));
    public static final RegistryObject<Block> QUARTZ_BLOCK_COLUMN2 = BLOCKS.register("column/quartz_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK), 2));
    public static final RegistryObject<Block> CHISELED_QUARTZ_BLOCK_COLUMN0 = BLOCKS.register("column/chiseled_quartz_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK), 0));
    public static final RegistryObject<Block> CHISELED_QUARTZ_BLOCK_COLUMN1 = BLOCKS.register("column/chiseled_quartz_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK), 1));
    public static final RegistryObject<Block> CHISELED_QUARTZ_BLOCK_COLUMN2 = BLOCKS.register("column/chiseled_quartz_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK), 2));
    public static final RegistryObject<Block> SMOOTH_QUARTZ_BLOCK_COLUMN0 = BLOCKS.register("column/smooth_quartz_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_QUARTZ), 0));
    public static final RegistryObject<Block> SMOOTH_QUARTZ_BLOCK_COLUMN1 = BLOCKS.register("column/smooth_quartz_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_QUARTZ), 1));
    public static final RegistryObject<Block> SMOOTH_QUARTZ_BLOCK_COLUMN2 = BLOCKS.register("column/smooth_quartz_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_QUARTZ), 2));
    public static final RegistryObject<Block> QUARTZ_PILLAR_COLUMN0 = BLOCKS.register("column/quartz_pillar_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR), 0));
    public static final RegistryObject<Block> QUARTZ_PILLAR_COLUMN1 = BLOCKS.register("column/quartz_pillar_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR), 1));
    public static final RegistryObject<Block> QUARTZ_PILLAR_COLUMN2 = BLOCKS.register("column/quartz_pillar_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR), 2));
    public static final RegistryObject<Block> PRISMARINE_COLUMN0 = BLOCKS.register("column/prismarine_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE), 0));
    public static final RegistryObject<Block> PRISMARINE_COLUMN1 = BLOCKS.register("column/prismarine_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE), 1));
    public static final RegistryObject<Block> PRISMARINE_COLUMN2 = BLOCKS.register("column/prismarine_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE), 2));
    public static final RegistryObject<Block> PRISMARINE_BRICKS_COLUMN0 = BLOCKS.register("column/prismarine_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS), 0));
    public static final RegistryObject<Block> PRISMARINE_BRICKS_COLUMN1 = BLOCKS.register("column/prismarine_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS), 1));
    public static final RegistryObject<Block> PRISMARINE_BRICKS_COLUMN2 = BLOCKS.register("column/prismarine_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS), 2));
    public static final RegistryObject<Block> DARK_PRISMARINE_COLUMN0 = BLOCKS.register("column/dark_prismarine_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DARK_PRISMARINE), 0));
    public static final RegistryObject<Block> DARK_PRISMARINE_COLUMN1 = BLOCKS.register("column/dark_prismarine_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DARK_PRISMARINE), 1));
    public static final RegistryObject<Block> DARK_PRISMARINE_COLUMN2 = BLOCKS.register("column/dark_prismarine_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.DARK_PRISMARINE), 2));
    public static final RegistryObject<Block> COAL_BLOCK_COLUMN0 = BLOCKS.register("column/coal_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK), 0));
    public static final RegistryObject<Block> COAL_BLOCK_COLUMN1 = BLOCKS.register("column/coal_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK), 1));
    public static final RegistryObject<Block> COAL_BLOCK_COLUMN2 = BLOCKS.register("column/coal_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK), 2));
    public static final RegistryObject<Block> CHISELED_RED_SANDSTONE_COLUMN0 = BLOCKS.register("column/chiseled_red_sandstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_RED_SANDSTONE), 0));
    public static final RegistryObject<Block> CHISELED_RED_SANDSTONE_COLUMN1 = BLOCKS.register("column/chiseled_red_sandstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_RED_SANDSTONE), 1));
    public static final RegistryObject<Block> CHISELED_RED_SANDSTONE_COLUMN2 = BLOCKS.register("column/chiseled_red_sandstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_RED_SANDSTONE), 2));
    public static final RegistryObject<Block> RED_SANDSTONE_COLUMN0 = BLOCKS.register("column/red_sandstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.RED_SANDSTONE), 0));
    public static final RegistryObject<Block> RED_SANDSTONE_COLUMN1 = BLOCKS.register("column/red_sandstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.RED_SANDSTONE), 1));
    public static final RegistryObject<Block> RED_SANDSTONE_COLUMN2 = BLOCKS.register("column/red_sandstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.RED_SANDSTONE), 2));
    public static final RegistryObject<Block> CUT_RED_SANDSTONE_COLUMN0 = BLOCKS.register("column/cut_red_sandstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_RED_SANDSTONE), 0));
    public static final RegistryObject<Block> CUT_RED_SANDSTONE_COLUMN1 = BLOCKS.register("column/cut_red_sandstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_RED_SANDSTONE), 1));
    public static final RegistryObject<Block> CUT_RED_SANDSTONE_COLUMN2 = BLOCKS.register("column/cut_red_sandstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_RED_SANDSTONE), 2));
    public static final RegistryObject<Block> SMOOTH_RED_SANDSTONE_COLUMN0 = BLOCKS.register("column/smooth_red_sandstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_RED_SANDSTONE), 0));
    public static final RegistryObject<Block> SMOOTH_RED_SANDSTONE_COLUMN1 = BLOCKS.register("column/smooth_red_sandstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_RED_SANDSTONE), 1));
    public static final RegistryObject<Block> SMOOTH_RED_SANDSTONE_COLUMN2 = BLOCKS.register("column/smooth_red_sandstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_RED_SANDSTONE), 2));
    public static final RegistryObject<Block> END_STONE_BRICKS_COLUMN0 = BLOCKS.register("column/end_stone_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS), 0));
    public static final RegistryObject<Block> END_STONE_BRICKS_COLUMN1 = BLOCKS.register("column/end_stone_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS), 1));
    public static final RegistryObject<Block> END_STONE_BRICKS_COLUMN2 = BLOCKS.register("column/end_stone_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICKS), 2));
    public static final RegistryObject<Block> RED_NETHER_BRICKS_COLUMN0 = BLOCKS.register("column/red_nether_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS), 0));
    public static final RegistryObject<Block> RED_NETHER_BRICKS_COLUMN1 = BLOCKS.register("column/red_nether_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS), 1));
    public static final RegistryObject<Block> RED_NETHER_BRICKS_COLUMN2 = BLOCKS.register("column/red_nether_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICKS), 2));
    public static final RegistryObject<Block> STRIPPED_CRIMSON_STEM_COLUMN0 = BLOCKS.register("column/stripped_crimson_stem_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_CRIMSON_STEM), 0));
    public static final RegistryObject<Block> STRIPPED_CRIMSON_STEM_COLUMN1 = BLOCKS.register("column/stripped_crimson_stem_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_CRIMSON_STEM), 1));
    public static final RegistryObject<Block> STRIPPED_CRIMSON_STEM_COLUMN2 = BLOCKS.register("column/stripped_crimson_stem_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_CRIMSON_STEM), 2));
    public static final RegistryObject<Block> STRIPPED_WARPED_STEM_COLUMN0 = BLOCKS.register("column/stripped_warped_stem_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_STEM), 0));
    public static final RegistryObject<Block> STRIPPED_WARPED_STEM_COLUMN1 = BLOCKS.register("column/stripped_warped_stem_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_STEM), 1));
    public static final RegistryObject<Block> STRIPPED_WARPED_STEM_COLUMN2 = BLOCKS.register("column/stripped_warped_stem_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_STEM), 2));
    public static final RegistryObject<Block> ANCIENT_DEBRIS_COLUMN0 = BLOCKS.register("column/ancient_debris_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.ANCIENT_DEBRIS), 0));
    public static final RegistryObject<Block> ANCIENT_DEBRIS_COLUMN1 = BLOCKS.register("column/ancient_debris_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.ANCIENT_DEBRIS), 1));
    public static final RegistryObject<Block> ANCIENT_DEBRIS_COLUMN2 = BLOCKS.register("column/ancient_debris_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.ANCIENT_DEBRIS), 2));
    public static final RegistryObject<Block> CRYING_OBSIDIAN_COLUMN0 = BLOCKS.register("column/crying_obsidian_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRYING_OBSIDIAN), 0));
    public static final RegistryObject<Block> CRYING_OBSIDIAN_COLUMN1 = BLOCKS.register("column/crying_obsidian_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRYING_OBSIDIAN), 1));
    public static final RegistryObject<Block> CRYING_OBSIDIAN_COLUMN2 = BLOCKS.register("column/crying_obsidian_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRYING_OBSIDIAN), 2));
    public static final RegistryObject<Block> CHISELED_POLISHED_BLACKSTONE_COLUMN0 = BLOCKS.register("column/chiseled_polished_blackstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_POLISHED_BLACKSTONE), 0));
    public static final RegistryObject<Block> CHISELED_POLISHED_BLACKSTONE_COLUMN1 = BLOCKS.register("column/chiseled_polished_blackstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_POLISHED_BLACKSTONE), 1));
    public static final RegistryObject<Block> CHISELED_POLISHED_BLACKSTONE_COLUMN2 = BLOCKS.register("column/chiseled_polished_blackstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_POLISHED_BLACKSTONE), 2));
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_COLUMN0 = BLOCKS.register("column/polished_blackstone_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE), 0));
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_COLUMN1 = BLOCKS.register("column/polished_blackstone_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE), 1));
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_COLUMN2 = BLOCKS.register("column/polished_blackstone_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE), 2));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICKS_COLUMN0 = BLOCKS.register("column/cracked_polished_blackstone_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS), 0));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICKS_COLUMN1 = BLOCKS.register("column/cracked_polished_blackstone_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS), 1));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICKS_COLUMN2 = BLOCKS.register("column/cracked_polished_blackstone_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS), 2));
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICKS_COLUMN0 = BLOCKS.register("column/polished_blackstone_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS), 0));
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICKS_COLUMN1 = BLOCKS.register("column/polished_blackstone_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS), 1));
    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICKS_COLUMN2 = BLOCKS.register("column/polished_blackstone_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BRICKS), 2));
    public static final RegistryObject<Block> QUARTZ_BRICKS_COLUMN0 = BLOCKS.register("column/quartz_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS), 0));
    public static final RegistryObject<Block> QUARTZ_BRICKS_COLUMN1 = BLOCKS.register("column/quartz_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS), 1));
    public static final RegistryObject<Block> QUARTZ_BRICKS_COLUMN2 = BLOCKS.register("column/quartz_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS), 2));
    public static final RegistryObject<Block> CHISELED_NETHER_BRICKS_COLUMN0 = BLOCKS.register("column/chiseled_nether_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_NETHER_BRICKS), 0));
    public static final RegistryObject<Block> CHISELED_NETHER_BRICKS_COLUMN1 = BLOCKS.register("column/chiseled_nether_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_NETHER_BRICKS), 1));
    public static final RegistryObject<Block> CHISELED_NETHER_BRICKS_COLUMN2 = BLOCKS.register("column/chiseled_nether_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_NETHER_BRICKS), 2));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICKS_COLUMN0 = BLOCKS.register("column/cracked_nether_bricks_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_NETHER_BRICKS), 0));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICKS_COLUMN1 = BLOCKS.register("column/cracked_nether_bricks_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_NETHER_BRICKS), 1));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICKS_COLUMN2 = BLOCKS.register("column/cracked_nether_bricks_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CRACKED_NETHER_BRICKS), 2));
    public static final RegistryObject<Block> OXIDIZED_COPPER_COLUMN0 = BLOCKS.register("column/oxidized_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER), 0));
    public static final RegistryObject<Block> OXIDIZED_COPPER_COLUMN1 = BLOCKS.register("column/oxidized_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER), 1));
    public static final RegistryObject<Block> OXIDIZED_COPPER_COLUMN2 = BLOCKS.register("column/oxidized_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_COPPER), 2));
    public static final RegistryObject<Block> WEATHERED_COPPER_COLUMN0 = BLOCKS.register("column/weathered_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER), 0));
    public static final RegistryObject<Block> WEATHERED_COPPER_COLUMN1 = BLOCKS.register("column/weathered_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER), 1));
    public static final RegistryObject<Block> WEATHERED_COPPER_COLUMN2 = BLOCKS.register("column/weathered_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_COPPER), 2));
    public static final RegistryObject<Block> EXPOSED_COPPER_COLUMN0 = BLOCKS.register("column/exposed_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER), 0));
    public static final RegistryObject<Block> EXPOSED_COPPER_COLUMN1 = BLOCKS.register("column/exposed_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER), 1));
    public static final RegistryObject<Block> EXPOSED_COPPER_COLUMN2 = BLOCKS.register("column/exposed_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_COPPER), 2));
    public static final RegistryObject<Block> COPPER_BLOCK_COLUMN0 = BLOCKS.register("column/copper_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), 0));
    public static final RegistryObject<Block> COPPER_BLOCK_COLUMN1 = BLOCKS.register("column/copper_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), 1));
    public static final RegistryObject<Block> COPPER_BLOCK_COLUMN2 = BLOCKS.register("column/copper_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), 2));
    public static final RegistryObject<Block> OXIDIZED_CUT_COPPER_COLUMN0 = BLOCKS.register("column/oxidized_cut_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER), 0));
    public static final RegistryObject<Block> OXIDIZED_CUT_COPPER_COLUMN1 = BLOCKS.register("column/oxidized_cut_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER), 1));
    public static final RegistryObject<Block> OXIDIZED_CUT_COPPER_COLUMN2 = BLOCKS.register("column/oxidized_cut_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER), 2));
    public static final RegistryObject<Block> WEATHERED_CUT_COPPER_COLUMN0 = BLOCKS.register("column/weathered_cut_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER), 0));
    public static final RegistryObject<Block> WEATHERED_CUT_COPPER_COLUMN1 = BLOCKS.register("column/weathered_cut_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER), 1));
    public static final RegistryObject<Block> WEATHERED_CUT_COPPER_COLUMN2 = BLOCKS.register("column/weathered_cut_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER), 2));
    public static final RegistryObject<Block> EXPOSED_CUT_COPPER_COLUMN0 = BLOCKS.register("column/exposed_cut_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER), 0));
    public static final RegistryObject<Block> EXPOSED_CUT_COPPER_COLUMN1 = BLOCKS.register("column/exposed_cut_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER), 1));
    public static final RegistryObject<Block> EXPOSED_CUT_COPPER_COLUMN2 = BLOCKS.register("column/exposed_cut_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER), 2));
    public static final RegistryObject<Block> CUT_COPPER_COLUMN0 = BLOCKS.register("column/cut_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER), 0));
    public static final RegistryObject<Block> CUT_COPPER_COLUMN1 = BLOCKS.register("column/cut_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER), 1));
    public static final RegistryObject<Block> CUT_COPPER_COLUMN2 = BLOCKS.register("column/cut_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER), 2));
    public static final RegistryObject<Block> WAXED_COPPER_BLOCK_COLUMN0 = BLOCKS.register("column/waxed_copper_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_COPPER_BLOCK), 0));
    public static final RegistryObject<Block> WAXED_COPPER_BLOCK_COLUMN1 = BLOCKS.register("column/waxed_copper_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_COPPER_BLOCK), 1));
    public static final RegistryObject<Block> WAXED_COPPER_BLOCK_COLUMN2 = BLOCKS.register("column/waxed_copper_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_COPPER_BLOCK), 2));
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_COLUMN0 = BLOCKS.register("column/waxed_weathered_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_COPPER), 0));
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_COLUMN1 = BLOCKS.register("column/waxed_weathered_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_COPPER), 1));
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_COLUMN2 = BLOCKS.register("column/waxed_weathered_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_COPPER), 2));
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_COLUMN0 = BLOCKS.register("column/waxed_exposed_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_COPPER), 0));
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_COLUMN1 = BLOCKS.register("column/waxed_exposed_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_COPPER), 1));
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_COLUMN2 = BLOCKS.register("column/waxed_exposed_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_COPPER), 2));
    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_COLUMN0 = BLOCKS.register("column/waxed_oxidized_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_COPPER), 0));
    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_COLUMN1 = BLOCKS.register("column/waxed_oxidized_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_COPPER), 1));
    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_COLUMN2 = BLOCKS.register("column/waxed_oxidized_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_COPPER), 2));
    public static final RegistryObject<Block> WAXED_OXIDIZED_CUT_COPPER_COLUMN0 = BLOCKS.register("column/waxed_oxidized_cut_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER), 0));
    public static final RegistryObject<Block> WAXED_OXIDIZED_CUT_COPPER_COLUMN1 = BLOCKS.register("column/waxed_oxidized_cut_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER), 1));
    public static final RegistryObject<Block> WAXED_OXIDIZED_CUT_COPPER_COLUMN2 = BLOCKS.register("column/waxed_oxidized_cut_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER), 2));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_COPPER_COLUMN0 = BLOCKS.register("column/waxed_weathered_cut_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_CUT_COPPER), 0));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_COPPER_COLUMN1 = BLOCKS.register("column/waxed_weathered_cut_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_CUT_COPPER), 1));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_COPPER_COLUMN2 = BLOCKS.register("column/waxed_weathered_cut_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_CUT_COPPER), 2));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_COPPER_COLUMN0 = BLOCKS.register("column/waxed_exposed_cut_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_CUT_COPPER), 0));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_COPPER_COLUMN1 = BLOCKS.register("column/waxed_exposed_cut_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_CUT_COPPER), 1));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_COPPER_COLUMN2 = BLOCKS.register("column/waxed_exposed_cut_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_CUT_COPPER), 2));
    public static final RegistryObject<Block> WAXED_CUT_COPPER_COLUMN0 = BLOCKS.register("column/waxed_cut_copper_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_CUT_COPPER), 0));
    public static final RegistryObject<Block> WAXED_CUT_COPPER_COLUMN1 = BLOCKS.register("column/waxed_cut_copper_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_CUT_COPPER), 1));
    public static final RegistryObject<Block> WAXED_CUT_COPPER_COLUMN2 = BLOCKS.register("column/waxed_cut_copper_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.WAXED_CUT_COPPER), 2));
    public static final RegistryObject<Block> SMOOTH_BASALT_COLUMN0 = BLOCKS.register("column/smooth_basalt_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT), 0));
    public static final RegistryObject<Block> SMOOTH_BASALT_COLUMN1 = BLOCKS.register("column/smooth_basalt_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT), 1));
    public static final RegistryObject<Block> SMOOTH_BASALT_COLUMN2 = BLOCKS.register("column/smooth_basalt_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_BASALT), 2));
    public static final RegistryObject<Block> PURPUR_BLOCK_COLUMN0 = BLOCKS.register("column/purpur_block_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK), 0));
    public static final RegistryObject<Block> PURPUR_BLOCK_COLUMN1 = BLOCKS.register("column/purpur_block_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK), 1));
    public static final RegistryObject<Block> PURPUR_BLOCK_COLUMN2 = BLOCKS.register("column/purpur_block_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK), 2));
    public static final RegistryObject<Block> PURPUR_PILLAR_COLUMN0 = BLOCKS.register("column/purpur_pillar_column0", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_PILLAR), 0));
    public static final RegistryObject<Block> PURPUR_PILLAR_COLUMN1 = BLOCKS.register("column/purpur_pillar_column1", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_PILLAR), 1));
    public static final RegistryObject<Block> PURPUR_PILLAR_COLUMN2 = BLOCKS.register("column/purpur_pillar_column2", () -> new ColumnBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_PILLAR), 2));


    
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
