package asyetuntitled;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config
{
    /*
     * CLIENT configs for things that exist on client side;
     * 
     * COMMON config affects server and client, *but is not synced*! This means players can mess
     * things up by having different settings to the server.
     *
     * Why does it exist, then? Both Client and Common exist per Minecraft directory, whereas
     * Server exists per world. This means you cannot use Server config for world gen.
     * 
     * SERVER config exists on the server and is synced to players on connection.
     */
    
    public static void registerConfigurations()
    {
        registerServerConfigs();
        registerCommonConfigs();
        registerClientConfigs();
    }
    
    private static void registerClientConfigs() {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
//        AsYetUntitledConfig.registerClientConfig(CLIENT_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }

    private static void registerCommonConfigs() {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
//        AsYetUntitledConfig.registerCommonConfig(COMMON_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());
    }

    private static void registerServerConfigs() {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
//        AsYetUntitledConfig.registerServerConfig(SERVER_BUILDER);
        SanityConfig.registerServerConfig(SERVER_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}
