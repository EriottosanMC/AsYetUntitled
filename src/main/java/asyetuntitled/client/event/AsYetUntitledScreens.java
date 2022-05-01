package asyetuntitled.client.event;

import asyetuntitled.client.screen.LivingChestScreen;
import asyetuntitled.common.menu.MenusRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

public class AsYetUntitledScreens
{

    public static void register()
    {
        MenuScreens.register(MenusRegistry.LIVING_CHEST_CONTAINER.get(), LivingChestScreen::new);          
    }

}
