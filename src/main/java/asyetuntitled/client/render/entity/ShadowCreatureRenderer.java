package asyetuntitled.client.render.entity;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import asyetuntitled.client.render.entity.layer.ShadowCreatureLayer;
import asyetuntitled.client.util.ClientResourceLocations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShadowCreatureRenderer<T extends PathfinderMob> extends MobRenderer<T, HierarchicalModel<T>> 
{
	public ShadowCreatureRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayer, HierarchicalModel<T> model, ResourceLocation loc) 
	{
		super(context, model, 0.0F);
		this.addLayer(new ShadowCreatureLayer<T>(this, model, loc));
	}
	
	@Override
	@Nullable
	protected RenderType getRenderType(PathfinderMob p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
		return RenderType.entityTranslucentCull(ClientResourceLocations.BLANK_TEXTURE);
	}
	   
	@Override
	public void render(T mob, float p_115309_, float p_115310_, PoseStack poseStack, MultiBufferSource bufferSource, int p_115313_) {
		super.render(mob, p_115309_, p_115310_, poseStack, bufferSource, p_115313_);
	}

	@Override
	public ResourceLocation getTextureLocation(PathfinderMob mob) 
	{
		return ClientResourceLocations.BLANK_TEXTURE;
	}

}
