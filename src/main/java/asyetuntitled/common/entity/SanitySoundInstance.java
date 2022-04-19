package asyetuntitled.common.entity;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class SanitySoundInstance extends AbstractTickableSoundInstance implements SoundInstance
{
	private final Player player;
	private final Entity entity;
	private final ShadowCreature shadow;
	
	public SanitySoundInstance(SoundEvent sound, SoundSource source, Player player, Entity entity)
	{
		super(sound, source);
		this.player = player;
		this.entity = entity;
		this.shadow = (ShadowCreature) entity;
		this.volume = shadow.getSanityByThresholdInverted(player);
	}

	@Override
	public void tick() 
	{
		if(this.entity.isRemoved() || this.player.isRemoved())
		{
			this.stop();
		}
		else
		{
			this.volume = shadow.getSanityByThresholdInverted(player);
		}
	}

}
