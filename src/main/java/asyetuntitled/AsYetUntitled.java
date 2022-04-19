package asyetuntitled;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asyetuntitled.client.util.ClientReflectionHelper;
import asyetuntitled.client.util.SoundsRegistry;
import asyetuntitled.common.entity.EntityRegistry;
import asyetuntitled.common.messages.MessagesRegistry;
import asyetuntitled.common.particle.ParticlesRegistry;
import asyetuntitled.common.util.BlockChange;
import asyetuntitled.common.util.CommonReflectionHelper;

import java.util.stream.Collectors;

@Mod(value = AsYetUntitled.MODID)
public class AsYetUntitled
{
    public static final Logger LOGGER = LogManager.getLogger();
	public static final int MAX_SANITY = 5000;

    public static final String MODID = "asyetuntitled";
	public static final String NAME = "As Yet Untitled";
    
    public AsYetUntitled() 
    {
    	// Register mod config
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UmmConfig.SPEC, MODID + ".toml");

        // Get the Mod Event Bus
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		// Register the setup methods for modloading
		modBus.addListener(this::setupCommon);
		modBus.addListener(this::setupClient);
        // Register the enqueueIMC method for modloading. This allows us to send messages to other mods
        modBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading. This allows us to receive messages from other mods
        modBus.addListener(this::processIMC);
        
        // Registers the mod so the event bus looks for our event handlers
        MinecraftForge.EVENT_BUS.register(this);
        
        // Registers the mod content to the respective deferred registries
        EntityRegistry.registerEntities(modBus);
        SoundsRegistry.register(modBus);
        ParticlesRegistry.register(modBus);
    }

    // Common Setup Event.
    private void setupCommon(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            //Set up CommonReflectionHelper and BlockSpeeds
            MessagesRegistry.register();
            CommonReflectionHelper.init();
            BlockChange.init();
        });
        
   }
    
    private void setupClient(final FMLClientSetupEvent event)
    {
    	event.enqueueWork(() -> {
        	ClientReflectionHelper.init();
 	   });
     }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("asyetuntitled", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }
    
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) 
    {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
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
