package asyetuntitled.client.render.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import asyetuntitled.client.render.model.LivingChestModel;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.entity.livingchest.LivingChest;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LivingChestRenderer extends MobRenderer<LivingChest, LivingChestModel<LivingChest>> 
{
   public LivingChestRenderer(EntityRendererProvider.Context p_174452_) {
      super(p_174452_, new LivingChestModel<>(p_174452_.bakeLayer(ClientResourceLocations.LIVING_CHEST_MODEL_LAYER)), 0.5F);
   }

   public void render(LivingChest p_116531_, float p_116532_, float p_116533_, PoseStack p_116534_, MultiBufferSource p_116535_, int p_116536_) {
	   RenderSystem.depthMask(true);
	   super.render(p_116531_, p_116532_, p_116533_, p_116534_, p_116535_, p_116536_);
   }

   public ResourceLocation getTextureLocation(LivingChest p_116526_) {
	   return ClientResourceLocations.LIVING_CHEST_TEXTURE;
   }
}