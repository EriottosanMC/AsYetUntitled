package asyetuntitled.client.sanity;

import java.util.List;
import java.util.Random;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.sound.FollowingSoundInstance;
import asyetuntitled.client.util.ClientReflectionHelper;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.client.util.SoundsRegistry;
import asyetuntitled.common.particle.ParticlesRegistry;
import asyetuntitled.common.util.DarknessHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientSanityData 
{
	private static final float EYES_THRESHOLD = 0.75F;
	private static final float SOUNDS_THRESHOLD = 0.6F;
	private static int playerSanity;
	private static int targetSanity;
	private static boolean requiresForcedUpdate;
	
	public static void setTarget(int sanity, boolean forceUpdate)
    {
    	if(sanity < 0) sanity = 0;
    	else if (sanity > AsYetUntitled.MAX_SANITY) sanity = AsYetUntitled.MAX_SANITY;
    	ClientSanityData.targetSanity = sanity;
    	if(forceUpdate)
    	{
    		playerSanity = sanity;
    	}
    	requiresForcedUpdate = forceUpdate;
    }

    public static int getPlayerSanityRaw() 
    {
        return playerSanity;
    }
    
    public static float getPlayerSanity() 
    {
        return playerSanity / (float) AsYetUntitled.MAX_SANITY;
    }
    
    public static int getTargetSanity() 
    {
        return targetSanity;
    }
    
    public static void forceUpdate()
    {
    	requiresForcedUpdate = true;
    }
    
    public static void tickSanity()
    {
		if(requiresForcedUpdate ||
			(isIncreasing() && Math.round((40 * playerSanity)/((float) AsYetUntitled.MAX_SANITY)) != Math.round((40 * ++playerSanity)/((float) AsYetUntitled.MAX_SANITY))) ||
			(isDecreasing() && Math.round((40 * playerSanity)/((float) AsYetUntitled.MAX_SANITY)) != Math.round((40 * --playerSanity)/((float) AsYetUntitled.MAX_SANITY))))
    	{
    		requiresForcedUpdate = false;
			updateSanityRenderEffects();
    	}
    	
    	renderSpookyEyes();
    	soundSanitySounds();
    }
    
    private static void soundSanitySounds() 
    {
    	float f = getPlayerSanity() / SOUNDS_THRESHOLD;
    	if(f > 1.0F) f = 1.0F;
    	else if(f < 0) f = 0F;
    	Minecraft mc = Minecraft.getInstance();
    	if(mc.level.random.nextFloat() > f && mc.level.random.nextFloat() < 0.01F)
    	{
    		Player player = mc.player;
    		SoundEvent s = SoundsRegistry.OMINOUS_WHISPER.get();
            mc.getSoundManager().play(new FollowingSoundInstance(s, SoundSource.VOICE, player));
    	}
	}

	public static void renderSpookyEyes()
    {
    	if(getPlayerSanity() < EYES_THRESHOLD)
    	{
    		float f = 1.0F - Math.min(getPlayerSanity() / EYES_THRESHOLD, 1.0F);
    		Minecraft mc = Minecraft.getInstance();
    		Level level = mc.level;
    		Player player = mc.player;
    		Random random = level.random;
    		BlockPos pos = new BlockPos(player.getBlockX() + random.nextInt(30) - 15 , player.getBlockY() + random.nextInt(8), player.getBlockZ() + random.nextInt(30) - 15);
    		if(level.random.nextFloat() < f * 0.15F && DarknessHelper.isTrueDarkness(level, pos) && level.getBlockState(pos).isAir() && player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) >  30)
    		{
    			level.addParticle((ParticleOptions) ParticlesRegistry.SPOOKY_EYES.get(), pos.getX(), pos.getY(), pos.getZ(), 0D, 0D, 0D);
    		}
    	}
    }
    
    public static void updateSanityRenderEffects()
    {
    	// Phosphor = blur(3F), desaturate = self(1F),
    	Minecraft mc = Minecraft.getInstance();
    	GameRenderer renderer = mc.gameRenderer;
    	
    	if(renderer.getMainCamera().getEntity() instanceof Player player)
    	{
    		if(player.isSpectator())
    		{
    			if(renderer.currentEffect() != null && renderer.currentEffect().getName().contains(ClientResourceLocations.SANITY_SHADER.getPath()))
            	{
                    renderer.shutdownEffect();
            	}
    		}
    		else
    		{
    			if(renderer.currentEffect() == null)
            	{
            		renderer.loadEffect(ClientResourceLocations.SANITY_SHADER);
            	}
            	else if(!renderer.currentEffect().getName().contains(ClientResourceLocations.SANITY_SHADER.getPath()))
            	{
                    renderer.shutdownEffect();
                    renderer.loadEffect(ClientResourceLocations.SANITY_SHADER);
            	}
    		}
    	}

    	if(renderer.currentEffect() != null && renderer.currentEffect().getName().toString().contains(ClientResourceLocations.SANITY_SHADER.getPath()))
    	{
	    	List<PostPass> passes = ClientReflectionHelper.getPasses(renderer.currentEffect());
	    	float saturationModifier = Math.round(40  * getPlayerSanity()) * 0.025F;
	    	float phosphorModifier;
	    	if(getPlayerSanity() > 0.5F)
	    	{
	    		phosphorModifier = 0.0F;
	    	}
	    	else
	    	{
	    		phosphorModifier = 1.0F - getPlayerSanity() / 0.5F;
	    	}
	    	
	    	phosphorModifier *= 0.7F;

	    	float desaturate = Math.max(saturationModifier, 0.1F);
			
			for(PostPass pass : passes)
	    	{
        		if(pass.getName().equals("color_convolve"))
	    		{
	        		pass.getEffect().getUniform("Saturation").getFloatBuffer().put(0, desaturate);
	    		}
	    		else if(pass.getName().equals("phosphor"))
	    		{
	    			pass.getEffect().getUniform("Phosphor").getFloatBuffer().put(0, phosphorModifier*1.2F);
	    			pass.getEffect().getUniform("Phosphor").getFloatBuffer().put(1, phosphorModifier);
	    			pass.getEffect().getUniform("Phosphor").getFloatBuffer().put(2, phosphorModifier);
	    		}
	    	}
    	}
    }

	public static boolean isIncreasing()
	{
		return targetSanity > playerSanity;
	}
	
	public static boolean isDecreasing()
	{
		return targetSanity < playerSanity;
	}

	public static float getArrowStrength()
	{
		int i = Math.abs(playerSanity - targetSanity);
		if(i == 0)
		{
			return 0.0F;
		}
		else if(i > 30)
		{
			return 1.0F;
		}
		else
		{
			return i / 30F;
		}
	}

	public static void playSound(SoundEvent sound, SoundSource source, BlockPos pos, float volumeO, float pitch, float sanityThreshold) 
	{
    	float volume = 1.0F - (getPlayerSanity() / sanityThreshold);
    	if(volume > 1.0F) volume = 1.0F;
    	else if(volume < 0) volume = 0F;
    	volume *= volumeO;
		Minecraft mc = Minecraft.getInstance();
		Level level = mc.level;
    	level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), sound, source, volume, pitch, false);
	}
}
