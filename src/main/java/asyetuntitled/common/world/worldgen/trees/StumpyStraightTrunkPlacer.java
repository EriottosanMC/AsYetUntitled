package asyetuntitled.common.world.worldgen.trees;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import asyetuntitled.common.world.worldgen.FeaturesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class StumpyStraightTrunkPlacer extends StraightTrunkPlacer 
{
	public StumpyStraightTrunkPlacer(int height, int heightRandA, int heightRandB)
	{
		super(height, heightRandA, heightRandB);
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, Random rand, int height, BlockPos pos, TreeConfiguration treeconf) {
	    List<FoliagePlacer.FoliageAttachment> ret = super.placeTrunk(level, consumer, rand, height, pos, treeconf);
	    consumer.accept(pos, FeaturesRegistry.getStumped(treeconf.trunkProvider.getState(rand, pos)));
	    return ret;
	}
}
