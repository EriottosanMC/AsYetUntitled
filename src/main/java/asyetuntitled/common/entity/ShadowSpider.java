package asyetuntitled.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

public class ShadowSpider extends Spider implements ShadowCreature
{
	public ShadowSpider(EntityType<? extends ShadowSpider> spider, Level level) {
		super(spider, level);
	}

	@Override
	public float getSanityThreshold() 
	{
		return 0.75F;
	}

}
