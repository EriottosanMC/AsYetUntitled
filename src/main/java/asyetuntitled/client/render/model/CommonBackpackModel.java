package asyetuntitled.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import asyetuntitled.client.util.ClientResourceLocations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CommonBackpackModel 
{
	
	private static final String MAIN = "main";
	private static final String FRONT= "front";
	private static final String TOP = "top";
	private static final String STRAP1 = "strap1";
	private static final String STRAP2 = "strap2";
	private static final String STRAP1TOP = "strap1top";
	private static final String STRAP2TOP = "strap2top";
	private static final String BUTTONMAIN1 = "buttonmain1";
	private static final String BUTTONMAIN2 = "buttonmain2";
	private static final String BUTTONFRONT = "buttonfront";
	private static final String BUTTONTOP = "buttontop";

	public static Pair<ImmutableList<ModelPart>, ImmutableList<ModelPart>> setModelParts(ModelPart part) 
	{
		return Pair.of(ImmutableList.of(part.getChild(MAIN), part.getChild(FRONT), part.getChild(TOP), part.getChild(STRAP1), part.getChild(STRAP2), part.getChild(STRAP1TOP), part.getChild(STRAP2TOP)),
				ImmutableList.of(part.getChild(BUTTONMAIN1), part.getChild(BUTTONMAIN2), part.getChild(BUTTONFRONT), part.getChild(BUTTONTOP)));
	}

	public static LayerDefinition createBodyLayer(boolean isWorn) 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partDefinition = meshdefinition.getRoot();
		partDefinition.addOrReplaceChild(MAIN, CubeListBuilder.create()
						.texOffs(0, 0).addBox(-6.0F, 13.0F, 2.0F, 12.0F, 11.0F, 6.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild(FRONT, CubeListBuilder.create()
				.texOffs(0, 17).addBox(-5.0F, 13.0F, 8.0F, 10.0F, 7.0F, 3.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild(TOP, CubeListBuilder.create()
				.texOffs(26, 17).addBox(-5.0F, 24.0F, 2.0F, 10.0F, 3.0F, 5.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		
		float strapOffset = isWorn ? -3.0F : 1.0F;
		partDefinition.addOrReplaceChild(STRAP1, CubeListBuilder.create()
				.texOffs(36, 0).addBox(3.0F, 15.0F, strapOffset, 2.0F, 9.0F, 1.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild(STRAP2, CubeListBuilder.create()
				.texOffs(42, 0).addBox(-5.0F, 15.0F, strapOffset, 2.0F, 9.0F, 1.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		
		float strapTopX = isWorn ? 2.0F : 0.0F;
		float strapTopY = isWorn ? 1.0F : 0.0F;
		float strapTopZ = isWorn ? 5.0F : 0.0F;
		partDefinition.addOrReplaceChild(STRAP1TOP, CubeListBuilder.create()
				.texOffs(48, 0).addBox(3.0F, 24.0F, -3.0F, strapTopX, strapTopY, strapTopZ, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild(STRAP2TOP, CubeListBuilder.create()
				.texOffs(48, 6).addBox(-5.0F, 24.0F, -3.0F, strapTopX, strapTopY, strapTopZ, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		
		partDefinition.addOrReplaceChild(BUTTONMAIN1, CubeListBuilder.create()
				.texOffs(30, 0).addBox(-4.0F, 20.5F, 7.5F, 1.0F, 2.0F, 1.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild(BUTTONMAIN2, CubeListBuilder.create()
				.texOffs(30, 3).addBox(3.0F, 20.5F, 7.5F, 1.0F, 2.0F, 1.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild(BUTTONFRONT, CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1.0F, 16.0F, 10.5F, 2.0F, 2.0F, 1.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		partDefinition.addOrReplaceChild(BUTTONTOP, CubeListBuilder.create()
				.texOffs(0, 3).addBox(-1.0F, 25.5F, 6.5F, 2.0F, 1.0F, 1.0F, true)
				, PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}
	
	public static void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int color, ImmutableList<ModelPart> backpack, ImmutableList<ModelPart> buttons)
	{
		VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(ClientResourceLocations.BACKPACK_TEXTURE));

		float r = (float)(color >> 16 & 255) / 255.0F;
		float g = (float)(color >> 8 & 255) / 255.0F;
		float b = (float)(color & 255) / 255.0F;
		backpack.forEach((part) -> {
	         part.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
	      });
		buttons.forEach((part) -> {
	         part.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		});
	}
	
	
}