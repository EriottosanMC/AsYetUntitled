package asyetuntitled;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class SanityConfig
{
    public static ForgeConfigSpec.IntValue MAX_SANITY;

    public static void registerServerConfig(Builder SERVER_BUILDER)
    {
        SERVER_BUILDER.comment("Sanity settings").push("sanity");
        
        MAX_SANITY = SERVER_BUILDER.comment("Max sanity value").defineInRange("maxSanity", 5000, 1, Integer.MAX_VALUE);
        
        SERVER_BUILDER.pop();
    }
}
