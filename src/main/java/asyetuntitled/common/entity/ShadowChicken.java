package asyetuntitled.common.entity;

import asyetuntitled.common.messages.ClientboundPacketShadowSoundEntity;
import asyetuntitled.common.messages.MessagesRegistry;
import asyetuntitled.common.sound.SoundsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ShadowChicken extends Chicken implements ShadowCreature
{

	public ShadowChicken(EntityType<? extends Chicken> chicken, Level level) {
		super(chicken, level);
	}

	@Override
	public float getSanityThreshold() 
	{
		return 0.9F;
	}
	
	@Override
	public float getFullStrengthThreshold()
	{
	    return 0.3F;
	}
	
	@Override
	public void playSound(SoundEvent sound, float pitch, float volume) 
	{
		if (!this.isSilent() && !this.level.isClientSide)
		{
			ServerLevel sl = (ServerLevel) level;
			for(ServerPlayer player : sl.getServer().getPlayerList().getPlayers())
			{
				if(player.level.dimension() == this.level.dimension() && player.distanceToSqr(this) < 16*16)
				{
					MessagesRegistry.sendToPlayer(new ClientboundPacketShadowSoundEntity(sound, this.getSoundSource(), this.getX(), this.getY(), this.getZ(), volume, pitch * this.level.random.nextFloat() * 0.5F, this.getSanityThreshold()), player);
				}
			}
		}
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
       return SoundsRegistry.SHADOW_CHICKEN_AMBIENT.get();
    }

	@Override
	protected SoundEvent getHurtSound(DamageSource p_28262_) {
       return SoundsRegistry.SHADOW_CHICKEN_HURT.get();
    }

	@Override
	protected SoundEvent getDeathSound() {
       return SoundsRegistry.SHADOW_CHICKEN_DEATH.get();
    }

	@Override
	protected void playStepSound(BlockPos p_28254_, BlockState p_28255_) {
	    this.playSound(SoundsRegistry.SHADOW_CHICKEN_STEP.get(), 0.15F, 1.0F);
	}

}
