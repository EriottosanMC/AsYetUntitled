package asyetuntitled.common.world.worldgen;

import java.util.Optional;
import java.util.Random;

import asyetuntitled.common.block.BlocksRegistry;
import asyetuntitled.common.block.CornerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

public class TouchstoneStructure extends StructureFeature<JigsawConfiguration>
{
    public TouchstoneStructure() {
        super(JigsawConfiguration.CODEC, context -> {
            if (!isFeatureChunk(context)) {
                return Optional.empty();
            } else {
                return createPiecesGenerator(context);
            }
        }, TouchstoneStructure::postPlace);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    // Test if the current chunk (from context) has a valid location for our structure
    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos pos = context.chunkPos().getWorldPosition();

        // Get height of land (stops at first non-air block)
        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

        // Grabs column of blocks at given position. In overworld, this column will be made of stone, water, and air.
        // In nether, it will be netherrack, lava, and air. End will only be endstone and air. It depends on what block
        // the chunk generator will place for that dimension.
        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor());

        // Combine the column of blocks with land height and you get the top block itself which you can test.
        BlockState topBlock = columnOfBlocks.getBlock(landHeight);

        // Now we test to make sure our structure is not spawning on water or other fluids.
        // You can do height check instead too to make it spawn at high elevations.
        return topBlock.getFluidState().isEmpty(); //landHeight > 100;
    }

    private static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        // Turns the chunk coordinates into actual coordinates we can use. (center of that chunk)
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        return JigsawPlacement.addPieces(
                context,
                PoolElementStructurePiece::new,
                blockpos,
                false,
                true);
    }
    
    private static void postPlace(WorldGenLevel level, StructureFeatureManager manager, ChunkGenerator chunkGen, Random rand, BoundingBox box, ChunkPos pos, PiecesContainer cont) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
//        int i = level.getMinBuildHeight();
        BoundingBox boundingbox = cont.calculateBoundingBox();
        int j = boundingbox.minY() + 1;
        
        for(int a = box.minX(); a <= box.maxX(); a++)
        {
            for(int b = box.minZ(); b <= box.maxZ(); b++)
            {
                blockpos$mutableblockpos.set(a, j, b);
                BlockState state = level.getBlockState(blockpos$mutableblockpos);
                BlockState stateNorth = level.getBlockState(blockpos$mutableblockpos.north());
                BlockState stateEast = level.getBlockState(blockpos$mutableblockpos.east());
                if(state.getBlock() == BlocksRegistry.TOUCHSTONE_CORNER.get())
                {
                  Direction direction;
                  if(stateNorth.getBlock() == BlocksRegistry.TOUCHSTONE_BASE.get())
                  {
                      if(stateEast.getBlock() == BlocksRegistry.TOUCHSTONE_BASE.get())
                      {
                          direction = Direction.EAST;
                      }
                      else
                      {
                          direction = Direction.NORTH;
                      }
                  }
                  else
                  {
                      if(stateEast.getBlock() == BlocksRegistry.TOUCHSTONE_BASE.get())
                      {
                          direction = Direction.SOUTH;
                      }
                      else
                      {
                          direction = Direction.WEST;
                      }
                  }
                  level.setBlock(blockpos$mutableblockpos, state.setValue(CornerBlock.FACING, direction), 2);
                }
            }
        }
     }
}
