package asyetuntitled.common.messages;

import java.util.function.Supplier;

import asyetuntitled.common.util.DarknessHelper;
import asyetuntitled.common.util.capability.PlayerDarknessProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class ServerboundPacketSkyFlash {

	
	public ServerboundPacketSkyFlash()
	{
	}
	
	public ServerboundPacketSkyFlash(FriendlyByteBuf buf)
	{
	}
	
	public void toBytes(FriendlyByteBuf buf)
	{
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context ctx = supplier.get();
		ctx.enqueueWork(() -> {
			// Here we are server side
			ServerPlayer player = ctx.getSender();
			player.getCapability(PlayerDarknessProvider.PLAYER_DARKNESS).ifPresent(cap -> {
				if(DarknessHelper.sawLightning(player.level, player.blockPosition()))
				{
					cap.resetDarkness(player);
				}
			});
		});
		return true;
	}
}
