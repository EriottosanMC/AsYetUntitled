package asyetuntitled.client.render.entity;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.client.render.model.BackpackModel;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.entity.backpack.EntityBackpack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BackpackRenderer extends EntityRenderer<EntityBackpack>
{
	private final Map<EntityBackpack.Type, Pair<ResourceLocation, BackpackModel>> backpackResources;

	
	public BackpackRenderer(Context context)
	{
		super(context);
		this.backpackResources = Stream.of(EntityBackpack.Type.values()).collect(ImmutableMap.toImmutableMap((p_173938_) -> {
			return p_173938_;
		}, (p_173941_) -> {
			return Pair.of(new ResourceLocation(AsYetUntitled.MODID, "textures/entities/backpack_" + p_173941_.getName() + ".png"), new BackpackModel(context.bakeLayer(ClientResourceLocations.BACKPACK_MODEL_LAYER)));
		}));
	}
	

	@Override
	public ResourceLocation getTextureLocation(EntityBackpack entity) {
		return ClientResourceLocations.BLANK_TEXTURE;
	}

	@Override
	public void render(EntityBackpack backpack, float yRot, float p_113931_, PoseStack stack, MultiBufferSource buffer, int packedLight) {
		stack.pushPose();
		stack.mulPose(Vector3f.YP.rotationDegrees(270-yRot));
		stack.translate(-0.3D, -0.825D, 0.0D);

		float f = (float)backpack.getHurtTime() - p_113931_;
		float f1 = backpack.getDamage() - p_113931_;
		if (f1 < 0.0F) 	f1 = 0.0F;

		if (f > 0.0F) stack.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)backpack.getHurtDir()));

		Pair<ResourceLocation, BackpackModel> pair = getModelWithLocation(backpack);
		BackpackModel backpackmodel = pair.getSecond();
		
		stack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		backpackmodel.setupAnim(backpack, p_113931_, 0.0F, -0.1F, 0.0F, 0.0F);
		
		int color = backpack.getColor();
		backpackmodel.render(stack, buffer, packedLight, color);

		stack.popPose();
		super.render(backpack, yRot, p_113931_, stack, buffer, packedLight);
	}	

	 public Pair<ResourceLocation, BackpackModel> getModelWithLocation(EntityBackpack backpack) { return this.backpackResources.get(backpack.getBackpackType()); }
}
