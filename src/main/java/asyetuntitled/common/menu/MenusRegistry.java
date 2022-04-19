package asyetuntitled.common.menu;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.container.LivingChestContainer;
import asyetuntitled.common.entity.livingchest.LivingChest;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenusRegistry {

	private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, AsYetUntitled.MODID);

	public static final RegistryObject<MenuType<LivingChestContainer>> LIVING_CHEST_CONTAINER = CONTAINERS.register("living_chest", 
			() -> IForgeMenuType.create((windowId, inv, data) -> 
			{
				Entity entity = inv.player.level.getEntity(data.readInt());
				if (entity instanceof LivingChest chest)
				{
					return new LivingChestContainer(windowId, inv, chest);
				}
				else{
					throw new IllegalStateException("Tried to open living chest inventory on a non chest");
				}
			}));
	
	public static void register(IEventBus bus) 
	{
		CONTAINERS.register(bus);
	}
}
