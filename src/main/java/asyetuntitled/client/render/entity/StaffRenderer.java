package asyetuntitled.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import asyetuntitled.client.render.model.StaffModel;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.entity.livingchest.Staff;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class StaffRenderer extends EntityRenderer<Staff>{

	private StaffModel<Staff> model;

	public StaffRenderer(Context context) 
	{
		super(context);
		this.model = new StaffModel<Staff>(context.bakeLayer(ClientResourceLocations.STAFF_MODEL_LAYER));
	}

	@Override
	public ResourceLocation getTextureLocation(Staff staff) 
	{
		if(staff.getHasChest())
		{
			return ClientResourceLocations.BLANK_TEXTURE;
		}
		else
		{
			return ClientResourceLocations.STAFF_TEXTURE;
		}
	}

	@Override
	public void render(Staff staff, float p_113930_, float p_113931_, PoseStack pose, MultiBufferSource buffer, int p_113934_) {
		
		pose.pushPose();
//		pose.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_113930_));
		pose.translate(0.0D, 0.6D, 0.0D);
		
		ResourceLocation resourcelocation = this.getTextureLocation(staff);
		StaffModel<Staff> model = this.model;
		
		pose.scale(1.0F, -1.0F, 1.0F);
		pose.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		model.setupAnim(staff, p_113931_, 0.0F, -0.1F, 0.0F, 0.0F);
//		int i = staff.getColor();
//		float r = (float)(i >> 16 & 255) / 255.0F;
//		float g = (float)(i >> 8 & 255) / 255.0F;
//		float b = (float)(i & 255) / 255.0F;
		VertexConsumer vertexconsumer = buffer.getBuffer(model.renderType(resourcelocation));
		model.renderToBuffer(pose, vertexconsumer, p_113934_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		pose.popPose();
		super.render(staff, p_113930_, p_113931_, pose, buffer, p_113934_);
	}	

}
