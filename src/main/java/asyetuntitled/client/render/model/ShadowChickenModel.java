package asyetuntitled.client.render.model;


import com.google.common.collect.ImmutableList;

import asyetuntitled.common.entity.ShadowChicken;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

// Made with Blockbench 4.2.1
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class ShadowChickenModel extends HierarchicalModel<ShadowChicken> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart leg0;
	private final ModelPart leg1;
	private final ModelPart wing0;
	private final ModelPart wing1;

	public ShadowChickenModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.leg0 = root.getChild("leg0");
		this.leg1 = root.getChild("leg1");
		this.wing0 = root.getChild("wing0");
		this.wing1 = root.getChild("wing1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, 1.0F, 10.0F, 10.0F, 8.0F, 
										new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 14).addBox(-3.0F, -15.0F, -3.0F, 6.0F, 8.0F, 4.0F, 
										new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, -4.0F));
		partdefinition.getChild("head").addOrReplaceChild("comb", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -10.0F, -4.0F, 2.0F, 3.0F, 2.0F, 
								new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.getChild("head").addOrReplaceChild("beak", CubeListBuilder.create().texOffs(10, 18).addBox(-2.0F, -12.0F, -5.0F, 4.0F, 2.0F, 2.0F, 
								new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(12, 33).addBox(-1.0F, -4.0F, -3.0F, 3.0F, 9.0F, 3.0F,
										new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 19.0F, 1.0F));
		partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, -4.0F, -3.0F, 3.0F, 9.0F, 3.0F, 
										new CubeDeformation(0.0F)), PartPose.offset(1.0F, 19.0F, 1.0F));
		partdefinition.addOrReplaceChild("wing0", CubeListBuilder.create().texOffs(18, 18).addBox(0.0F, -1.0F, -4.0F, 1.0F, 7.0F, 8.0F), PartPose.offset(-6.0F, 8.0F, 0.0F));
		partdefinition.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.0F, -4.0F, 1.0F, 7.0F, 8.0F), PartPose.offset(6.0F, 8.0F, 0.0F));
	 	    
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(ShadowChicken entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
//		this.beak.xRot = this.head.xRot;
//		this.beak.yRot = this.head.yRot;
//		this.redThing.xRot = this.head.xRot;
//		this.redThing.yRot = this.head.yRot;
		this.leg0.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg1.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.wing0.zRot = ageInTicks;
		this.wing1.zRot = -ageInTicks;
	}

	@Override
	public ModelPart root() 
	{
		return this.root;
	}
	
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.root, this.leg0, this.leg1, this.wing0, this.wing1);
	}
}