package asyetuntitled.common.block;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import asyetuntitled.common.block.entity.TouchStoneBE;
import asyetuntitled.common.player.spawn.SpawnPoint;
import asyetuntitled.common.util.CommonReflectionHelper;
import asyetuntitled.common.util.capability.LevelSpawns;
import asyetuntitled.common.util.capability.LevelSpawnsProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.ServerLifecycleHooks;

public class TouchStoneBlock extends Block implements EntityBlock 
{
    private static final ImmutableList<Vec3i> RESPAWN_HORIZONTAL_OFFSETS = ImmutableList.of(new Vec3i(-1, 0, -2), new Vec3i(0, 0, -2), new Vec3i(1, 0, -2), new Vec3i(-2, 0, -1), new Vec3i(-2, 0, 0), new Vec3i(-2, 0, 1), new Vec3i(-1, 0, 2), new Vec3i(0, 0, 2), new Vec3i(1, 0, 2), new Vec3i(2, 0, -1), new Vec3i(2, 0, 0), new Vec3i(2, 0, 1));
    private static final ImmutableList<Vec3i> RESPAWN_OFFSETS = (new Builder<Vec3i>()).addAll(RESPAWN_HORIZONTAL_OFFSETS).addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::below).iterator()).addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::above).iterator()).add(new Vec3i(0, 1, 0)).build();
    private final VoxelShape shape;
    
    public TouchStoneBlock() {
        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2.0f)
                .noOcclusion()
                .requiresCorrectToolForDrops()
            );
        this.shape = makeShape();
    }
    
	private VoxelShape makeShape()
    {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }

    @Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) 
	{
		return new TouchStoneBE(pos, state);
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
	{
	    return this.shape;
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if (!level.isClientSide()) {
			return (lvl, pos, stt, te) -> {
				if (te instanceof TouchStoneBE touchstone) touchstone.tickServer();
			};
		}
		return null;
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
	{
	    InteractionResult retResult = InteractionResult.PASS;
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof TouchStoneBE touchstone && player instanceof ServerPlayer sPlayer) 
        {
            Block[] blocks = {Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.COBBLESTONE,
                    Blocks.STRIPPED_SPRUCE_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.STRIPPED_OAK_LOG,
                    Blocks.LAPIS_BLOCK, Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.NETHERITE_BLOCK, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE,
                    Blocks.BRICKS, Blocks.MOSSY_COBBLESTONE, Blocks.OBSIDIAN, Blocks.BASALT, Blocks.POLISHED_BASALT, Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS,
                    Blocks.CHISELED_STONE_BRICKS, Blocks.NETHER_BRICKS, Blocks.EMERALD_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, 
                    Blocks.DARK_PRISMARINE, Blocks.COAL_BLOCK, Blocks.CHISELED_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE, Blocks.END_STONE_BRICKS, Blocks.RED_NETHER_BRICKS,
                    Blocks.STRIPPED_CRIMSON_STEM, Blocks.STRIPPED_WARPED_STEM, Blocks.ANCIENT_DEBRIS, Blocks.CRYING_OBSIDIAN, Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE,
                    Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.QUARTZ_BRICKS, Blocks.CHISELED_NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS,
                    Blocks.OXIDIZED_COPPER, Blocks.WEATHERED_COPPER, Blocks.EXPOSED_COPPER, Blocks.COPPER_BLOCK, Blocks.OXIDIZED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.EXPOSED_CUT_COPPER,
                    Blocks.CUT_COPPER, Blocks.WAXED_COPPER_BLOCK, Blocks.WAXED_WEATHERED_COPPER, Blocks.WAXED_EXPOSED_COPPER, Blocks.WAXED_OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER,
                    Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_CUT_COPPER, Blocks.SMOOTH_BASALT};

            String[] models = {"_column_base", "_column_centre_short_short", "_column_centre_short_tall", "_column_centre_tall_tall", "_column_centre_tall_short", "_column_crown"};
            
            for(Block block : blocks) {
                for(int i = 0 ; i < 3 ; i++)
                {
                  String name = block.getRegistryName().getPath();
                  String type = i == 0 ? "Wide " : i == 2 ? "Narrow " : "";
                  String[] names = name.split("_");
                  String nameC = "";
                  for(String s : names)
                  {
                      nameC += s.substring(0,1).toUpperCase() + s.substring(1) + " ";
                  }

                  System.out.println("\"block.asyetuntitled.column."+name+"_column"+i+"\": \""+type+nameC + "Column\",");
//                  System.out.println
//                    public static final RegistryObject<BlockItem> COLUMN0 = ITEMS.register("column0", () -> new BlockItem(BlocksRegistry.TOUCHSTONE_COLUMN0.get(), new Item.Properties().tab(ITEM_GROUP)));

//                    System.out.println("public static final RegistryObject<BlockItem> " + name.toUpperCase() + "COLUMN" + i + " = ITEMS.register(\""+name.toLowerCase()+"_column"+i+", () -> new BlockItem(BlocksRegistry." + name.toUpperCase()+"_COLUMN"+i+".get(), newItem.Properties().tab(ITEM_GROUP)));");
//                        try
//                        {
//                            File file = new File("gen_"+name+"_column"+i+".json");
//                            file.createNewFile();
//                            FileWriter writer = new FileWriter("gen_"+name + "_column" + i + ".json");
//                            writer.write("{"
//                                    + "\n\t\"parent\": \"asyetuntitled:item/column/column" + i +"\","
//                                    + "\n\t\"ambientocclusion\": false,"
//                                    + "\n\t\"textures\": {"
//                                    + "\n\t\t\"top\": \"minecraft:block/" + name + "\","
//                                    + "\n\t\t\"side\": \"minecraft:block/" + name + "\""
//                                    + "\n\t}"
//                                    + "\n}"
//                                    );
//                            writer.close();
//                        } catch (IOException e)
//                        {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
                   
                }
            }
//            SpawnPoint point = new SpawnPoint(pos, level.dimension());
//            LazyOptional<LevelSpawns> optional = ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getCapability(LevelSpawnsProvider.LEVEL_SPAWNS);
//            if(!optional.map(spawns -> spawns.isPlayerSpawn(sPlayer, point)).get())
//            {
//                retResult = InteractionResult.CONSUME;
//                optional.ifPresent(spawns -> spawns.addPlayerSpawn(sPlayer, point));
//            }
        }
        return retResult;
	}
	
	@Override
	public void destroy(LevelAccessor level, BlockPos pos, BlockState state) 
	{
	    if(!level.isClientSide() && level instanceof ServerLevel s)
	    ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getCapability(LevelSpawnsProvider.LEVEL_SPAWNS).ifPresent(spawns -> {
            spawns.destroySpawnForAll(new SpawnPoint(pos, s.dimension()));
        });
	}
	
	public static Optional<Vec3> findStandUpPosition(EntityType<?> p_55840_, CollisionGetter p_55841_, BlockPos p_55842_) {
	       Optional<Vec3> optional = findStandUpPosition(p_55840_, p_55841_, p_55842_, true);
	       return optional.isPresent() ? optional : findStandUpPosition(p_55840_, p_55841_, p_55842_, false);
	}
	
	private static Optional<Vec3> findStandUpPosition(EntityType<?> p_55844_, CollisionGetter p_55845_, BlockPos p_55846_, boolean p_55847_) {
	    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	    for(Vec3i vec3i : RESPAWN_OFFSETS) {
	        blockpos$mutableblockpos.set(p_55846_).move(vec3i);
	        Vec3 vec3 = DismountHelper.findSafeDismountLocation(p_55844_, p_55845_, blockpos$mutableblockpos, p_55847_);
	        if (vec3 != null) {
                return Optional.of(vec3);
	        }
	    }

	    return Optional.empty();
	}

}
