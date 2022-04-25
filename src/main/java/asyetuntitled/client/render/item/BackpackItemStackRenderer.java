package asyetuntitled.client.render.item;

import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import asyetuntitled.common.item.ItemBackpack;
import asyetuntitled.common.item.ItemsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.EmptyModelData;

public class BackpackItemStackRenderer extends BlockEntityWithoutLevelRenderer {

	public BackpackItemStackRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
		super(blockEntityRenderDispatcher, entityModelSet);
	}

	@Override
	public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		//ItemRenderer.render does transformations that would need to be transformed against in complicated way so rather pop the pose here and push the new one with the same transforms
		// applied in the correct order with the getModel
		if(stack.getItem() == ItemsRegistry.BACKPACK_BASE.get())
		{
			Minecraft mc = Minecraft.getInstance();
			ItemRenderer renderer = mc.getItemRenderer();
			Random random = new Random();
			random.setSeed(42L);

			int c = ((ItemBackpack) stack.getItem()).getColor(stack);
			matrixStack.popPose();
			matrixStack.pushPose();
			BakedModel model = renderer.getModel(stack, null, mc.player, 0);
			
			boolean leftHand = mc.player != null && mc.player.getOffhandItem() == stack;
			model = ForgeHooksClient.handleCameraTransforms(matrixStack, model, transformType, leftHand);
			RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, true);
			VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(buffer, rendertype, true, stack.hasFoil());
//			itemRenderer.renderModelLists(model, stack, combinedLight, combinedOverlay, matrixStack, ivertexbuilder);
			
			NonNullList<ItemStack> parts = ((ItemBackpack) stack.getItem()).getParts(stack);
//			matrixStack.mulPose(Vector3f.ZP.rotationDegrees(itemDisplayRenderInfo.getRotation()));

			int a = parts.size();
			if(a > 6) a = 6;
//			matrixStack.translate(-0.1D * a, -0.1D * a, -0.1D * a);
			matrixStack.translate(-(0.3D + a * 0.05D), -(0.3D + a * 0.05D), -(0.3D + a * 0.05D));
			
			for(ItemStack part : parts)
			{
				if(!part.isEmpty())
				{
					if(part.getItem() == Items.DIAMOND_CHESTPLATE)
					{
						float f = 1.2F;
						matrixStack.scale(f, f, f);
						float x = 0.3F;
						float y = 0.3F;
						float z = 0.8F;
						matrixStack.translate(x, y, z);
						renderer.renderStatic(null, part, transformType, leftHand, matrixStack, buffer, mc.level, combinedLight, OverlayTexture.NO_OVERLAY, 0);
						matrixStack.translate(-x, -y, -z);
						f = 1.0F / f;
						matrixStack.scale(f, f, f);

					}
					else
					{
						float scale = 0.9F;
						matrixStack.scale(scale, scale, scale);
						int colour = c;
						if(part.getItem().getRegistryName().getPath().contains("button")) colour = 0xFFFFFF;
						 BakedModel bakedmodel = renderer.getModel(part, null, null, 0);
						 this.renderQuadList(matrixStack, ivertexbuilder, bakedmodel.getQuads((BlockState)null, 
								 (Direction)null, random, EmptyModelData.INSTANCE), stack, combinedLight, combinedOverlay, colour);
						 scale = 1.0F / scale;
						 matrixStack.scale(scale, scale, scale);
					}

				}
			}
		}
	}
	
   public void renderQuadList(PoseStack poseStack, VertexConsumer vertex, List<BakedQuad> quads, ItemStack p_115166_, int combinedLight, int combinedOverlay, int colour) {
	   PoseStack.Pose posestack$pose = poseStack.last();
	   for(BakedQuad bakedquad : quads) {
		   float f = (float)(colour >> 16 & 255) / 255.0F;
		   float f1 = (float)(colour >> 8 & 255) / 255.0F;
		   float f2 = (float)(colour & 255) / 255.0F;
		   vertex.putBulkData(posestack$pose, bakedquad, f, f1, f2, combinedLight, combinedOverlay, true);
	   }
	   
   }
}