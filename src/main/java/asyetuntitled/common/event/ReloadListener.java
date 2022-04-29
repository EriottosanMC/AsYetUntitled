package asyetuntitled.common.event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import asyetuntitled.client.sanity.ClientSanityData;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;

public class ReloadListener implements ResourceManagerReloadListener
{
    
    @Override
    public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier p_10752_, ResourceManager p_10753_, ProfilerFiller p_10754_, ProfilerFiller p_10755_, Executor p_10756_, Executor p_10757_) {
        ClientSanityData.forceUpdate();
        System.out.println("pumpkin king");
        return p_10752_.wait(Unit.INSTANCE).thenRunAsync(() -> {
               p_10755_.startTick();
               p_10755_.push("listener");
               this.onResourceManagerReload(p_10753_);
               p_10755_.pop();
               p_10755_.endTick();
            }, p_10757_);
     }

    @Override
    public void onResourceManagerReload(ResourceManager manager)
    {
        System.out.println("oogieboogie");
        ClientSanityData.forceUpdate();
    }
}
