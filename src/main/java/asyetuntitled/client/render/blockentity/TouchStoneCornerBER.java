package asyetuntitled.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.block.CornerBlock;
import asyetuntitled.common.block.TouchStoneCornerBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class TouchStoneCornerBER implements BlockEntityRenderer<TouchStoneCornerBE>
{
    
    public TouchStoneCornerBER(BlockEntityRendererProvider.Context context)
    {
    }

    @Override
    public void render(TouchStoneCornerBE corner, float partialTicks, PoseStack stack, MultiBufferSource buffer,
            int combinedOverlay, int packedLight) 
    {
        Minecraft mc = Minecraft.getInstance();
        TextureAtlasSprite topTex = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(AsYetUntitled.MODID, "block/touchstone"));
        TextureAtlasSprite sideTex = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(AsYetUntitled.MODID, "block/touchstone_side"));
        VertexConsumer builder = buffer.getBuffer(RenderType.solid());
        
        stack.pushPose();
        Direction direction = corner.getBlockState().getValue(CornerBlock.FACING);
        if(direction.equals(Direction.NORTH))
        {
            stack.mulPose(Vector3f.YN.rotationDegrees(90));
            stack.translate(0, 0, -1);
        }
        else if(direction.equals(Direction.EAST))
        {
            stack.mulPose(Vector3f.YN.rotationDegrees(180));
            stack.translate(-1, 0, -1);
        }
        else if(direction.equals(Direction.SOUTH))
        {
            stack.mulPose(Vector3f.YP.rotationDegrees(90));
            stack.translate(-1, 0, 0);
        }
        
        add(builder, stack, 0, 0, 0, topTex.getU0(), topTex.getV0(), packedLight);
        add(builder, stack, 0, 0, 0, topTex.getU0(), topTex.getV0(), packedLight);
        add(builder, stack, 1, 0, 1, topTex.getU1(), topTex.getV1(), packedLight);
        add(builder, stack, 0, 0, 1, topTex.getU0(), topTex.getV1(), packedLight);
        
        add(builder, stack, 0, 0.5F, 1, topTex.getU0(), topTex.getV1(), packedLight);
        add(builder, stack, 1, 0.5F, 1, topTex.getU1(), topTex.getV1(), packedLight);
        add(builder, stack, 0, 0.5F, 0, topTex.getU0(), topTex.getV0(), packedLight);
        add(builder, stack, 0, 0.5F, 0, topTex.getU0(), topTex.getV0(), packedLight);
        
        add(builder, stack, 0, 0, 1, sideTex.getU0(), sideTex.getV0(), packedLight);
        add(builder, stack, 1, 0, 1, sideTex.getU1(), sideTex.getV0(), packedLight);
        add(builder, stack, 1, 0.5F, 1, sideTex.getU1(), sideTex.getV1(), packedLight);
        add(builder, stack, 0, 0.5F, 1, sideTex.getU0(), sideTex.getV1(), packedLight);
        
        add(builder, stack, 0, 0, 0, sideTex.getU0(), sideTex.getV0(), packedLight);
        add(builder, stack, 0, 0, 1, sideTex.getU1(), sideTex.getV0(), packedLight);
        add(builder, stack, 0, 0.5F, 1, sideTex.getU1(), sideTex.getV1(), packedLight);
        add(builder, stack, 0, 0.5F, 0, sideTex.getU0(), sideTex.getV1(), packedLight);
      
        stack.popPose();
        stack.pushPose();
        if(direction.equals(Direction.NORTH))
        {
            stack.mulPose(Vector3f.YN.rotationDegrees(90));
            stack.translate(0, 0, -1);
        }
        else if(direction.equals(Direction.EAST))
        {
            stack.mulPose(Vector3f.YN.rotationDegrees(180));
            stack.translate(-1, 0, -1);
        }
        else if(direction.equals(Direction.SOUTH))
        {
            stack.mulPose(Vector3f.YP.rotationDegrees(90));
            stack.translate(-1, 0, 0);
        }
        stack.mulPose(Vector3f.YN.rotationDegrees(45));
        stack.scale((float) Math.sqrt(2.0D), 1F, 1F);
        add(builder, stack, 0 , 0.5F , 0, sideTex.getU0(), sideTex.getV(8), packedLight);
        add(builder, stack, 1 , 0.5F , 0, sideTex.getU1(), sideTex.getV(8), packedLight);
        add(builder, stack, 1, 0, 0, sideTex.getU1(), sideTex.getV1(), packedLight);
        add(builder, stack, 0, 0, 0, sideTex.getU0(), sideTex.getV1(), packedLight);

        stack.popPose();
    }

    private void add(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, int packedLight) {
        renderer.vertex(stack.last().pose(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728640)
                .normal(1, 0, 0)
                .endVertex();
    }
}
