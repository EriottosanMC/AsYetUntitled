package asyetuntitled.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ClientReflectionHelper 
{
	private static Field updateLightTextureField;
	private static Field blockLightRedFlickerField;
	private static Field lightTextureField;
	private static Field lightPixelsField;
	
	private static Field pixelsField;
	
	private static Field passesField;
	
	private static Method renderQuadListMethod;

	public static void init()
	{
		updateLightTextureField = ObfuscationReflectionHelper.findField(LightTexture.class, "f_109873_");
		blockLightRedFlickerField = ObfuscationReflectionHelper.findField(LightTexture.class, "f_109874_");
		lightTextureField = ObfuscationReflectionHelper.findField(LightTexture.class, "f_109870_");
		lightPixelsField = ObfuscationReflectionHelper.findField(LightTexture.class, "f_109871_");
		pixelsField = ObfuscationReflectionHelper.findField(DynamicTexture.class, "f_117977_");
		passesField = ObfuscationReflectionHelper.findField(PostChain.class, "f_110009_");
		renderQuadListMethod = ObfuscationReflectionHelper.findMethod(ModelBlockRenderer.class, "m_111058_", PoseStack.Pose.class, VertexConsumer.class, Float.class, Float.class, Float.class, List.class, Integer.class, Integer.class); 
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


	public static void renderQuadList(ModelBlockRenderer renderer, PoseStack.Pose pose, VertexConsumer vertex, float f1, float f2, float f3, List<BakedQuad> quads, int i1, int i2)
	{
	    try
        {
            renderQuadListMethod.invoke(renderer, pose,  vertex, f1, f2, f3, quads, i1, i2);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
	}
	
	public static boolean notSet() 
	{
		return renderQuadListMethod == null || passesField == null || updateLightTextureField == null || blockLightRedFlickerField == null || lightTextureField == null || lightPixelsField == null || pixelsField == null;
	}
}
