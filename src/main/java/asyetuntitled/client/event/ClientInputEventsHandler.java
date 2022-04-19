package asyetuntitled.client.event;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.item.ItemsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.RawMouseEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AsYetUntitled.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientInputEventsHandler {
	
	
//	//Stops blank slots being selected by mouse press
//	@SubscribeEvent
//	public static void handleMousePress(ScreenEvent.MouseClickedEvent.Pre event)
//	{
//		System.out.println(event.isCancelable() +"a;"+event.hasResult());
//	if(event.getScreen() instanceof AbstractContainerScreen<?> gui && !(event.getScreen() instanceof CreativeModeInventoryScreen))
//		{
//			Slot slotUnder = gui.getSlotUnderMouse();
//			if(slotUnder != null && !slotUnder.getItem().isEmpty() && slotUnder.getItem().getItem() == ItemsRegistry.BLANK_SLOT.get())
//			{
//				event.setCanceled(true);
//			}
//		}
//	}
	
	//ScreenEvent.MouseClickedEvent.Pre used to be used, but allowed swapping of items.
	@SubscribeEvent
	public static void handleRawMousePress(RawMouseEvent event)
	{
		Minecraft mc = Minecraft.getInstance();
		Screen screen = mc.screen;
		if(screen != null && screen instanceof AbstractContainerScreen<?> gui && !(screen instanceof CreativeModeInventoryScreen))
		{
			Slot slotUnder = gui.getSlotUnderMouse();
			if(slotUnder != null && !slotUnder.getItem().isEmpty() && slotUnder.getItem().getItem() == ItemsRegistry.BLANK_SLOT.get())
			{
				event.setCanceled(true);
			}
		}
	}
	
	//Stops blank slots being moved our thrown by key press
	@SubscribeEvent
	public static void handleKeyPress(ScreenEvent.KeyboardKeyPressedEvent.Pre event)
	{
		Minecraft mc = Minecraft.getInstance();
		if(event.getScreen() instanceof AbstractContainerScreen<?> gui && !(event.getScreen() instanceof CreativeModeInventoryScreen))
		{
			Slot slotUnder = gui.getSlotUnderMouse();
			if(slotUnder != null && !slotUnder.getItem().isEmpty() && slotUnder.getItem().getItem() == ItemsRegistry.BLANK_SLOT.get())
			{
				if(mc.options.keySwapOffhand.getKey().getValue() == event.getKeyCode() || mc.options.keyDrop.getKey().getValue() == event.getKeyCode())
				{
					event.setCanceled(true);
					return;
				}
				for(int i = 0; i < mc.options.keyHotbarSlots.length; i++)
				{
					if(mc.options.keyHotbarSlots[i].getKey().getValue() == event.getKeyCode())
					{
						event.setCanceled(true);
						return;
					}
				}
			}
		}
	}
}
