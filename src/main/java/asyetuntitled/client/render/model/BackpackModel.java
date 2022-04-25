package asyetuntitled.client.render.model;

import java.util.Collections;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import asyetuntitled.common.entity.backpack.EntityBackpack;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;

public class BackpackModel extends ListModel<EntityBackpack> {
	
	private final Pair<ImmutableList<ModelPart>, ImmutableList<ModelPart>> modelParts;

	public BackpackModel(ModelPart part) 
	{
		this.modelParts = CommonBackpackModel.setModelParts(part);
	}
	
	public static LayerDefinition createBodyLayer() 
	{
		return CommonBackpackModel.createBodyLayer(false);
   	}
	
	
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int color)
	{
		CommonBackpackModel.render(poseStack, buffer, packedLight, color, modelParts.getFirst(), modelParts.getSecond());
	}
	
	@Override
	public Iterable<ModelPart> parts() 
	{
		return Collections.emptyList();
	}
	
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer vertexBuilder, int packedLight, int overlayTexture, float r, float g, float b, float alpha)	{}

	@Override
	public void setupAnim(EntityBackpack p_102269_, float p_102270_, float p_102271_, float p_102272_, float p_102273_, float p_102274_) {}
}