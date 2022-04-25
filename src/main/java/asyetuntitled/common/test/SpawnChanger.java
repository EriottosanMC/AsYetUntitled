package asyetuntitled.common.test;
//package asyetuntitled.common.test;
//
//
//import java.util.Objects;
//import java.util.Optional;
//import java.util.Random;
//
//import javax.annotation.Nullable;
//
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import asyetuntitled.AsYetUntitled;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.BlockPos.MutableBlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.core.Holder;
//import net.minecraft.core.Registry;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.tags.FluidTags;
//import net.minecraft.util.Mth;
//import net.minecraft.util.random.WeightedRandomList;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.MobCategory;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.SpawnGroupData;
//import net.minecraft.world.entity.SpawnPlacements;
//import net.minecraft.world.entity.SpawnPlacements.Type;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.ChunkPos;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.LevelReader;
//import net.minecraft.world.level.NaturalSpawner.AfterSpawnCallback;
//import net.minecraft.world.level.NaturalSpawner.SpawnPredicate;
//import net.minecraft.world.level.ServerLevelAccessor;
//import net.minecraft.world.level.StructureFeatureManager;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.MobSpawnSettings;
//import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.SlabBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.properties.SlabType;
//import net.minecraft.world.level.chunk.ChunkAccess;
//import net.minecraft.world.level.chunk.ChunkGenerator;
//import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
//import net.minecraft.world.level.levelgen.feature.NetherFortressFeature;
//import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
//import net.minecraft.world.level.material.FluidState;
//import net.minecraft.world.level.pathfinder.PathComputationType;
//import net.minecraft.world.phys.Vec3;
//
//public class SpawnChanger {
//
//	public static BlockPos getSpawnPos(ServerLevel server, double d0, double d1, double d2) 
//	{
//		BlockPos pos = new BlockPos(d0, d1, d2);
//		return pos;
//	}
//
//	public static boolean isValidEmptySpawnBlock(BlockGetter p_47057_, BlockPos p_47058_, BlockState p_47059_,
//			FluidState p_47060_, EntityType<?> p_47061_) {
//
//		if (p_47059_.isCollisionShapeFullBlock(p_47057_, p_47058_)) {
//			return false;
//	      } else if (p_47059_.isSignalSource()) {
//	    	  return false;
//	      } else if (!p_47060_.isEmpty()) {
//	         return false;
//	      } else if (p_47059_.is(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)) {
//	         return false;
//	      } else {
//	         return !p_47061_.isBlockDangerous(p_47059_);
//	      }
//	}
//
//	public static boolean canSpawnAtBody(Type p_47052_, LevelReader p_47053_, BlockPos p_47054_,
//			EntityType<?> p_47055_) {
//		BlockState blockstate = p_47053_.getBlockState(p_47054_);
//		FluidState fluidstate = p_47053_.getFluidState(p_47054_);
//		BlockPos blockpos = p_47054_.above();
//		BlockPos blockpos1 = p_47054_.below();
//		switch(p_47052_) {
//		case IN_WATER:
//			return fluidstate.is(FluidTags.WATER) && !p_47053_.getBlockState(blockpos).isRedstoneConductor(p_47053_, blockpos);
//		case IN_LAVA:
//			return fluidstate.is(FluidTags.LAVA);
//		case ON_GROUND:
//		default:
////			System.out.println(p_47055_ + ";" + (isValidEmptySpawnBlock(p_47053_, p_47054_, blockstate, fluidstate, p_47055_) && isValidEmptySpawnBlock(p_47053_, blockpos, p_47053_.getBlockState(blockpos), p_47053_.getFluidState(blockpos), p_47055_)));
//			BlockState blockstate1 = p_47053_.getBlockState(blockpos1);
//			if (!blockstate1.isValidSpawn(p_47053_, blockpos1, p_47052_, p_47055_)) {
//				return false;
//			} else {
//				return isValidEmptySpawnBlock(p_47053_, p_47054_, blockstate, fluidstate, p_47055_) && isValidEmptySpawnBlock(p_47053_, blockpos, p_47053_.getBlockState(blockpos), p_47053_.getFluidState(blockpos), p_47055_);
//			}
//		}
//	}
//
//	public static BlockPos getTopNonCollidingPos(LevelReader level, EntityType<?> type, int x, int z) {
//		int i = level.getHeight(SpawnPlacements.getHeightmapType(type), x, z);
//		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, i, z);
//		if (level.dimensionType().hasCeiling()) {
//			do {
//				blockpos$mutableblockpos.move(Direction.DOWN);
//			} while(!level.getBlockState(blockpos$mutableblockpos).isAir());
//			
//			do {
//				blockpos$mutableblockpos.move(Direction.DOWN);
//			} while(level.getBlockState(blockpos$mutableblockpos).isAir() && blockpos$mutableblockpos.getY() > level.getMinBuildHeight());
//		}
//		if (SpawnPlacements.getPlacementType(type) == SpawnPlacements.Type.ON_GROUND) {
//			BlockPos blockpos = blockpos$mutableblockpos.below();
//			if (level.getBlockState(blockpos).isPathfindable(level, blockpos, PathComputationType.LAND)) {
//				return blockpos;
//			}
//		}
//		
//		return blockpos$mutableblockpos.immutable();
//	}
//
//	public static void spawnMobsForChunkGeneration(ServerLevelAccessor p_204176_, Holder<Biome> p_204177_,
//			ChunkPos p_204178_, Random p_204179_) {
//		MobSpawnSettings mobspawnsettings = p_204177_.value().getMobSettings();
//		
//	      WeightedRandomList<MobSpawnSettings.SpawnerData> weightedrandomlist = mobspawnsettings.getMobs(MobCategory.CREATURE);
//	      if (!weightedrandomlist.isEmpty()) {
//	         int i = p_204178_.getMinBlockX();
//	         int j = p_204178_.getMinBlockZ();
//
//	         while(p_204179_.nextFloat() < mobspawnsettings.getCreatureProbability()) {
//	            Optional<MobSpawnSettings.SpawnerData> optional = weightedrandomlist.getRandom(p_204179_);
//	            if (optional.isPresent()) {
//	               MobSpawnSettings.SpawnerData mobspawnsettings$spawnerdata = optional.get();
//
//	               int k = mobspawnsettings$spawnerdata.minCount + p_204179_.nextInt(1 + mobspawnsettings$spawnerdata.maxCount - mobspawnsettings$spawnerdata.minCount);
//	               SpawnGroupData spawngroupdata = null;
//	               int l = i + p_204179_.nextInt(16);
//	               int i1 = j + p_204179_.nextInt(16);
//	               int j1 = l;
//	               int k1 = i1;
//
//	               for(int l1 = 0; l1 < k; ++l1) {
//	                  boolean flag = false;
//
//	                  for(int i2 = 0; !flag && i2 < 4; ++i2) {
//	                     BlockPos blockpos = getTopNonCollidingPos(p_204176_, mobspawnsettings$spawnerdata.type, l, i1);
//	                     if (mobspawnsettings$spawnerdata.type.canSummon() && isSpawnPositionOk(SpawnPlacements.getPlacementType(mobspawnsettings$spawnerdata.type), p_204176_, blockpos, mobspawnsettings$spawnerdata.type)) {
//	                        float f = mobspawnsettings$spawnerdata.type.getWidth();
//	                        double d0 = Mth.clamp((double)l, (double)i + (double)f, (double)i + 16.0D - (double)f);
//	                        double d1 = Mth.clamp((double)i1, (double)j + (double)f, (double)j + 16.0D - (double)f);
//	                        if (!p_204176_.noCollision(mobspawnsettings$spawnerdata.type.getAABB(d0, (double)blockpos.getY(), d1)) || !SpawnPlacements.checkSpawnRules(mobspawnsettings$spawnerdata.type, p_204176_, MobSpawnType.CHUNK_GENERATION, new BlockPos(d0, (double)blockpos.getY(), d1), p_204176_.getRandom())) {
//	                           continue;
//	                        }
//
//	                        Entity entity;
//	                        try {
//	                           entity = mobspawnsettings$spawnerdata.type.create(p_204176_.getLevel());
//	                        } catch (Exception exception) {
//	                           AsYetUntitled.LOGGER.warn("Failed to create mob", (Throwable)exception);
//	                           continue;
//	                        }
//
//	                        entity.moveTo(d0, (double)blockpos.getY(), d1, p_204179_.nextFloat() * 360.0F, 0.0F);
//	                        if (entity instanceof Mob) {
//	                           Mob mob = (Mob)entity;
//	                           if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, p_204176_, d0, blockpos.getY(), d1, null, MobSpawnType.CHUNK_GENERATION) == -1) continue;
//	                           if (mob.checkSpawnRules(p_204176_, MobSpawnType.CHUNK_GENERATION) && mob.checkSpawnObstruction(p_204176_)) {
//	                              spawngroupdata = mob.finalizeSpawn(p_204176_, p_204176_.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.CHUNK_GENERATION, spawngroupdata, (CompoundTag)null);
//	                              p_204176_.addFreshEntityWithPassengers(mob);
//	                              flag = true;
//	                           }
//	                        }
//	                     }
//
//	                     l += p_204179_.nextInt(5) - p_204179_.nextInt(5);
//
//	                     for(i1 += p_204179_.nextInt(5) - p_204179_.nextInt(5); l < i || l >= i + 16 || i1 < j || i1 >= j + 16; i1 = k1 + p_204179_.nextInt(5) - p_204179_.nextInt(5)) {
//	                        l = j1 + p_204179_.nextInt(5) - p_204179_.nextInt(5);
//	                     }
//	                  }
//	               }
//	            }
//	         }
//
//	      }		
//	}
//	
//	
//	   public static boolean isSpawnPositionOk(SpawnPlacements.Type p_47052_, LevelReader p_47053_, BlockPos p_47054_, @Nullable EntityType<?> p_47055_) {
//		      if (p_47052_ == SpawnPlacements.Type.NO_RESTRICTIONS) {
//		         return true;
//		      } else if (p_47055_ != null && p_47053_.getWorldBorder().isWithinBounds(p_47054_)) {
//		         return p_47052_.canSpawnAt(p_47053_, p_47054_, p_47055_);
//		      }
//		      return false;
//		   }
//
//	public static boolean isValidPositionForMob(ServerLevel level, Mob mob, double d) {
//
//		if (d > (double)(mob.getType().getCategory().getDespawnDistance() * mob.getType().getCategory().getDespawnDistance()) && mob.removeWhenFarAway(d)) {
//			return false;
//		} else {
//			return mob.checkSpawnRules(level, MobSpawnType.NATURAL) && mob.checkSpawnObstruction(level);
//		}
//	}
//
//	public static boolean isValidSpawnPositionForType(ServerLevel level, MobCategory mobCat,
//			StructureFeatureManager structure, ChunkGenerator chunkGen, SpawnerData spawnerData,
//			MutableBlockPos blockPos, double d) {
//		EntityType<?> entitytype = spawnerData.type;
//		if (entitytype.getCategory() == MobCategory.MISC) {
//			return false;
//		} else if (!entitytype.canSpawnFarFromPlayer() && d > (double)(entitytype.getCategory().getDespawnDistance() * entitytype.getCategory().getDespawnDistance())) {
//			return false;
//		} else if (entitytype.canSummon() && canSpawnMobAt(level, structure, chunkGen, mobCat, spawnerData, blockPos)) {
//			SpawnPlacements.Type spawnplacements$type = SpawnPlacements.getPlacementType(entitytype);
//			if (!isSpawnPositionOk(spawnplacements$type, level, blockPos, entitytype)) {
//				return false;
//			} else if (!SpawnPlacements.checkSpawnRules(entitytype, level, MobSpawnType.NATURAL, blockPos, level.random)) {
//				return false;
//			} else {
//				return level.noCollision(entitytype.getAABB((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D));
//			}
//		} else {
//			return false;
//		}
//	}
//	
//	   private static boolean isValidSpawnPostitionForType(ServerLevel level, MobCategory p_46997_, StructureFeatureManager p_46998_, ChunkGenerator p_46999_, MobSpawnSettings.SpawnerData p_47000_, BlockPos.MutableBlockPos pos, double p_47002_) {
//		      EntityType<?> entitytype = p_47000_.type;
//		      if (entitytype.getCategory() == MobCategory.MISC) {
//		         return false;
//		      } else if (!entitytype.canSpawnFarFromPlayer() && p_47002_ > (double)(entitytype.getCategory().getDespawnDistance() * entitytype.getCategory().getDespawnDistance())) {
//		         return false;
//		      } else if (entitytype.canSummon() && canSpawnMobAt(level, p_46998_, p_46999_, p_46997_, p_47000_, pos)) {
//		         SpawnPlacements.Type spawnplacements$type = SpawnPlacements.getPlacementType(entitytype);
//		         if (!isSpawnPositionOk(spawnplacements$type, level, pos, entitytype)) {
//		            return false;
//		         } else if (!SpawnPlacements.checkSpawnRules(entitytype, level, MobSpawnType.NATURAL, pos, level.random)) {
//		            return false;
//		         } else {
//		        	 double offset = 0.0D;
//		        	 if(level.getBlockState(pos).getBlock() instanceof SlabBlock && level.getBlockState(pos).getValue(SlabBlock.TYPE) == SlabType.BOTTOM)
//		        	 {
//		        		 offset = -0.05D;
//		        	 }
//		        	 return level.noCollision(entitytype.getAABB((double)pos.getX() + 0.5D, (double)pos.getY() + offset, (double)pos.getZ() + 0.5D));
//		         }
//		      } else {
//		         return false;
//		      }
//		   }
//	
//	  private static boolean canSpawnMobAt(ServerLevel p_47004_, StructureFeatureManager p_47005_, ChunkGenerator p_47006_, MobCategory p_47007_, MobSpawnSettings.SpawnerData p_47008_, BlockPos p_47009_) {
//	      return mobsAt(p_47004_, p_47005_, p_47006_, p_47007_, p_47009_, (Holder<Biome>)null).unwrap().contains(p_47008_);
//	   }
//	  
//	   private static WeightedRandomList<MobSpawnSettings.SpawnerData> mobsAt(ServerLevel p_204169_, StructureFeatureManager p_204170_, ChunkGenerator p_204171_, MobCategory p_204172_, BlockPos p_204173_, @Nullable Holder<Biome> p_204174_) {
//		      return isInNetherFortressBounds(p_204173_, p_204169_, p_204172_, p_204170_) ? NetherFortressFeature.FORTRESS_ENEMIES : p_204171_.getMobsAt(p_204174_ != null ? p_204174_ : p_204169_.getBiome(p_204173_), p_204170_, p_204172_, p_204173_);
//		   }
//	   
//	   public static boolean isInNetherFortressBounds(BlockPos p_186530_, ServerLevel p_186531_, MobCategory p_186532_, StructureFeatureManager p_186533_) {
//		      if (p_186532_ == MobCategory.MONSTER && p_186531_.getBlockState(p_186530_.below()).is(Blocks.NETHER_BRICKS)) {
//		         ConfiguredStructureFeature<?, ?> configuredstructurefeature = p_186533_.registryAccess().registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY).get(BuiltinStructures.FORTRESS);
//		         return configuredstructurefeature == null ? false : p_186533_.getStructureAt(p_186530_, configuredstructurefeature).isValid();
//		      } else {
//		         return false;
//		      }
//		   }
//
//	   
//	   private static boolean isRightDistanceToPlayerAndSpawnPoint(ServerLevel p_47025_, ChunkAccess p_47026_, BlockPos.MutableBlockPos p_47027_, double p_47028_) {
//		      if (p_47028_ <= 576.0D) {
//		         return false;
//		      } else if (p_47025_.getSharedSpawnPos().closerToCenterThan(new Vec3((double)p_47027_.getX() + 0.5D, (double)p_47027_.getY(), (double)p_47027_.getZ() + 0.5D), 24.0D)) {
//		         return false;
//		      } else {
//		         return Objects.equals(new ChunkPos(p_47027_), p_47026_.getPos()) || p_47025_.isNaturalSpawningAllowed(p_47027_);
//		      }
//		   }
//	   
//	public static void doStuff(MobCategory mobCat, ServerLevel level, ChunkAccess chunk, BlockPos pos,
//			SpawnPredicate spawnpred, AfterSpawnCallback ascb, CallbackInfo cb) {
//		StructureFeatureManager structurefeaturemanager = level.structureFeatureManager();
//		if(false)
//		{
//			cb.cancel();
//		}
//		else
//		{
//			return;
//		}
//	      ChunkGenerator chunkgenerator = level.getChunkSource().getGenerator();
//	      double i = pos.getY();
//	      double offset = 0.0D;
//	      BlockState blockstate = chunk.getBlockState(pos);
//	      if(blockstate.getBlock() instanceof SlabBlock && blockstate.getValue(SlabBlock.TYPE) == SlabType.BOTTOM)
//	      {
//	    	  offset = -0.5D;
//	      }
//	      i+=offset;
//	      if (!blockstate.isRedstoneConductor(chunk, pos)) {
//	         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
//	         int j = 0;
//
//	         for(int k = 0; k < 3; ++k) {
//	            int l = pos.getX();
//	            int i1 = pos.getZ();
//	            int j1 = 6;
//	            MobSpawnSettings.SpawnerData mobspawnsettings$spawnerdata = null;
//	            SpawnGroupData spawngroupdata = null;
//	            int k1 = Mth.ceil(level.random.nextFloat() * 4.0F);
//	            int l1 = 0;
//
//	            for(int i2 = 0; i2 < k1; ++i2) {
//	               l += level.random.nextInt(6) - level.random.nextInt(6);
//	               i1 += level.random.nextInt(6) - level.random.nextInt(6);
//	               blockpos$mutableblockpos.set(l, i, i1);
//	               double d0 = (double)l + 0.5D;
//	               double d1 = (double)i1 + 0.5D;
//	               Player player = level.getNearestPlayer(d0, (double)i, d1, -1.0D, false);
//	               if (player != null) {
//	                  double d2 = player.distanceToSqr(d0, (double)i, d1);
//	                  if (isRightDistanceToPlayerAndSpawnPoint(level, chunk, blockpos$mutableblockpos, d2)) {
//	                     if (mobspawnsettings$spawnerdata == null) {
//	                        Optional<MobSpawnSettings.SpawnerData> optional = getRandomSpawnMobAt(level, structurefeaturemanager, chunkgenerator, mobCat, level.random, blockpos$mutableblockpos);
//	                        if (optional.isEmpty()) {
//	                           break;
//	                        }
//
//	                        mobspawnsettings$spawnerdata = optional.get();
//	                        k1 = mobspawnsettings$spawnerdata.minCount + level.random.nextInt(1 + mobspawnsettings$spawnerdata.maxCount - mobspawnsettings$spawnerdata.minCount);
//	                     }
//
//	                     if (isValidSpawnPostitionForType(level, mobCat, structurefeaturemanager, chunkgenerator, mobspawnsettings$spawnerdata, blockpos$mutableblockpos, d2) && spawnpred.test(mobspawnsettings$spawnerdata.type, blockpos$mutableblockpos, chunk)) {
//	                        Mob mob = getMobForSpawn(level, mobspawnsettings$spawnerdata.type);
//	                        if (mob == null) {
//	                           return;
//	                        }
//
//	                        mob.moveTo(d0, i, d1, level.random.nextFloat() * 360.0F, 0.0F);
//	                        int canSpawn = net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, d0, i, d1, null, MobSpawnType.NATURAL);
//	                        if (canSpawn != -1 && (canSpawn == 1 || isValidPositionForMob(level, mob, d2))) {
//	                           if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(mob, (LevelAccessor) level, (float)d0, (float)i, (float)d1, null, MobSpawnType.NATURAL))
//	                           spawngroupdata = mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.NATURAL, spawngroupdata, (CompoundTag)null);
//	                           ++j;
//	                           ++l1;
//	                           level.addFreshEntityWithPassengers(mob);
//	                           ascb.run(mob, chunk);
//	                           if (j >= net.minecraftforge.event.ForgeEventFactory.getMaxSpawnPackSize(mob)) {
//	                              return;
//	                           }
//
//	                           if (mob.isMaxGroupSizeReached(l1)) {
//	                              break;
//	                           }
//	                        }
//	                     }
//	                  }
//	               }
//	            }
//	         }
//
//	      }
//		
////		
////		ChunkGenerator chunkgenerator = level.getChunkSource().getGenerator();
////	      double i = (int) pos.getY();
////	      BlockState blockstate = chunk.getBlockState(pos);
////	      Material mat = blockstate.getMaterial();
////	      
//////	      if(mat == Material.BAMBOO || mat == Material.WOOD || mat == Material.WOOL)
//////	      {
//////	    	  return;
//////	      }
//////	      else if(!(level.getBlockState(pos.above()).isAir())) return;
//////	      System.out.println("has");
////	      double offset = 0.5D;
////	      if(blockstate.getBlock() instanceof SlabBlock && blockstate.getValue(SlabBlock.TYPE) == SlabType.BOTTOM)
////	      {   
////	    	  offset = 0.5D;
////	      }
////	      i += offset;
////	      if (!blockstate.isRedstoneConductor(chunk, pos)) {
////	         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
////	         int j = 0;
////
////	         for(int k = 0; k < 3; ++k) {
////	            int l = pos.getX();
////	            int i1 = pos.getZ();
////	            int j1 = 6;
////	            MobSpawnSettings.SpawnerData mobspawnsettings$spawnerdata = null;
////	            SpawnGroupData spawngroupdata = null;
////	            int k1 = Mth.ceil(level.random.nextFloat() * 4.0F);
////	            int l1 = 0;
////
////	            for(int i2 = 0; i2 < k1; ++i2) {
////	               l += level.random.nextInt(6) - level.random.nextInt(6);
////	               i1 += level.random.nextInt(6) - level.random.nextInt(6);
////	               blockpos$mutableblockpos.set(l, i, i1);
////	               double d0 = (double)l + 0.5D;
////	               double d1 = (double)i1 + 0.5D;
////	               Player player = level.getNearestPlayer(d0, i, d1, -1.0D, false);
////	               if (player != null) {
////	                  double d2 = player.distanceToSqr(d0, i, d1);
////	                  if (isRightDistanceToPlayerAndSpawnPoint(level, chunk, blockpos$mutableblockpos, d2)) {
////	                     if (mobspawnsettings$spawnerdata == null) {
////	                        Optional<MobSpawnSettings.SpawnerData> optional = getRandomSpawnMobAt(level, structurefeaturemanager, chunkgenerator, mobCat, level.random, blockpos$mutableblockpos);
////	                        if (optional.isEmpty()) {
////	                           break;
////	                        }
////
////	                        mobspawnsettings$spawnerdata = optional.get();
////	                        k1 = mobspawnsettings$spawnerdata.minCount + level.random.nextInt(1 + mobspawnsettings$spawnerdata.maxCount - mobspawnsettings$spawnerdata.minCount);
////	                     }
////
////	                     if (isValidSpawnPositionForType(level, mobCat, structurefeaturemanager, chunkgenerator, mobspawnsettings$spawnerdata,  new BlockPos.MutableBlockPos().set(l, i-offset, i1), d2) && spawnpred.test(mobspawnsettings$spawnerdata.type, blockpos$mutableblockpos, chunk)) {
////	                        Mob mob = getMobForSpawn(level, mobspawnsettings$spawnerdata.type);
////	                        if (mob == null) {
////	                           return;
////	                        }
////	                        
////	                        mob.moveTo(d0, i, d1, level.random.nextFloat() * 360.0F, 0.0F);
////	                        int canSpawn = net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, d0, i, d1, null, MobSpawnType.NATURAL);
////	                        if (canSpawn != -1 && (canSpawn == 1 || isValidPositionForMob(level, mob, d2))) {
////	                           if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(mob, (LevelAccessor) level, (float)d0, (float)i, (float)d1, null, MobSpawnType.NATURAL))
////	                           spawngroupdata = mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.NATURAL, spawngroupdata, (CompoundTag)null);
////	                           ++j;
////	                           ++l1;
////	                           level.addFreshEntityWithPassengers(mob);
////	                           ascb.run(mob, chunk);
////	                           if (j >= net.minecraftforge.event.ForgeEventFactory.getMaxSpawnPackSize(mob)) {
////	                              return;
////	                           }
////
////	                           if (mob.isMaxGroupSizeReached(l1)) {
////	                              break;
////	                           }
////	                        }
////	                     }
////	                  }
////	               }
////	            }
////	         }
////
////	      }
//	}
//	 @Nullable
//	   private static Mob getMobForSpawn(ServerLevel p_46989_, EntityType<?> p_46990_) {
//	      try {
//	         Entity entity = p_46990_.create(p_46989_);
//	         if (!(entity instanceof Mob)) {
//	            throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.ENTITY_TYPE.getKey(p_46990_));
//	         } else {
//	            return (Mob)entity;
//	         }
//	      } catch (Exception exception) {
//	         AsYetUntitled.LOGGER.warn("Failed to create mob", (Throwable)exception);
//	         return null;
//	      }
//	   }
//	   private static Optional<MobSpawnSettings.SpawnerData> getRandomSpawnMobAt(ServerLevel p_151599_, StructureFeatureManager p_151600_, ChunkGenerator p_151601_, MobCategory p_151602_, Random p_151603_, BlockPos p_151604_) {
//		      Holder<Biome> holder = p_151599_.getBiome(p_151604_);
//		      return p_151602_ == MobCategory.WATER_AMBIENT && Biome.getBiomeCategory(holder) == Biome.BiomeCategory.RIVER && p_151603_.nextFloat() < 0.98F ? Optional.empty() : mobsAt(p_151599_, p_151600_, p_151601_, p_151602_, p_151604_, holder).getRandom(p_151603_);
//		   }
//
//}
