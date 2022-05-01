package asyetuntitled.common.block;

import asyetuntitled.common.util.CommonResourceLocations;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags 
{

	public static class Blocks
	{
		public static final TagKey<Block> DIGGABLE_BY_HAND = BlockTags.create(CommonResourceLocations.DIGGABLE_BY_HAND);
		
	}
	
	public static class Items
	{
		
	}
	
	public static class Spawnable
	{
	    public static final TagKey<Biome> HAS_TOUCHSTONE = TagKey.create(Registry.BIOME_REGISTRY, CommonResourceLocations.HAS_TOUCHSTONE);

	}
	
	
}
