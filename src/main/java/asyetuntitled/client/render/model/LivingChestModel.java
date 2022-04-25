package asyetuntitled.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import asyetuntitled.common.entity.livingchest.LivingChest;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class LivingChestModel<Type extends LivingChest> extends EntityModel<Type> {
	
	private final ModelPart body;
	private final ModelPart mouth;

	public LivingChestModel(ModelPart root) {
		this.body = root.getChild("body");
		this.mouth = this.body.getChild("mouth");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 22).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-5.0F, -7.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(34, 48).addBox(-6.0F, -9.0F, 5.0F, 12.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 40).addBox(-6.0F, -9.0F, -6.0F, 12.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 23).addBox(5.0F, -9.0F, -5.0F, 1.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(30, 23).addBox(5.0F, -9.0F, -5.0F, 1.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(30, 1).addBox(-6.0F, -9.0F, -5.0F, 1.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		body.addOrReplaceChild("feet", CubeListBuilder.create().texOffs(0, 15).addBox(3.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(3.0F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-5.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -10.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(12, 37).addBox(5.0F, -4.0F, -10.0F, 1.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(-6.0F, -4.0F, 0.0F, 12.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 51).addBox(-6.0F, -4.0F, -11.0F, 12.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 33).addBox(-6.0F, -4.0F, -10.0F, 1.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-1.0F, -1.0F, -12.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 5.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Type entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = ageInTicks - entity.tickCount;
		float r = -(float) Math.sin(entity.getJumpCompletion(f) * (float) Math.PI);
		this.mouth.setRotation(r, 0.0F, 0.0F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, buffer, packedLight, packedOverlay);
	}
}