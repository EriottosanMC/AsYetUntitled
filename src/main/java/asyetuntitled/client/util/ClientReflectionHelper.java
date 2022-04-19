package asyetuntitled.client.util;

import java.lang.reflect.Field;
import java.util.List;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@OnlyIn(Dist.CLIENT)
public class ClientReflectionHelper 
{
	private static Field updateLightTextureField;
	private static Field blockLightRedFlickerField;
	private static Field lightTextureField;
	private static Field lightPixelsField;
	
	private static Field pixelsField;
	
	private static Field passesField;
	

	public static void init()
	{
		updateLightTextureField = ObfuscationReflectionHelper.findField(LightTexture.class, "f_109873_");
		blockLightRedFlickerField = ObfuscationReflectionHelper.findField(LightTexture.class, "f_109874_");
		lightTextureField = ObfuscationReflectionHelper.findField(LightTexture.class, "f_109870_");
		lightPixelsField = ObfuscationReflectionHelper.findField(LightTexture.class, "f_109871_");
		pixelsField = ObfuscationReflectionHelper.findField(DynamicTexture.class, "f_117977_");
		passesField = ObfuscationReflectionHelper.findField(PostChain.class, "f_110009_");
		 
	}
	
	public static boolean getUpdateLightTexture(LightTexture lt)
	{
		try {
			return updateLightTextureField.getBoolean(lt);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static float getBlockLightRedFlicker(LightTexture lt)
	{
		try {
			return blockLightRedFlickerField.getFloat(lt);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0.0F;
	}
	
	public static DynamicTexture getLightTexture(LightTexture lt)
	{
		try {
			return (DynamicTexture) lightTextureField.get(lt);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static NativeImage getLightPixels(LightTexture lt)
	{
		try {
			return (NativeImage) lightPixelsField.get(lt);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static NativeImage getPixels(DynamicTexture dt)
	{
		try {
			return (NativeImage) pixelsField.get(dt);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<PostPass> getPasses(PostChain pc)
	{
		try {
			return (List<PostPass>) passesField.get(pc);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static boolean notSet() 
	{
		return passesField == null || updateLightTextureField == null || blockLightRedFlickerField == null || lightTextureField == null || lightPixelsField == null || pixelsField == null;
	}
}
