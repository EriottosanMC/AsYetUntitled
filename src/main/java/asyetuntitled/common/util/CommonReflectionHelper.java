package asyetuntitled.common.util;

import java.lang.reflect.Field;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class CommonReflectionHelper 
{
	private static Field blockEngineField;
	private static Field skyEngineField;
	
	private static Field blockSpeedFactorField;
//	private static Field blockPropertiesSpeedFactorField
	
	private static Field blockPropertiesField;
	private static Field blockIsValidSpawnBehaviourField;
	private static Field blockStateDestroySpeedField;
	
	private static Field entityBlockPosField;
	
	public static void init()
	{
		blockEngineField = ObfuscationReflectionHelper.findField(LevelLightEngine.class, "f_75802_");
		skyEngineField = ObfuscationReflectionHelper.findField(LevelLightEngine.class, "f_75803_");
		blockSpeedFactorField = ObfuscationReflectionHelper.findField(BlockBehaviour.class, "f_60448_");
		blockIsValidSpawnBehaviourField = ObfuscationReflectionHelper.findField(BlockBehaviour.Properties.class, "f_60897_");
		blockPropertiesField = ObfuscationReflectionHelper.findField(BlockBehaviour.class, "f_60439_");
		blockStateDestroySpeedField = ObfuscationReflectionHelper.findField(BlockBehaviour.BlockStateBase.class, "f_60599_");
		entityBlockPosField = ObfuscationReflectionHelper.findField(Entity.class, "f_19825_");
	}
	
	public static LayerLightEngine<?, ?> getBlockEngine(LevelLightEngine engine)
	{
		try {
			return (LayerLightEngine<?, ?>) blockEngineField.get(engine);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static LayerLightEngine<?, ?> getSkyEngine(LevelLightEngine engine)
	{
		try {
			return (LayerLightEngine<?, ?>) skyEngineField.get(engine);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setSpeedFactor(BlockBehaviour block, float speedFactor)
	{
		try {
			blockSpeedFactorField.setFloat(block, speedFactor);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void setBlockValidSpawnBehaviour(BlockBehaviour.Properties blockProp, BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn)
	{
		try {
			blockIsValidSpawnBehaviourField.set(blockProp, isValidSpawn);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static BlockBehaviour.Properties getBlockProperties(Block block)
	{
		try {
			return (Properties) blockPropertiesField.get(block);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setExactEntityPos(Entity entity, Vec3 pos)
	{
		try {
			entityBlockPosField.set(entity, pos);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void setBlockStateDestroySpeed(BlockStateBase state, float speed)
	{
		try {
			blockStateDestroySpeedField.set(state, speed);
		} catch (IllegalAccessException | IllegalArgumentException e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean notSet() 
	{
		return entityBlockPosField == null || blockStateDestroySpeedField == null || blockEngineField == null || skyEngineField == null || blockSpeedFactorField == null || blockIsValidSpawnBehaviourField == null || blockIsValidSpawnBehaviourField == null;
	}
}
