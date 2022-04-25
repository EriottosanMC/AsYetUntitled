package asyetuntitled.client.render.model;

import java.util.Collections;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;

public class WearableBackpackModel extends AgeableListModel<LivingEntity> {
	
	private static Pair<ImmutableList<ModelPart>, ImmutableList<ModelPart>> modelParts;

	public WearableBackpackModel(ModelPart part) {
		modelParts = CommonBackpackModel.setModelParts(part);
	}

	public static LayerDefinition createBodyLayer() 
	{
		return CommonBackpackModel.createBodyLayer(true);
	}

//	private static Map<Integer, Item> getBackpackItems() {
//		return new LinkedHashMap<>(Map.of(
//				0, UMMItems.BACKPACK_SIMPLE.get()
//				,
//				1, ModItems.IRON_BACKPACK.get(),
//				2, ModItems.GOLD_BACKPACK.get(),
//				3, ModItems.DIAMOND_BACKPACK.get(),
//				4, ModItems.NETHERITE_BACKPACK.get()
//		));
//	}

	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int color)
	{
		CommonBackpackModel.render(poseStack, buffer, packedLight, color, modelParts.getFirst(), modelParts.getSecond());
	}

	@Override
	protected Iterable<ModelPart> headParts() 
	{
		return Collections.emptyList();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() 
	{
		return Collections.emptyList();
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {}
	
	@Override
	public void setupAnim(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
}