package asyetuntitled.common.test;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class HelpfulGrassFeature extends Feature<FeatureConfiguration>
{
	public HelpfulGrassFeature(Codec<FeatureConfiguration> codec)
	{
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<FeatureConfiguration> p_159749_) {
		// TODO Auto-generated method stub
		return false;
	}
}
