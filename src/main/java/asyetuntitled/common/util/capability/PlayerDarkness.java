package asyetuntitled.common.util.capability;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.util.SoundsRegistry;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

public class PlayerDarkness
{
	private boolean alreadyDark = false;
	private int darknessTicks = 0;
	private DamageSource CHARLIE = new DamageSource(AsYetUntitled.MODID + ".charlie");
	public void increaseDarkness(Player player)
	{
		player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
			sanity.changeSanity(player, alreadyDark ? -1 : -20);
		});
		if(this.darknessTicks % 80 == 0)
		{
			SoundEvent s = SoundsRegistry.CHARLIE_MOOD.get();
//			s = SoundEvents.
			player.playNotifySound(s, SoundSource.HOSTILE, 1.0F, player.getRandom().nextFloat(0.5F)+0.5F);
		}
		if(this.darknessTicks > 400 && this.darknessTicks % 33 == 0)
		{
			SoundEvent s = SoundsRegistry.CHARLIE_SCARE.get();
			player.playNotifySound(s, SoundSource.HOSTILE, 0.6F, player.getRandom().nextFloat(0.7F)+0.3F);
		}
		if(this.darknessTicks > 800 && this.darknessTicks % 120 == 0)
		{
			SoundEvent s = SoundsRegistry.CHARLIE_ATTACK.get();
			player.playNotifySound(s, SoundSource.HOSTILE, 2F, player.getRandom().nextFloat(0.3F)+0.7F);
			player.hurt(this.CHARLIE, 8.0F);
		}
		this.darknessTicks++;
		
		if(this.darknessTicks == 100)
		{
			player.sendMessage(new TranslatableComponent("asyetuntitled.charlie.scare.message"), Util.NIL_UUID);
		}
		
		alreadyDark = true;
	}
	
	public void resetDarkness(Player player)
	{
		if(this.darknessTicks != 0)
		{
			this.darknessTicks = 0;
			player.sendMessage(new TranslatableComponent("asyetuntitled.charlie.gone.message"), Util.NIL_UUID);
			alreadyDark = false;
		}
	}
	
	public int getDarkness()
	{
		return this.darknessTicks;
	}
	
    public void saveNBTData(CompoundTag compound) {
        compound.putInt("darkness", darknessTicks);
    }

    public void loadNBTData(CompoundTag compound) {
        darknessTicks = compound.getInt("darkness");
    }
}
