package asyetuntitled.common.test;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.minecraft.world.level.levelgen.WorldgenRandom.Algorithm;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class ExtendedSurfaceSystem extends SurfaceSystem {

	public ExtendedSurfaceSystem(Registry<NoiseParameters> noise, BlockState defaultBlock, int seaLevel,
			long seed, Algorithm randomSource) {
		super(noise, defaultBlock, seaLevel, seed, randomSource);
	}

}
