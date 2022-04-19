package asyetuntitled.common.world.worldgen;

import java.util.function.UnaryOperator;

import asyetuntitled.AsYetUntitled;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ChangedFeature implements UnaryOperator<Holder<PlacedFeature>>
{
	@Override
	public Holder<PlacedFeature> apply(Holder<PlacedFeature> original)
	{
		Holder<PlacedFeature> ret = original;
		String name = original.toString().substring(68, original.toString().indexOf("]"));
		if(name.startsWith("patch_")) name = name.substring(6);
		switch(name)
		{
			//GRASS
			case "grass_badlands":
				ret = FeaturesRegistry.HELPFUL_GRASS_BADLANDS;
				break;
			case "grass_forest":
				ret = FeaturesRegistry.HELPFUL_GRASS_FOREST;
				break;
			case "grass_jungle":
				ret = FeaturesRegistry.HELPFUL_GRASS_JUNGLE;
				break;
			case "grass_normal":
				ret = FeaturesRegistry.HELPFUL_GRASS_NORMAL;
				break;
			case "grass_plain":
				ret = FeaturesRegistry.HELPFUL_GRASS_PLAIN;
				break;
			case "grass_savanna":
				ret = FeaturesRegistry.HELPFUL_GRASS_SAVANNA;
				break;
			case "grass_taiga":
				ret = FeaturesRegistry.HELPFUL_GRASS_TAIGA;
				break;
			case "grass_taiga_2":
				ret = FeaturesRegistry.HELPFUL_GRASS_TAIGA_2;
				break;
			case "tall_grass":
				ret = FeaturesRegistry.HELPFUL_TALL_GRASS;
				break;
			case "tall_grass_2":
				ret = FeaturesRegistry.HELPFUL_TALL_GRASS_2;
				break;
			//TREES
			case "birch_tall":
				ret = FeaturesRegistry.STUMPY_BIRCH_TALL;
				break;
			case "dark_forest_vegetation":
				ret = FeaturesRegistry.STUMPY_DARK_FOREST_VEGETATION;
				break;
			case "trees_badlands":
				ret = FeaturesRegistry.STUMPY_TREES_BADLANDS;
				break;
			case "trees_birch":
				ret = FeaturesRegistry.STUMPY_TREES_BIRCH;
				break;
			case "trees_birch_and_oak":
				ret = FeaturesRegistry.STUMPY_TREES_BIRCH_AND_OAK;
				break;
			case "trees_flower_forest":
				ret = FeaturesRegistry.STUMPY_TREES_FLOWER_FOREST;
				break;
			case "trees_grove":
				ret = FeaturesRegistry.STUMPY_TREES_GROVE;
				break;
			case "trees_jungle":
				ret = FeaturesRegistry.STUMPY_TREES_JUNGLE;
				break;
			case "trees_meadow":
				ret = FeaturesRegistry.STUMPY_TREES_MEADOW;
				break;
			case "trees_old_growth_pine_taiga":
				ret = FeaturesRegistry.STUMPY_TREES_OLD_GROWTH_PINE_TAIGA;
				break;
			case "trees_old_growth_spruce_taiga":
				ret = FeaturesRegistry.STUMPY_TREES_OLD_GROWTH_SPRUCE_TAIGA;
				break;
			case "trees_plains":
				ret = FeaturesRegistry.STUMPY_TREES_PLAINS;
				break;
			case "trees_savanna":
				ret = FeaturesRegistry.STUMPY_TREES_SAVANNA;
				break;
			case "trees_sparse_jungle":
				ret = FeaturesRegistry.STUMPY_TREES_SPARSE_JUNGLE;
				break;
			case "trees_snowy":
				ret = FeaturesRegistry.STUMPY_TREES_SNOWY;
				break;
			case "trees_swamp":
				ret = FeaturesRegistry.STUMPY_TREES_SWAMP;
				break;
			case "trees_taiga":
				ret = FeaturesRegistry.STUMPY_TREES_TAIGA;
				break;
			case "trees_water":
				ret = FeaturesRegistry.STUMPY_TREES_WATER;
				break;
			case "trees_windswept_forest":
				ret = FeaturesRegistry.STUMPY_TREES_WINDSWEPT_FOREST;
				break;
			case "trees_windswept_hills":
				ret = FeaturesRegistry.STUMPY_TREES_WINDSWEPT_HILLS;
				break;
			case "trees_windswept_savanna":
				ret = FeaturesRegistry.STUMPY_TREES_WINDSWEPT_SAVANNA;
				break;
		}
		String s = ret == original ? String.format("Saw %s but left it alone.", name) : String.format("Replaced %s with %s", name, ret.toString().substring(68, ret.toString().indexOf("]")));
		AsYetUntitled.LOGGER.info(s);
		return ret;
	}
}