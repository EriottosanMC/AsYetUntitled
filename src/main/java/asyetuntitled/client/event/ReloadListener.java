package asyetuntitled.client.event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import asyetuntitled.client.sanity.ClientSanityData;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public class ReloadListener implements PreparableReloadListener
{

    @Override
    public CompletableFuture<Void> reload(PreparationBarrier p_10638_, ResourceManager p_10639_,
            ProfilerFiller p_10640_, ProfilerFiller p_10641_, Executor ex1, Executor p_10643_)
    {
        
        return CompletableFuture.runAsync(() -> {
            ClientSanityData.forceUpdate();
         }, ex1);
    }
    

}
