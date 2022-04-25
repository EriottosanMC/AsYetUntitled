package asyetuntitled.client.render.entity.layer;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import asyetuntitled.client.render.model.WearableBackpackModel;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.item.ItemBackpack;
import asyetuntitled.common.item.ItemsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class BackpackLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> 
{
	private static WearableBackpackModel model;
	private static final Map<EntityType<?>, Vec3> entityTranslations;
	
	static {
		entityTranslations = new HashMap<>();
		entityTranslations.put(EntityType.ENDERMAN, new Vec3(0, -0.8, 0));
	}

	public BackpackLayer(RenderLayerParent<T, M> entityRendererIn) {
		super(entityRendererIn);
		if (model == null) {
			EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
			model = new WearableBackpackModel(entityModels.bakeLayer(ClientResourceLocations.WEARABLE_BACKPACK_MODEL_LAYER));
		}
	}
	
	@Override
	public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

		if (entity instanceof AbstractClientPlayer player) {
			matrixStack.pushPose();
			ItemStack backpack = player.getItemBySlot(EquipmentSlot.CHEST);
			renderBackpack(player, matrixStack, buffer, packedLight, backpack, false, model);
			matrixStack.popPose();
		} else {
			ItemStack chestStack = entity.getItemBySlot(EquipmentSlot.CHEST);
			if (chestStack.getItem() == ItemsRegistry.BACKPACK_BASE.get()) {
				renderBackpack(entity, matrixStack, buffer, packedLight, chestStack, false, model);

			}
		}
	}

	public static void renderBackpack(LivingEntity livingEntity, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, ItemStack backpack, boolean wearsArmor, WearableBackpackModel model) {
		
		float f = livingEntity.attackAnim;
		
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * -15.0F));
		float fz = Mth.sin(f * (float)Math.PI) * -3.0F;
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(fz));
		if (livingEntity.isCrouching()) {
			matrixStack.translate(0D, 0.2D, 0D);
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(90F / (float) Math.PI));
		}
	
		matrixStack.translate(0.0F, 1.55F, 0.0F);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180));
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
		
//		matrixStack.translate(0, yOffset, 0);

		if (livingEntity.isBaby()) {
			matrixStack.translate(0, -0.7F, -0.05F);
//			matrixStack.scale(CHILD_SCALE, CHILD_SCALE, CHILD_SCALE);
		}
		

		if (entityTranslations.containsKey(livingEntity.getType())) {
			Vec3 translVector = entityTranslations.get(livingEntity.getType());
			matrixStack.translate(translVector.x(), translVector.y(), translVector.z());
		}
		
		if(backpack.getItem() == ItemsRegistry.BACKPACK_BASE.get())
		{
			ItemBackpack bp = (ItemBackpack) backpack.getItem();
			int color = bp.getColor(backpack);
			model.render(matrixStack, buffer, packedLight, color);

		}
		
	}

//	private static void renderItemShown(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, RenderInfo renderInfo) {
//		BackpackRenderInfo.ItemDisplayRenderInfo itemDisplayRenderInfo = renderInfo.getItemDisplayRenderInfo();
//		if (!itemDisplayRenderInfo.getItem().isEmpty()) {
//			matrixStack.pushPose();
//			matrixStack.translate(0, 0.9, -0.25);
//			matrixStack.scale(0.5f, 0.5f, 0.5f);
//			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180f + itemDisplayRenderInfo.getRotation()));
//			Minecraft.getInstance().getItemRenderer().renderStatic(itemDisplayRenderInfo.getItem(), ItemTransforms.TransformType.FIXED, packedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer, 0);
//			matrixStack.popPose();
//		}
//	}

//	private static Vector3f getBackpackMiddleFacePoint(LivingEntity livingEntity, Vector3f vector) {
//		Vector3f point = vector.copy();
//		point.transform(Vector3f.XP.rotationDegrees(livingEntity.isCrouching() ? 25 : 0));
//		point.add(0, 0.8f, livingEntity.isCrouching() ? 0.9f : 0.7f);
//		point.transform(Vector3f.YN.rotationDegrees(livingEntity.yBodyRot - 180));
//		point.add(new Vector3f(livingEntity.position()));
//		return point;
//	}

}
