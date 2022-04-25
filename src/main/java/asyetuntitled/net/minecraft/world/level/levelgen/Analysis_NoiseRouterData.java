package asyetuntitled.net.minecraft.world.level.levelgen;
//package asyetuntitled.net.minecraft.world.level.levelgen;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Optional;
//
//import net.minecraft.core.Holder;
//import net.minecraft.core.Registry;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.biome.OverworldBiomeBuilder;
//import net.minecraft.world.level.biome.TerrainShaper;
//import net.minecraft.world.level.levelgen.synth.BlendedNoise;
//import net.minecraft.world.level.levelgen.synth.NormalNoise;
//import net.minecraft.world.level.levelgen.NoiseRouterData;
//import net.minecraft.world.level.levelgen.NoiseRouterWithOnlyNoises;
//import net.minecraft.world.level.levelgen.NoiseSettings;
//import net.minecraft.world.level.levelgen.Noises;
//import net.minecraft.world.level.levelgen.PositionalRandomFactory;
//import net.minecraft.world.level.levelgen.WorldgenRandom;
//import net.minecraft.world.level.levelgen.DensityFunction;
//import net.minecraft.world.level.levelgen.DensityFunctions;
//import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
//import net.minecraft.world.level.levelgen.NoiseRouter;
//
//public class Analysis_NoiseRouterData
//{
//
//    public static NoiseRouter createNoiseRouter(NoiseSettings p_209503_, long p_209504_, Registry<NormalNoise.NoiseParameters> p_209505_, WorldgenRandom.Algorithm p_209506_, NoiseRouterWithOnlyNoises p_209507_) {
//        boolean flag = p_209506_ == WorldgenRandom.Algorithm.LEGACY;
//        PositionalRandomFactory positionalrandomfactory = p_209506_.newInstance(p_209504_).forkPositional();
//        Map<DensityFunction, DensityFunction> map = new HashMap<>();
//        DensityFunction.Visitor densityfunction$visitor = (p_209535_) -> {
//           if (p_209535_ instanceof DensityFunctions.Noise) {
//              DensityFunctions.Noise densityfunctions$noise = (DensityFunctions.Noise)p_209535_;
//              Holder<NormalNoise.NoiseParameters> holder2 = densityfunctions$noise.noiseData();
//              return new DensityFunctions.Noise(holder2, seedNoise(positionalrandomfactory, p_209505_, holder2), densityfunctions$noise.xzScale(), densityfunctions$noise.yScale());
//           } else if (p_209535_ instanceof DensityFunctions.ShiftNoise) {
//              DensityFunctions.ShiftNoise densityfunctions$shiftnoise = (DensityFunctions.ShiftNoise)p_209535_;
//              Holder<NormalNoise.NoiseParameters> holder3 = densityfunctions$shiftnoise.noiseData();
//              NormalNoise normalnoise1;
//              if (flag) {
//                 normalnoise1 = NormalNoise.create(positionalrandomfactory.fromHashOf(Noises.SHIFT.location()), new NormalNoise.NoiseParameters(0, 0.0D));
//              } else {
//                 normalnoise1 = seedNoise(positionalrandomfactory, p_209505_, holder3);
//              }
//
//              return densityfunctions$shiftnoise.withNewNoise(normalnoise1);
//           } else if (p_209535_ instanceof DensityFunctions.ShiftedNoise) {
//              DensityFunctions.ShiftedNoise densityfunctions$shiftednoise = (DensityFunctions.ShiftedNoise)p_209535_;
//              if (flag) {
//                 Holder<NormalNoise.NoiseParameters> holder = densityfunctions$shiftednoise.noiseData();
//                 if (Objects.equals(holder.unwrapKey(), Optional.of(Noises.TEMPERATURE))) {
//                    NormalNoise normalnoise2 = NormalNoise.createLegacyNetherBiome(p_209506_.newInstance(p_209504_), new NormalNoise.NoiseParameters(-7, 1.0D, 1.0D));
//                    return new DensityFunctions.ShiftedNoise(densityfunctions$shiftednoise.shiftX(), densityfunctions$shiftednoise.shiftY(), densityfunctions$shiftednoise.shiftZ(), densityfunctions$shiftednoise.xzScale(), densityfunctions$shiftednoise.yScale(), holder, normalnoise2);
//                 }
//
//                 if (Objects.equals(holder.unwrapKey(), Optional.of(Noises.VEGETATION))) {
//                    NormalNoise normalnoise = NormalNoise.createLegacyNetherBiome(p_209506_.newInstance(p_209504_ + 1L), new NormalNoise.NoiseParameters(-7, 1.0D, 1.0D));
//                    return new DensityFunctions.ShiftedNoise(densityfunctions$shiftednoise.shiftX(), densityfunctions$shiftednoise.shiftY(), densityfunctions$shiftednoise.shiftZ(), densityfunctions$shiftednoise.xzScale(), densityfunctions$shiftednoise.yScale(), holder, normalnoise);
//                 }
//              }
//
//              Holder<NormalNoise.NoiseParameters> holder1 = densityfunctions$shiftednoise.noiseData();
//              return new DensityFunctions.ShiftedNoise(densityfunctions$shiftednoise.shiftX(), densityfunctions$shiftednoise.shiftY(), densityfunctions$shiftednoise.shiftZ(), densityfunctions$shiftednoise.xzScale(), densityfunctions$shiftednoise.yScale(), holder1, seedNoise(positionalrandomfactory, p_209505_, holder1));
//           } else if (p_209535_ instanceof DensityFunctions.WeirdScaledSampler) {
//              DensityFunctions.WeirdScaledSampler densityfunctions$weirdscaledsampler = (DensityFunctions.WeirdScaledSampler)p_209535_;
//              return new DensityFunctions.WeirdScaledSampler(densityfunctions$weirdscaledsampler.input(), densityfunctions$weirdscaledsampler.noiseData(), seedNoise(positionalrandomfactory, p_209505_, densityfunctions$weirdscaledsampler.noiseData()), densityfunctions$weirdscaledsampler.rarityValueMapper());
//           } else if (p_209535_ instanceof BlendedNoise) {
//              return flag ? new BlendedNoise(p_209506_.newInstance(p_209504_), p_209503_.noiseSamplingSettings(), p_209503_.getCellWidth(), p_209503_.getCellHeight()) : new BlendedNoise(positionalrandomfactory.fromHashOf(new ResourceLocation("terrain")), p_209503_.noiseSamplingSettings(), p_209503_.getCellWidth(), p_209503_.getCellHeight());
//           } else if (p_209535_ instanceof DensityFunctions.EndIslandDensityFunction) {
//              return new DensityFunctions.EndIslandDensityFunction(p_209504_);
//           } else if (p_209535_ instanceof DensityFunctions.TerrainShaperSpline) {
//              DensityFunctions.TerrainShaperSpline densityfunctions$terrainshaperspline = (DensityFunctions.TerrainShaperSpline)p_209535_;
//              TerrainShaper terrainshaper = p_209503_.terrainShaper();
//              return new DensityFunctions.TerrainShaperSpline(densityfunctions$terrainshaperspline.continentalness(), densityfunctions$terrainshaperspline.erosion(), densityfunctions$terrainshaperspline.weirdness(), terrainshaper, densityfunctions$terrainshaperspline.spline(), densityfunctions$terrainshaperspline.minValue(), densityfunctions$terrainshaperspline.maxValue());
//           } else if (p_209535_ instanceof DensityFunctions.Slide) {
//              DensityFunctions.Slide densityfunctions$slide = (DensityFunctions.Slide)p_209535_;
//              return new DensityFunctions.Slide(p_209503_, densityfunctions$slide.input());
//           } else {
//              return p_209535_;
//           }
//        };
//        DensityFunction.Visitor densityfunction$visitor1 = (p_209541_) -> {
//           return map.computeIfAbsent(p_209541_, densityfunction$visitor);
//        };
//        NoiseRouterWithOnlyNoises noiserouterwithonlynoises = p_209507_.mapAll(densityfunction$visitor1);
//        PositionalRandomFactory positionalrandomfactory1 = positionalrandomfactory.fromHashOf(new ResourceLocation("aquifer")).forkPositional();
//        PositionalRandomFactory positionalrandomfactory2 = positionalrandomfactory.fromHashOf(new ResourceLocation("ore")).forkPositional();
//        return new NoiseRouter(noiserouterwithonlynoises.barrierNoise(), noiserouterwithonlynoises.fluidLevelFloodednessNoise(), noiserouterwithonlynoises.fluidLevelSpreadNoise(), noiserouterwithonlynoises.lavaNoise(), positionalrandomfactory1, positionalrandomfactory2, noiserouterwithonlynoises.temperature(), noiserouterwithonlynoises.vegetation(), noiserouterwithonlynoises.continents(), noiserouterwithonlynoises.erosion(), noiserouterwithonlynoises.depth(), noiserouterwithonlynoises.ridges(), noiserouterwithonlynoises.initialDensityWithoutJaggedness(), noiserouterwithonlynoises.finalDensity(), noiserouterwithonlynoises.veinToggle(), noiserouterwithonlynoises.veinRidged(), noiserouterwithonlynoises.veinGap(), (new OverworldBiomeBuilder()).spawnTarget());
//     }
//    
//    private static NormalNoise seedNoise(PositionalRandomFactory p_209525_, Registry<NormalNoise.NoiseParameters> p_209526_, Holder<NormalNoise.NoiseParameters> p_209527_) {
//        return Noises.instantiate(p_209525_, p_209527_.unwrapKey().flatMap(p_209526_::getHolder).orElse(p_209527_));
//     }
//
//}
