package asyetuntitled.common.messages;

import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

import asyetuntitled.client.sanity.ClientSanityData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundPacketShadowSoundEntity {
	public static final float LOCATION_ACCURACY = 8.0F;
	private final SoundEvent sound;
	private final SoundSource source;
	private final int x;
	private final int y;
	private final int z;
	private final float volume;
	private final float pitch;
	private final float sanityThreshold;

	public ClientboundPacketShadowSoundEntity(SoundEvent sound, SoundSource source, double x, double y, double z, 
			float volume, float pitch, float sanityThreshold)
	{
		Validate.notNull(sound, "sound");
		this.sound = sound;
		this.source = source;
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
		this.volume = volume;
		this.pitch = pitch;
		this.sanityThreshold = sanityThreshold;
	}

	public ClientboundPacketShadowSoundEntity(FriendlyByteBuf buf) {
		this.sound = buf.readRegistryIdSafe(SoundEvent.class);
		this.source = buf.readEnum(SoundSource.class);
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.volume = buf.readFloat();
		this.pitch = buf.readFloat();
		this.sanityThreshold = buf.readFloat();
	}

	public void toBytes(FriendlyByteBuf buf) {
	    buf.writeRegistryId(this.sound);
	    buf.writeEnum(this.source);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
		buf.writeFloat(this.sanityThreshold);
	}

	public SoundEvent getSound() {
		return this.sound;
	}

	public SoundSource getSource() {
		return this.source;
	}

	public double getX() {
		return (double)((float)this.x / 8.0F);
	}	

	public double getY() {
		return (double)((float)this.y / 8.0F);
	}

	public double getZ() {
		return (double)((float)this.z / 8.0F);
	}

	public float getVolume() {
		return this.volume;
	}

	public float getPitch() {
		return this.pitch;
	}
	
	public float getSanityThreshold()
	{
		return this.sanityThreshold;
	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context ctx = supplier.get();
		ctx.enqueueWork(() -> {
			// Here we are client side.
			 // Be very careful not to access client-only classes here! (like Minecraft) because
			 // this packet needs to be available server-side too
			ClientSanityData.playSound(this.sound, this.source, new BlockPos(this.x, this.y, this.z), this.volume, this.pitch, this.sanityThreshold);
		 });
		 return true;
	 }
}