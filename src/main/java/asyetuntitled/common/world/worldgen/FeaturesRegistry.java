package asyetuntitled.common.world.worldgen;

import java.util.List;
import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;

import asyetuntitled.common.block.BlocksRegistry;
import asyetuntitled.common.world.worldgen.trees.StumpyBendingTrunkPlacer;
import asyetuntitled.common.world.worldgen.trees.StumpyDarkOakTrunkPlacer;
import asyetuntitled.common.world.worldgen.trees.StumpyFancyTrunkPlacer;
import asyetuntitled.common.world.worldgen.trees.StumpyForkingTrunkPlacer;
import asyetuntitled.common.world.worldgen.trees.StumpyGiantTrunkPlacer;
import asyetuntitled.common.world.worldgen.trees.StumpyMegaJungleTrunkPlacer;
import asyetuntitled.common.world.worldgen.trees.StumpyStraightTrunkPlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

public class FeaturesRegistry 
{
//	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, AsYetUntitled.MODID);
	

	//GRASS BASES
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> HELPFUL_GRASS_BASE = FeatureUtils.register("helpful_grass", Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(BlocksRegistry.GRASSUSEFUL.get()), 32));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> HELPFUL_GRASS_JUNGLE_BASE = FeatureUtils.register("helpful_grass_jungle", Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlocksRegistry.GRASSUSEFUL.get().defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 1))), BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.not(BlockPredicate.matchesBlock(Blocks.PODZOL, new BlockPos(0, -1, 0)))))));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> HELPFUL_GRASS_TAIGA_BASE = FeatureUtils.register("helpful_taiga_grass", Feature.RANDOM_PATCH, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(BlocksRegistry.GRASSUSEFUL.get().defaultBlockState(), 1).add(Blocks.FERN.defaultBlockState(), 4)), 32));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> HELPFUL_TALL_GRASS_BASE = FeatureUtils.register("helpful_tall_grass", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.TALLGRASSUSEFUL.get()))));
	
	//GRASS FEATURES
	public static final Holder<PlacedFeature> HELPFUL_GRASS_BADLANDS = PlacementUtils.register("helpful_grass_badlands", HELPFUL_GRASS_BASE, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	public static final Holder<PlacedFeature> HELPFUL_GRASS_FOREST = PlacementUtils.register("helpful_grass_forest", HELPFUL_GRASS_BASE, VegetationPlacements.worldSurfaceSquaredWithCount(2));
	public static final Holder<PlacedFeature> HELPFUL_TALL_GRASS = PlacementUtils.register("helpful_tall_grass", HELPFUL_TALL_GRASS_BASE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	public static final Holder<PlacedFeature> HELPFUL_TALL_GRASS_2 = PlacementUtils.register("helpful_tall_grass_2", HELPFUL_TALL_GRASS_BASE, NoiseThresholdCountPlacement.of(-0.8D, 0, 7), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	public static final Holder<PlacedFeature> HELPFUL_GRASS_JUNGLE = PlacementUtils.register("helpful_grass_jungle", HELPFUL_GRASS_JUNGLE_BASE, VegetationPlacements.worldSurfaceSquaredWithCount(25));
	public static final Holder<PlacedFeature> HELPFUL_GRASS_NORMAL = PlacementUtils.register("helpful_grass_normal", HELPFUL_GRASS_BASE, VegetationPlacements.worldSurfaceSquaredWithCount(5));
	public static final Holder<PlacedFeature> HELPFUL_GRASS_PLAIN = PlacementUtils.register("helpful_grass_plain", HELPFUL_GRASS_BASE, NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
	public static final Holder<PlacedFeature> HELPFUL_GRASS_SAVANNA = PlacementUtils.register("helpful_grass_savanna", HELPFUL_GRASS_BASE, VegetationPlacements.worldSurfaceSquaredWithCount(20));
	public static final Holder<PlacedFeature> HELPFUL_GRASS_TAIGA = PlacementUtils.register("helpful_grass_taiga", HELPFUL_GRASS_TAIGA_BASE, VegetationPlacements.worldSurfaceSquaredWithCount(7));
	public static final Holder<PlacedFeature> HELPFUL_GRASS_TAIGA_2 = PlacementUtils.register("helpful_grass_taiga_2", HELPFUL_GRASS_TAIGA_BASE, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

	private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
		return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
	}
	
	//BEEHIVES
	private static final BeehiveDecorator BEEHIVE_0002 = new BeehiveDecorator(0.002F);
	private static final BeehiveDecorator BEEHIVE_002 = new BeehiveDecorator(0.02F);
	private static final BeehiveDecorator BEEHIVE_005 = new BeehiveDecorator(0.05F);
	private static final BeehiveDecorator BEEHIVE = new BeehiveDecorator(1.0F);
	
	//TREES
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_ACACIA = FeatureUtils.register("stumpy_acacia", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.ACACIA_LOG), new StumpyForkingTrunkPlacer(5, 2, 2), BlockStateProvider.simple(Blocks.ACACIA_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_AZALEA_TREE = FeatureUtils.register("stumpy_azalea_tree", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new StumpyBendingTrunkPlacer(4, 2, 0, 3, UniformInt.of(1, 2)), new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.AZALEA_LEAVES.defaultBlockState(), 3).add(Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState(), 1)), new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50), new TwoLayersFeatureSize(1, 0, 1))).dirt(BlockStateProvider.simple(Blocks.ROOTED_DIRT)).forceDirt().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_BIRCH = FeatureUtils.register("stumpy_birch", Feature.TREE, createBirch().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_BIRCH_BEES_002 = FeatureUtils.register("stumpy_birch_bees_002", Feature.TREE, createBirch().decorators(List.of(BEEHIVE_002)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_BIRCH_BEES_005 = FeatureUtils.register("stumpy_birch_bees_005", Feature.TREE, createBirch().decorators(List.of(BEEHIVE_005)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_BIRCH_BEES_0002 = FeatureUtils.register("stumpy_birch_bees_0002", Feature.TREE, createBirch().decorators(List.of(BEEHIVE_0002)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_DARK_OAK = FeatureUtils.register("stumpy_dark_oak", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG), new StumpyDarkOakTrunkPlacer(6, 2, 1), BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES), new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_FANCY_OAK = FeatureUtils.register("stumpy_fancy_oak", Feature.TREE, createFancyOak().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_FANCY_OAK_BEES = FeatureUtils.register("stumpy_fancy_oak_bees", Feature.TREE, createFancyOak().decorators(List.of(BEEHIVE)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_FANCY_OAK_BEES_002 = FeatureUtils.register("stumpy_fancy_oak_bees_002", Feature.TREE, createFancyOak().decorators(List.of(BEEHIVE_002)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_FANCY_OAK_BEES_005 = FeatureUtils.register("stumpy_fancy_oak_bees_005", Feature.TREE, createFancyOak().decorators(List.of(BEEHIVE_005)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_FANCY_OAK_BEES_0002 = FeatureUtils.register("stumpy_fancy_oak_bees_0002", Feature.TREE, createFancyOak().decorators(List.of(BEEHIVE_0002)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_JUNGLE_BUSH = FeatureUtils.register("stumpy_jungle_bush", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.JUNGLE_LOG), new StumpyStraightTrunkPlacer(1, 0, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2), new TwoLayersFeatureSize(0, 0, 0))).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_JUNGLE_TREE = FeatureUtils.register("stumpy_jungle_tree", Feature.TREE, createJungleTree().decorators(ImmutableList.of(new CocoaDecorator(0.2F), TrunkVineDecorator.INSTANCE, LeaveVineDecorator.INSTANCE)).ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_JUNGLE_TREE_NO_VINE = FeatureUtils.register("stumpy_jungle_tree_no_vine", Feature.TREE, createJungleTree().ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_MEGA_JUNGLE_TREE = FeatureUtils.register("stumpy_mega_jungle_tree", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.JUNGLE_LOG), new StumpyMegaJungleTrunkPlacer(10, 2, 19), BlockStateProvider.simple(Blocks.JUNGLE_LEAVES), new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(TrunkVineDecorator.INSTANCE, LeaveVineDecorator.INSTANCE)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_MEGA_PINE = FeatureUtils.register("stumpy_mega_pine", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StumpyGiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)))).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_MEGA_SPRUCE = FeatureUtils.register("stumpy_mega_spruce", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StumpyGiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(Blocks.PODZOL)))).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_OAK = FeatureUtils.register("stumpy_oak", Feature.TREE, createOak().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_OAK_BEES_002 = FeatureUtils.register("stumpy_oak_bees_002", Feature.TREE, createOak().decorators(List.of(BEEHIVE_002)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_OAK_BEES_005 = FeatureUtils.register("stumpy_oak_bees_005", Feature.TREE, createOak().decorators(List.of(BEEHIVE_005)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_OAK_BEES_0002 = FeatureUtils.register("stumpy_oak_bees_0002", Feature.TREE, createOak().decorators(List.of(BEEHIVE_0002)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_PINE = FeatureUtils.register("stumpy_pine", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StumpyStraightTrunkPlacer(6, 4, 0), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)), new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_SPRUCE = FeatureUtils.register("stumpy_spruce", Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG), new StumpyStraightTrunkPlacer(5, 2, 1), BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)), new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_SUPER_BIRCH_BEES = FeatureUtils.register("stumpy_super_birch_bees", Feature.TREE, createSuperBirch().decorators(ImmutableList.of(BEEHIVE)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_SUPER_BIRCH_BEES_0002 = FeatureUtils.register("stumpy_super_birch_bees_0002", Feature.TREE, createSuperBirch().decorators(ImmutableList.of(BEEHIVE_0002)).build());
	public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> STUMPY_SWAMP_OAK = FeatureUtils.register("stumpy_swamp_oak", Feature.TREE, createStraightBlobTree(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 5, 3, 0, 3).decorators(ImmutableList.of(LeaveVineDecorator.INSTANCE)).build());

	
	//TREE PLACEMENTS
//	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_CRIMSON_FUNGI = PlacementUtils.register("stumpy_crimson_fungi", STUMPY_CRIMSON_FUNGUS, CountOnEveryLayerPlacement.of(8), BiomeFilter.biome());
//	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_WARPED_FUNGI = PlacementUtils.register("stumpy_warped_fungi", STUMPY_WARPED_FUNGUS, CountOnEveryLayerPlacement.of(8), BiomeFilter.biome());
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_ACACIA_CHECKED = PlacementUtils.register("stumpy_acacia_checked", STUMPY_ACACIA, PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_DARK_OAK_CHECKED = PlacementUtils.register("stumpy_dark_oak_checked", STUMPY_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_BIRCH_BEES_002 = PlacementUtils.register("stumpy_birch_bees_002", STUMPY_BIRCH_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_BIRCH_BEES_0002_PLACED = PlacementUtils.register("stumpy_birch_bees_0002", STUMPY_BIRCH_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_BIRCH_CHECKED = PlacementUtils.register("stumpy_birch_checked", STUMPY_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_FANCY_OAK_BEES = PlacementUtils.register("stumpy_fancy_oak_bees", STUMPY_FANCY_OAK_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_FANCY_OAK_BEES_002 = PlacementUtils.register("stumpy_fancy_oak_bees_002", STUMPY_FANCY_OAK_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_FANCY_OAK_BEES_0002 = PlacementUtils.register("stumpy_fancy_oak_bees_0002", STUMPY_FANCY_OAK_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_FANCY_OAK_CHECKED = PlacementUtils.register("stumpy_fancy_oak_checked", STUMPY_FANCY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_JUNGLE_BUSH = PlacementUtils.register("stumpy_jungle_bush", STUMPY_JUNGLE_BUSH, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_JUNGLE_TREE_CHECKED = PlacementUtils.register("stumpy_jungle_tree", STUMPY_JUNGLE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_MEGA_JUNGLE_TREE_CHECKED = PlacementUtils.register("stumpy_mega_jungle_tree_checked", STUMPY_MEGA_JUNGLE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_MEGA_PINE_CHECKED = PlacementUtils.register("stumpy_mega_pine_checked", STUMPY_MEGA_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_MEGA_SPRUCE_CHECKED = PlacementUtils.register("stumpy_mega_spruce_checked", STUMPY_MEGA_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_OAK_BEES_002 = PlacementUtils.register("stumpy_oak_bees_002", STUMPY_OAK_BEES_002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_OAK_BEES_0002 = PlacementUtils.register("stumpy_oak_bees_0002", STUMPY_OAK_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_OAK_CHECKED = PlacementUtils.register("stumpy_oak_checked", STUMPY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_PINE_CHECKED = PlacementUtils.register("stumpy_pine_checked", STUMPY_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_PINE_ON_SNOW = PlacementUtils.register("stumpy_pine_on_snow", STUMPY_PINE, TreePlacements.SNOW_TREE_FILTER_DECORATOR);
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_SPRUCE_CHECKED = PlacementUtils.register("stumpy_spruce_checked", STUMPY_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_SPRUCE_ON_SNOW = PlacementUtils.register("stumpy_spruce_on_snow", STUMPY_SPRUCE, TreePlacements.SNOW_TREE_FILTER_DECORATOR);
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_SUPER_BIRCH_BEES = PlacementUtils.register("stumpy_super_birch_bees", STUMPY_SUPER_BIRCH_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> PLACEMENT_STUMPY_SUPER_BIRCH_BEES_0002 = PlacementUtils.register("stumpy_super_birch_bees_0002", STUMPY_SUPER_BIRCH_BEES_0002, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

	
	//TREE BASES
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_BIRCH_TALL_BASE = FeatureUtils.register("stumpy_birch_tall_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_SUPER_BIRCH_BEES_0002, 0.5F)), PLACEMENT_STUMPY_BIRCH_BEES_0002_PLACED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_DARK_FOREST_VEGETATION_BASE = FeatureUtils.register("stumpy_dark_forest_vegetation_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), 0.05F), new WeightedPlacedFeature(PLACEMENT_STUMPY_DARK_OAK_CHECKED, 0.6666667F), new WeightedPlacedFeature(PLACEMENT_STUMPY_BIRCH_CHECKED, 0.2F), new WeightedPlacedFeature(PLACEMENT_STUMPY_FANCY_OAK_CHECKED, 0.1F)), PLACEMENT_STUMPY_OAK_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_BIRCH_AND_OAK_BASE = FeatureUtils.register("stumpy_trees_birch_and_oak_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_BIRCH_BEES_0002_PLACED, 0.2F), new WeightedPlacedFeature(PLACEMENT_STUMPY_FANCY_OAK_BEES_0002, 0.1F)), PLACEMENT_STUMPY_OAK_BEES_0002));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_FLOWER_FOREST_BASE = FeatureUtils.register("stumpy_trees_flower_forest_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_BIRCH_BEES_002, 0.2F), new WeightedPlacedFeature(PLACEMENT_STUMPY_FANCY_OAK_BEES_002, 0.1F)), PLACEMENT_STUMPY_OAK_BEES_002));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_GROVE_BASE = FeatureUtils.register("stumpy_trees_grove_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_PINE_ON_SNOW, 0.33333334F)), PLACEMENT_STUMPY_SPRUCE_ON_SNOW));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_JUNGLE_BASE = FeatureUtils.register("stumpy_trees_jungle_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_FANCY_OAK_CHECKED, 0.1F), new WeightedPlacedFeature(PLACEMENT_STUMPY_JUNGLE_BUSH, 0.5F), new WeightedPlacedFeature(PLACEMENT_STUMPY_MEGA_JUNGLE_TREE_CHECKED, 0.33333334F)), PLACEMENT_STUMPY_JUNGLE_TREE_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_MEADOW_BASE = FeatureUtils.register("stumpy_meadow_trees_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_FANCY_OAK_BEES, 0.5F)), PLACEMENT_STUMPY_SUPER_BIRCH_BEES));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_OLD_GROWTH_PINE_TAIGA_BASE = FeatureUtils.register("stumpy_trees_old_growth_pine_taiga_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_MEGA_SPRUCE_CHECKED, 0.025641026F), new WeightedPlacedFeature(PLACEMENT_STUMPY_MEGA_PINE_CHECKED, 0.30769232F), new WeightedPlacedFeature(PLACEMENT_STUMPY_PINE_CHECKED, 0.33333334F)), PLACEMENT_STUMPY_SPRUCE_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_OLD_GROWTH_SPRUCE_TAIGA_BASE = FeatureUtils.register("stumpy_trees_old_growth_spruce_taiga_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_MEGA_SPRUCE_CHECKED, 0.33333334F), new WeightedPlacedFeature(PLACEMENT_STUMPY_PINE_CHECKED, 0.33333334F)), PLACEMENT_STUMPY_SPRUCE_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_PLAINS_BASE = FeatureUtils.register("stumpy_trees_plains_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(STUMPY_FANCY_OAK_BEES_005), 0.33333334F)), PlacementUtils.inlinePlaced(STUMPY_OAK_BEES_005)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_SPARSE_JUNGLE_BASE = FeatureUtils.register("stumpy_trees_sparse_jungle_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_FANCY_OAK_CHECKED, 0.1F), new WeightedPlacedFeature(PLACEMENT_STUMPY_JUNGLE_BUSH, 0.5F)), PLACEMENT_STUMPY_JUNGLE_TREE_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_TAIGA_BASE = FeatureUtils.register("stumpy_trees_taiga_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_PINE_CHECKED, 0.33333334F)), PLACEMENT_STUMPY_SPRUCE_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_SAVANNA_BASE = FeatureUtils.register("stumpy_trees_savanna_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_ACACIA_CHECKED, 0.8F)), PLACEMENT_STUMPY_OAK_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_WATER_BASE = FeatureUtils.register("stumpy_trees_water_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_FANCY_OAK_CHECKED, 0.1F)), PLACEMENT_STUMPY_OAK_CHECKED));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> STUMPY_TREES_WINDSWEPT_HILLS_BASE = FeatureUtils.register("stumpy_trees_windswept_hills_base", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PLACEMENT_STUMPY_SPRUCE_CHECKED, 0.666F), new WeightedPlacedFeature(PLACEMENT_STUMPY_FANCY_OAK_CHECKED, 0.1F)), PLACEMENT_STUMPY_OAK_CHECKED));

	
	//TREE FEATURES
	public static final Holder<PlacedFeature> STUMPY_BIRCH_TALL = PlacementUtils.register("stumpy_birch_tall", STUMPY_BIRCH_TALL_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_DARK_FOREST_VEGETATION = PlacementUtils.register("stumpy_dark_forest_vegetation", STUMPY_DARK_FOREST_VEGETATION_BASE, CountPlacement.of(16), InSquarePlacement.spread(), VegetationPlacements.TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome());
	public static final Holder<PlacedFeature> STUMPY_TREES_BADLANDS = PlacementUtils.register("stumpy_trees_badlands", STUMPY_OAK, VegetationPlacements.treePlacement(PlacementUtils.countExtra(5, 0.1F, 1), Blocks.OAK_SAPLING));
	public static final Holder<PlacedFeature> STUMPY_TREES_BIRCH = PlacementUtils.register("stumpy_trees_birch", STUMPY_BIRCH_BEES_0002, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1), Blocks.BIRCH_SAPLING));
	public static final Holder<PlacedFeature> STUMPY_TREES_BIRCH_AND_OAK = PlacementUtils.register("stumpy_trees_birch_and_oak", STUMPY_TREES_BIRCH_AND_OAK_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_FLOWER_FOREST = PlacementUtils.register("stumpy_trees_flower_forest", STUMPY_TREES_FLOWER_FOREST_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_GROVE = PlacementUtils.register("stumpy_trees_grove", STUMPY_TREES_GROVE_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_JUNGLE = PlacementUtils.register("stumpy_trees_jungle", STUMPY_TREES_JUNGLE_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(50, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_MEADOW = PlacementUtils.register("stumpy_trees_meadow", STUMPY_TREES_MEADOW_BASE, VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(100)));
	public static final Holder<PlacedFeature> STUMPY_TREES_OLD_GROWTH_PINE_TAIGA = PlacementUtils.register("stumpy_trees_old_growth_pine_taiga", STUMPY_TREES_OLD_GROWTH_PINE_TAIGA_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_OLD_GROWTH_SPRUCE_TAIGA = PlacementUtils.register("stumpy_trees_old_growth_spruce_taiga", STUMPY_TREES_OLD_GROWTH_SPRUCE_TAIGA_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_PLAINS = PlacementUtils.register("stumpy_trees_plains", STUMPY_TREES_PLAINS_BASE, PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(), VegetationPlacements.TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome());
	public static final Holder<PlacedFeature> STUMPY_TREES_SAVANNA = PlacementUtils.register("stumpy_trees_savanna", STUMPY_TREES_SAVANNA_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_SPARSE_JUNGLE = PlacementUtils.register("stumpy_trees_sparse_jungle", STUMPY_TREES_SPARSE_JUNGLE_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_SNOWY = PlacementUtils.register("stumpy_trees_snowy", STUMPY_SPRUCE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> STUMPY_TREES_SWAMP = PlacementUtils.register("stumpy_trees_swamp", STUMPY_SWAMP_OAK, PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(2), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)));
	public static final Holder<PlacedFeature> STUMPY_TREES_TAIGA = PlacementUtils.register("stumpy_trees_taiga", STUMPY_TREES_TAIGA_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_WATER = PlacementUtils.register("stumpy_trees_water", STUMPY_TREES_WATER_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_WINDSWEPT_FOREST = PlacementUtils.register("stumpy_trees_windswept_forest", STUMPY_TREES_WINDSWEPT_HILLS_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_WINDSWEPT_HILLS = PlacementUtils.register("stumpy_trees_windswept_hills", STUMPY_TREES_WINDSWEPT_HILLS_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1)));
	public static final Holder<PlacedFeature> STUMPY_TREES_WINDSWEPT_SAVANNA = PlacementUtils.register("stumpy_trees_windswept_savanna", STUMPY_TREES_SAVANNA_BASE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));

	
	private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block p_195147_, Block p_195148_, int x, int y, int z, int p_195152_) {
		return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new StumpyStraightTrunkPlacer(x, y, z), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
	}

	private static TreeConfiguration.TreeConfigurationBuilder createFancyOak() {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new StumpyFancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
	}
	
	private static TreeConfiguration.TreeConfigurationBuilder createOak() {
		return createStraightBlobTree(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 2, 0, 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder createBirch() {
		return createStraightBlobTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 0, 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder createSuperBirch() {
		return createStraightBlobTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 6, 2).ignoreVines();
	}

	private static TreeConfiguration.TreeConfigurationBuilder createJungleTree() {
		return createStraightBlobTree(Blocks.JUNGLE_LOG, Blocks.JUNGLE_LEAVES, 4, 8, 0, 2);
	}

	public static BlockState getStumped(BlockState state) 
	{
		String name = state.toString().substring(16, state.toString().indexOf("}"));
		switch(name)
		{
			case "oak_log":
				state = Blocks.OAK_PLANKS.defaultBlockState();
				break;
			case "birch_log":
				state = Blocks.BIRCH_PLANKS.defaultBlockState();
				break;
			case "spruce_log":
				state = Blocks.SPRUCE_PLANKS.defaultBlockState();
				break;
			case "jungle_log":
				state = Blocks.JUNGLE_PLANKS.defaultBlockState();
				break;
			case "acacia_log":
				state = Blocks.ACACIA_PLANKS.defaultBlockState();
				break;
			case "dark_oak_log":
				state = Blocks.DARK_OAK_PLANKS.defaultBlockState();
				break;
		}
		return state;
	}
}
