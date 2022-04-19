package asyetuntitled.common.entity;


import asyetuntitled.common.messages.ClientboundPacketShadowSoundEntity;
import asyetuntitled.common.messages.MessagesRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;

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
	public void playSound(SoundEvent sound, float pitch, float volume) 
	{
		if (!this.isSilent() && !this.level.isClientSide)
		{
			ServerLevel sl = (ServerLevel) level;
			for(ServerPlayer player : sl.getServer().getPlayerList().getPlayers())
			{
				if(player.level.dimension() == this.level.dimension() && player.distanceToSqr(this) < 16*16)
				{
					MessagesRegistry.sendToPlayer(new ClientboundPacketShadowSoundEntity(sound, this.getSoundSource(), this.getX(), this.getY(), this.getZ(), volume, pitch, this.getSanityThreshold()), player);
				}
			}
		}
//		if(level.isClientSide)
//		{
//			
//			Minecraft mc = Minecraft.getInstance();
//			Player player = mc.player;
//            mc.getSoundManager().play(new SanitySoundInstance(sound, SoundSource.NEUTRAL, player, this));
//		}
		
	}
}
