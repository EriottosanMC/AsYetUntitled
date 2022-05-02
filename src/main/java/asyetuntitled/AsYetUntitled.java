package asyetuntitled;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asyetuntitled.common.block.BlocksRegistry;
import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.item.ItemsRegistry;
import asyetuntitled.common.menu.MenusRegistry;
import asyetuntitled.common.particle.ParticlesRegistry;
import asyetuntitled.common.sound.SoundsRegistry;
import asyetuntitled.common.world.worldgen.StructuresRegistry;


@Mod(value = AsYetUntitled.MODID)
public class AsYetUntitled
{
    public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "asyetuntitled";
	public static final String NAME = "As Yet Untitled";
    
    public AsYetUntitled() 
    {

        // Registers the mod so the event bus looks for our event handlers
        MinecraftForge.EVENT_BUS.register(this);
        
        // Get the Mod Event Bus
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Registers the mod content to the respective deferred registries
        BlocksRegistry.registerBlocks(modBus);
        ItemsRegistry.registerItems(modBus);
        EntityRegistry.registerEntities(modBus);
        MenusRegistry.registerContainers(modBus);
        SoundsRegistry.registerSounds(modBus);    
        ParticlesRegistry.registerParticles(modBus);    
        StructuresRegistry.registerStructures(modBus);
        Config.registerConfigurations();
    }
    
    public static void logMessage(String s) 
    {
    	StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    	String message = "";
    	message = message + stackTraceElements[2].toString() + "; ";
    	message = message + s;
    	LOGGER.info(message);
    }
    
}
