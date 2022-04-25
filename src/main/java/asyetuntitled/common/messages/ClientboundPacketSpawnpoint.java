package asyetuntitled.common.messages;

import java.util.function.Supplier;

import asyetuntitled.client.util.ClientPlayerSpawnPoints;
import asyetuntitled.common.player.spawn.SpawnPoint;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundPacketSpawnpoint
{
    private SpawnPoint point;
    private boolean add;
    
    public ClientboundPacketSpawnpoint(SpawnPoint point, boolean add)
    {
        this.point = point;
        this.add = add;
    }
    
    public ClientboundPacketSpawnpoint(FriendlyByteBuf buf)
    {
        this.point = new SpawnPoint(buf.readBlockPos(), ResourceKey.create(Registry.DIMENSION_REGISTRY, buf.readResourceLocation()));
        this.add = buf.readBoolean();
    }
    
     public void toBytes(FriendlyByteBuf buf)
     {
         buf.writeBlockPos(this.point.getBlockPos());
         buf.writeResourceLocation(this.point.getDimension().location());
         buf.writeBoolean(this.add);
     }

     public boolean handle(Supplier<NetworkEvent.Context> supplier) {
         NetworkEvent.Context ctx = supplier.get();
         ctx.enqueueWork(() -> {
             // Here we are client side.
             // Be very careful not to access client-only classes here! (like Minecraft) because
             // this packet needs to be available server-side too
             if(this.add)
             {
                 ClientPlayerSpawnPoints.addSpawnPoint(this.point);
             }
             else
             {
                 ClientPlayerSpawnPoints.removeSpawnPoint(this.point);
             }
         });
         return true;
     }
}
