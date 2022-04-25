package asyetuntitled.client.render.entity.layer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import asyetuntitled.common.entity.ShadowCreature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;

public class ShadowCreatureLayer <T extends PathfinderMob> extends RenderLayer<T, HierarchicalModel<T>> {
	
	private final EntityModel<T> model;
	private ResourceLocation location;

	public ShadowCreatureLayer(RenderLayerParent<T, HierarchicalModel<T>> parent, HierarchicalModel<T> model, ResourceLocation loc) {
		super(parent);
		this.model = model;
		location = loc;
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T mob, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		
		if(mob instanceof ShadowCreature creature && creature.canPlayerSee(player))
		{
			float strength = creature.getSanityByThresholdInverted(player);
			LightTexture lt = mc.gameRenderer.lightTexture();
			VertexConsumer vc = bufferSource.getBuffer(RenderType.entityTranslucent(location, false));
		      
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(mob, limbSwing, limbSwingAmount, partialTicks);
			this.model.setupAnim(mob, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);	      
		     
			lt.turnOnLightLayer();
//				RenderSystem.disableCull();
//				RenderSystem.enableBlend();
//				RenderSystem.defaultBlendFunc();
//				RenderSystem.enableDepthTest();
////				RenderSystem.depthMask(false);
			
			RenderSystem.setShader(GameRenderer::getRendertypeEntityTranslucentShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			this.model.renderToBuffer(poseStack, vc, packedLight, LivingEntityRenderer.getOverlayCoords(mob, 0.0F), 1.0F, 1.0F, 1.0F, strength);
			
//				RenderSystem.disableBlend();
//				RenderSystem.enableCull();    
			lt.turnOffLightLayer();
		}
	}
}