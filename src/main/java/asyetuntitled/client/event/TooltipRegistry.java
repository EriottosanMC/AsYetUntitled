package asyetuntitled.client.event;

import asyetuntitled.client.screen.ClientCustomBundleTooltip;
import asyetuntitled.common.item.CustomBundleTooltip;
import net.minecraftforge.client.MinecraftForgeClient;

public class TooltipRegistry
{

    public static void register()
    {
        MinecraftForgeClient.registerTooltipComponentFactory(CustomBundleTooltip.class, ClientCustomBundleTooltip::new);
    }

}
