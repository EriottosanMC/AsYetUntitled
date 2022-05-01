package asyetuntitled.client.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

public class LightMapGenerator 
{
	//0-3 = noon-midnight, 4 = lightning
	private static Vector3f[] skyColours = {new Vector3f(239, 234, 218), new Vector3f(219, 189, 134), new Vector3f(183, 78, 85), new Vector3f(0, 60, 160), new Vector3f(186, 253, 253), new Vector3f(237, 215, 156)};
	
	public static void update(LightTexture lt, Minecraft mc, DynamicTexture dt) {
		LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (level != null) {
   			if(player == null || level.dimension() != Level.OVERWORLD || player.hasEffect(MobEffects.NIGHT_VISION) ||
				(player.hasEffect(MobEffects.CONDUIT_POWER) && player.getWaterVision() > 0)) {}
   			else
   			{
   				mc.getProfiler().push("lightTex");
   			 	boolean night = false;
   		        float angle = level.getSunAngle(0.0F);
   			 	float angleMid = (float) (angle > Math.PI ? 2 * Math.PI - angle : angle);
   				int skyIndex;
   				float percentThrough;
   				float sunStrength;
   				
   				if(angleMid < 3 * Math.PI / 8)
   				{
   					skyIndex = 0;
   					percentThrough = (float) (8 * angleMid / (3 * Math.PI));
   					sunStrength = 1.0F;
   				}
   				else if(angleMid < Math.PI / 2)
   				{
   					skyIndex = 1;
   					percentThrough = (float) ((8 * angleMid - 3 * Math.PI) / Math.PI);
   					sunStrength = 1.0F - (float) ((8 * angleMid - 3 * Math.PI) / (2 * Math.PI));
   				}
   				else if(angleMid < 5 * Math.PI / 8) 
   				{
   					skyIndex = 2;
   					percentThrough = (float) ((8 * angleMid - 4 * Math.PI) / Math.PI);
   					sunStrength = 1.0F - (float) ((8 * angleMid - 3 * Math.PI) / (2 * Math.PI));
   				}
   				else 
   				{
   					skyIndex = 3;
   					percentThrough = (float) ((8 * angleMid - 5 * Math.PI) / (3 * Math.PI));
   					night = true;
   					sunStrength = 0.0F;
   				}
   				
   				if(level.isThundering()) sunStrength -= 0.4F;
   				else if(level.isRaining()) sunStrength -= 0.2F;
   				  				
   				if(sunStrength < 0) sunStrength = 0.0F;
   			
   				
   				float moon;
   				int moonPhase = level.getMoonPhase();
   				switch(moonPhase)
   				{
   					case 0:
   						moon = 1.0F;
   						break;
   					case 1:
   					case 7:
   						moon = 0.3F;
   						break;
   					case 2:
   					case 6:
   						moon = 0.2F;
   						break;
   					case 3:
   					case 5:
   						moon = 0.1F;
   						break;
   					default:
   						moon = 0.0F;
   				}
   				
   				float moonReduction;
   				if(angleMid < Math.PI/2) moonReduction = 1.0F;
   				else if(angleMid >= 5 * Math.PI / 8) moonReduction = moon;
   				else moonReduction = (1.0F - percentThrough) + percentThrough*moon;
   				int timeIndex;
   				if(angleMid >= 5 * Math.PI / 8) timeIndex = 3;
   				else timeIndex = skyIndex+1;
   				
   				if(angle >= Math.PI)
   				{
   					percentThrough = 1.0F - percentThrough;
   					int tmp = skyIndex;
   					skyIndex = timeIndex;
   					timeIndex = tmp;
   				}
   				//Selects lightning colours if lightning
   				if(level.getSkyFlashTime() > 0) 
				{
   					sunStrength = 1.0F;
   					skyIndex = timeIndex = 4;
				}
   				
   				//creates a colour vector averaged between this and the next light values, then adjusts for the moon and weather
   				Vector3f skyVector = skyColours[skyIndex].copy();
   				Vector3f timeVector = skyColours[timeIndex].copy();
   				skyVector.lerp(timeVector, percentThrough);
   				boolean flag = false;
   				if(level.getSkyFlashTime() == 0)
   				{
   	   				skyVector.mul(moonReduction);
   	   				skyVector.mul(1 - (0.4F * level.getThunderLevel(1.0F)));
   					skyVector.mul(1 - (0.4F * level.getRainLevel(1.0F)));
   					flag = true;
   				}
				NativeImage pixels = ClientReflectionHelper.getLightPixels(lt);
   		        
   		        //Block light colour
   		        Vector3f blockVector = skyColours[5].copy();

   				/*
   				 * sky = 0 -> no sky exposure, sky = 15 -> full sky exposure (unaffected by night for constant input)
   				 * block = 0 -> no block light exposure, block = 14 -> full block light exposure (functionally 14)
   				 * both=15 -> overlays
   				 */
   		        
   		        float moonlightThreshold = 16 - 16 * level.getMoonBrightness();
   		        for(int sky = 0; sky < 16; ++sky) {
	              for(int block = 0; block < 15; ++block) 
	              {

	        		 float blockLit = block/14.0F;
	        		 float skyLit = sky/15.0F;
	        		 //Creates a copy of the colour vector (time based sky light) and modifies for sky exposure
	        		 Vector3f colourVector = skyVector.copy();
	        		 colourVector.mul(skyLit);
	        		 
	        		 //Creates a block light vector and modifies it based on block exposure
        			 float sunEffect = sunStrength * skyLit;
        			 float blockEffect = 1.0F;
        			 if(sunEffect > 0)
        			 {
        				 blockEffect = blockLit / sunEffect;
        			 }
        			 if(blockEffect > 1) blockEffect = 1.0F;
        			 else if(blockEffect < 0) blockEffect = 0.0F;
        			 colourVector.lerp(blockVector, blockEffect * blockLit);
	        		
        			 int red = (int) colourVector.x();
	        		 int green = (int) colourVector.y();
	        		 int blue = (int) colourVector.z();
	        		 if(red == 0 && green == 0 && blue == 0)
	        		 {
	        			 blue = 10;
	        		 }
	        		 if(flag && block == 0 && sky < moonlightThreshold && night) 
        			 {
	        			 pixels.setPixelRGBA(block, sky, 0xFF010000);
        			 }
	        		 else 
	        		 {
	        			 pixels.setPixelRGBA(block, sky, -16777216 | blue << 16 | green << 8 | red);
	        		 } 
        		 }
	           }
   		       dt.upload();
	           mc.getProfiler().pop();
   			}
        }
	}
}
