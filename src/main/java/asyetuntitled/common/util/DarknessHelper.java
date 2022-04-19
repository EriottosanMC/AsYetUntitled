package asyetuntitled.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class DarknessHelper 
{
	public static boolean isTrueDarkness(Level level, BlockPos pos)
	{
		if(!(level.dimension() == Level.OVERWORLD))
		{
			return false;
		}
		float time = (float) (level.getSunAngle(1.0F) > Math.PI ? 2 * Math.PI - level.getSunAngle(1.0F) : level.getSunAngle(1.0F));
		boolean night = time >= 5 * Math.PI / 8;
		int skyLight = CommonReflectionHelper.getSkyEngine(level.getLightEngine()).getLightValue(pos);
		int blockLight = CommonReflectionHelper.getBlockEngine(level.getLightEngine()).getLightValue(pos);
		boolean fullDark = skyLight == 0 && blockLight == 0;
		boolean isLight = blockLight > 0 || (blockLight == 0 && skyLight > 16 - 16 * level.getMoonBrightness());
		return fullDark || (!isLight && night);
	}
	
	public static boolean sawLightning(Level level, BlockPos pos)
	{
		int skyLight = CommonReflectionHelper.getSkyEngine(level.getLightEngine()).getLightValue(pos);
		return skyLight > 0;
	}
}
