package asyetuntitled.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class FollowingSoundInstance extends AbstractTickableSoundInstance implements SoundInstance {

	private final Player player;
	private final int xOffset;
	private final int yOffset;
	private final int zOffset;

	public FollowingSoundInstance(SoundEvent sound, SoundSource source, Player player)
	{
		super(sound, source);
		this.volume = player.getRandom().nextFloat() * 0.5F + 0.5F;
		this.pitch = player.getRandom().nextFloat();
		this.player = player;
		this.xOffset = player.getRandom().nextInt(10) - 5;
		this.yOffset = player.getRandom().nextInt(6) - 3;
		this.zOffset = player.getRandom().nextInt(10) - 5;
		this.x = player.getX() + this.xOffset;
		this.y = player.getY() + this.yOffset;
		this.z = player.getZ() + this.zOffset;
	}


	@Override
	public void tick() 
	{
		if(this.player.isRemoved())
		{
			this.stop();
		}
		else
		{
			this.x = this.player.getX() + this.xOffset;
			this.y = this.player.getY() + this.yOffset;
			this.z = this.player.getZ() + this.zOffset;
		}
	}

}
