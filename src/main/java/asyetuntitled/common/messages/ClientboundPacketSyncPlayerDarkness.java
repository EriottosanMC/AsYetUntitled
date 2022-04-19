package asyetuntitled.common.messages;

import java.util.function.Supplier;

import asyetuntitled.client.sanity.ClientSanityData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundPacketSyncPlayerDarkness 
{
	private final int playerSanity;
	private boolean forceUpdate;
	
	public ClientboundPacketSyncPlayerDarkness(int sanity, boolean forceUpdate)
	{
		this.playerSanity = sanity;
		this.forceUpdate = forceUpdate;
	}
	
	public ClientboundPacketSyncPlayerDarkness(FriendlyByteBuf buf)
	{
		playerSanity = buf.readInt();
		forceUpdate = buf.readBoolean();
	}
	
	 public void toBytes(FriendlyByteBuf buf)
	 {
		 buf.writeInt(playerSanity);
		 buf.writeBoolean(forceUpdate);
	 }

	 public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		 NetworkEvent.Context ctx = supplier.get();
		 ctx.enqueueWork(() -> {
			 // Here we are client side.
			 // Be very careful not to access client-only classes here! (like Minecraft) because
			 // this packet needs to be available server-side too
			 ClientSanityData.setTarget(playerSanity, forceUpdate);
		 });
		 return true;
	 }
}
