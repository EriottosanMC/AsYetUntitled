package asyetuntitled.common.messages;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ServerboundPacketGatherSanity
{
    public static final String MESSAGE_NO_SANITY= "message.nosanity";

    public ServerboundPacketGatherSanity() {
    }

    public ServerboundPacketGatherSanity(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Here we are server side
        });
        return true;
    }
}
