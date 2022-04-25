package asyetuntitled.client.render.model;

import asyetuntitled.common.entity.ShadowSpider;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ShadowSpiderModel extends HierarchicalModel<ShadowSpider>
{
	
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightMiddleHindLeg;
	private final ModelPart leftMiddleHindLeg;
	private final ModelPart rightMiddleFrontLeg;
	private final ModelPart leftMiddleFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	
	public ShadowSpiderModel(ModelPart part) 
	{
		this.root = part;
		this.head = part.getChild("head");
		this.rightHindLeg = part.getChild("right_hind_leg");
		this.leftHindLeg = part.getChild("left_hind_leg");
		this.rightMiddleHindLeg = part.getChild("right_middle_hind_leg");
		this.leftMiddleHindLeg = part.getChild("left_middle_hind_leg");
		this.rightMiddleFrontLeg = part.getChild("right_middle_front_leg");
		this.leftMiddleFrontLeg = part.getChild("left_middle_front_leg");
		this.rightFrontLeg = part.getChild("right_front_leg");
		this.leftFrontLeg = part.getChild("left_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 4).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 15.0F, -3.0F));
		partdefinition.addOrReplaceChild("body0", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 15.0F, 0.0F));
		partdefinition.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F), PartPose.offset(0.0F, 15.0F, 9.0F));
		CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
		CubeListBuilder cubelistbuilder1 = CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
		partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-4.0F, 15.0F, 2.0F));
		partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder1, PartPose.offset(4.0F, 15.0F, 2.0F));
		partdefinition.addOrReplaceChild("right_middle_hind_leg", cubelistbuilder, PartPose.offset(-4.0F, 15.0F, 1.0F));
		partdefinition.addOrReplaceChild("left_middle_hind_leg", cubelistbuilder1, PartPose.offset(4.0F, 15.0F, 1.0F));
		partdefinition.addOrReplaceChild("right_middle_front_leg", cubelistbuilder, PartPose.offset(-4.0F, 15.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_middle_front_leg", cubelistbuilder1, PartPose.offset(4.0F, 15.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-4.0F, 15.0F, -1.0F));
		partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder1, PartPose.offset(4.0F, 15.0F, -1.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}
	   
	@Override
	public ModelPart root()
	{
		return this.root;
	}	
	
	@Override
	public void setupAnim(ShadowSpider p_103866_, float p_103867_, float p_103868_, float p_103869_, float p_103870_, float p_103871_) {
		this.head.yRot = p_103870_ * ((float)Math.PI / 180F);
		this.head.xRot = p_103871_ * ((float)Math.PI / 180F);
		this.rightHindLeg.zRot = (-(float)Math.PI / 4F);
		this.leftHindLeg.zRot = ((float)Math.PI / 4F);
		this.rightMiddleHindLeg.zRot = -0.58119464F;
		this.leftMiddleHindLeg.zRot = 0.58119464F;
		this.rightMiddleFrontLeg.zRot = -0.58119464F;
		this.leftMiddleFrontLeg.zRot = 0.58119464F;
		this.rightFrontLeg.zRot = (-(float)Math.PI / 4F);
		this.leftFrontLeg.zRot = ((float)Math.PI / 4F);
		this.rightHindLeg.yRot = ((float)Math.PI / 4F);
		this.leftHindLeg.yRot = (-(float)Math.PI / 4F);
		this.rightMiddleHindLeg.yRot = ((float)Math.PI / 8F);
		this.leftMiddleHindLeg.yRot = (-(float)Math.PI / 8F);
		this.rightMiddleFrontLeg.yRot = (-(float)Math.PI / 8F);
		this.leftMiddleFrontLeg.yRot = ((float)Math.PI / 8F);
		this.rightFrontLeg.yRot = (-(float)Math.PI / 4F);
		this.leftFrontLeg.yRot = ((float)Math.PI / 4F);
		float f3 = -(Mth.cos(p_103867_ * 0.6662F * 2.0F + 0.0F) * 0.4F) * p_103868_;
		float f4 = -(Mth.cos(p_103867_ * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * p_103868_;
		float f5 = -(Mth.cos(p_103867_ * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * p_103868_;
		float f6 = -(Mth.cos(p_103867_ * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * p_103868_;
		float f7 = Math.abs(Mth.sin(p_103867_ * 0.6662F + 0.0F) * 0.4F) * p_103868_;
		float f8 = Math.abs(Mth.sin(p_103867_ * 0.6662F + (float)Math.PI) * 0.4F) * p_103868_;
		float f9 = Math.abs(Mth.sin(p_103867_ * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * p_103868_;
		float f10 = Math.abs(Mth.sin(p_103867_ * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * p_103868_;
		this.rightHindLeg.yRot += f3;
		this.leftHindLeg.yRot += -f3;
		this.rightMiddleHindLeg.yRot += f4;
		this.leftMiddleHindLeg.yRot += -f4;
		this.rightMiddleFrontLeg.yRot += f5;
		this.leftMiddleFrontLeg.yRot += -f5;
		this.rightFrontLeg.yRot += f6;
		this.leftFrontLeg.yRot += -f6;
		this.rightHindLeg.zRot += f7;
		this.leftHindLeg.zRot += -f7;
		this.rightMiddleHindLeg.zRot += f8;
		this.leftMiddleHindLeg.zRot += -f8;
		this.rightMiddleFrontLeg.zRot += f9;
		this.leftMiddleFrontLeg.zRot += -f9;
		this.rightFrontLeg.zRot += f10;
		this.leftFrontLeg.zRot += -f10;
	}	
}