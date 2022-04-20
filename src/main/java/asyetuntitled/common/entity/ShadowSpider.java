package asyetuntitled.common.entity;

import asyetuntitled.client.util.SoundsRegistry;
import asyetuntitled.common.messages.ClientboundPacketShadowSoundEntity;
import asyetuntitled.common.messages.MessagesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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
	
	@Override
    public float getFullStrengthThreshold() 
    {
        return 0.25F;
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
        return SoundsRegistry.SHADOW_SPIDER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33814_) {
        return SoundsRegistry.SHADOW_SPIDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsRegistry.SHADOW_SPIDER_DEATH.get();
    }
    
    @Override
    protected void playStepSound(BlockPos p_33804_, BlockState p_33805_) {
        this.playSound(SoundsRegistry.SHADOW_SPIDER_STEP.get(), 0.15F, 1.0F);
     }
}
