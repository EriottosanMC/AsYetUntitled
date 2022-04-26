package asyetuntitled.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.block.TouchStoneCornerBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class TouchStoneCornerBER implements BlockEntityRenderer<TouchStoneCornerBE>
{
    private final BlockEntityRendererProvider.Context context;
    
    public TouchStoneCornerBER(BlockEntityRendererProvider.Context context)
    {
        this.context = context;
    }

    @Override
    public void render(TouchStoneCornerBE touchstone, float partialTicks, PoseStack stack, MultiBufferSource buffer,
            int combinedOverlay, int packedLight) 
    {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ClientResourceLocations.DARKBLOCK);
        VertexConsumer builder = buffer.getBuffer(RenderType.solid());
        
        stack.pushPose();
        
        
        add(builder, stack, 0, 2 , 0, sprite.getU0(), sprite.getV0());
        add(builder, stack, 0, 2, 0, sprite.getU0(), sprite.getV0());
        add(builder, stack, 1 , 2 , 1, sprite.getU1(), sprite.getV1());
        add(builder, stack, 0 , 2 , 1, sprite.getU0(), sprite.getV1());
        
        add(builder, stack, 0, 2.5F , 1, sprite.getU0(), sprite.getV1());
        add(builder, stack, 1, 2.5F, 1, sprite.getU1(), sprite.getV1());
        add(builder, stack, 0 , 2.5F , 0, sprite.getU0(), sprite.getV0());
        add(builder, stack, 0 , 2.5F , 0, sprite.getU0(), sprite.getV0());
        
        
        add(builder, stack, 0, 2 , 1, sprite.getU0(), sprite.getV0());
        add(builder, stack, 1, 2, 1, sprite.getU1(), sprite.getV0());
        add(builder, stack, 1 , 2.5F , 1, sprite.getU1(), sprite.getV1());
        add(builder, stack, 0 , 2.5F , 1, sprite.getU0(), sprite.getV1());
        
        add(builder, stack, 0, 2 , 0, sprite.getU0(), sprite.getV0());
        add(builder, stack, 0, 2, 1, sprite.getU1(), sprite.getV0());
        add(builder, stack, 0 , 2.5F , 1, sprite.getU1(), sprite.getV1());
        add(builder, stack, 0 , 2.5F , 0, sprite.getU0(), sprite.getV1());
      
        stack.popPose();
        stack.pushPose();
        stack.mulPose(Vector3f.YN.rotationDegrees(45));
        stack.scale((float) Math.sqrt(2.0D), 1F, 1F);
        add(builder, stack, 0 , 2.5F , 0, sprite.getU0(), sprite.getV1());
        add(builder, stack, 1 , 2.5F , 0, sprite.getU1(), sprite.getV1());
        add(builder, stack, 1, 2 , 0, sprite.getU1(), sprite.getV0());
        add(builder, stack, 0, 2, 0, sprite.getU0(), sprite.getV0());

        stack.popPose();
    }

    private void add(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v) {
        renderer.vertex(stack.last().pose(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(1, 0, 0)
                .endVertex();
    }
}
