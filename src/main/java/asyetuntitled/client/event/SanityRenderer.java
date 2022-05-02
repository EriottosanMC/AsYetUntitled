package asyetuntitled.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import asyetuntitled.client.sanity.ClientSanityData;
import asyetuntitled.client.util.ClientResourceLocations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class SanityRenderer
{

    public static void render(Window window)
    {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if(!player.isSpectator())
        {
            float playerSanity = ClientSanityData.getPlayerSanity();
            int size = 7;
            int i = window.getGuiScaledWidth() / 2;
            int j = window.getGuiScaledHeight() / 2;
            double j2 = window.getGuiScaledHeight() - 43;
            if(player.isCreative()) j2 += 13;
            double d1 = i + size;
            double d2 = j2 + size;
            double d3 = i - size;
            double d4 = j2 - size;
            
            RenderSystem.enableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            
            
            //Renders zombie sanity
            render(d1, d2, d3, d4, ClientResourceLocations.MIDSANE, 1.0F, 1.0F, 1.0F, 1.0F, false);
            
            //Determines if you are more sane or insane and assigns a texture
            float sanityModifier = 2 * (playerSanity - 0.5F);
            ResourceLocation tex;
            if(sanityModifier > 0) tex = ClientResourceLocations.SANE;
            else 
            {
                tex = ClientResourceLocations.INSANE;
                sanityModifier *= -1;
            }
        
            //Renders your sanity face
            render(d1, d2, d3, d4, tex, 1.0F, 1.0F, 1.0F, sanityModifier, false);
            
            //Renders arrows if you are increasing/decreasing in sanity
            float arrowStrength = ClientSanityData.getArrowStrength();
            if(arrowStrength > 0F)
            {
                size = 3;
                i += 6;
                float r;
                float g;
                float b;
                tex = ClientResourceLocations.ARROW;
                if(ClientSanityData.isIncreasing())
                {
                   r = 1.0F;
                   g = 0.415F;
                   b = 0.0F;
                   j2 -= 4;
                }
                else
                {
                    r = 0.0F;
                    g = 0.58F;
                    b = 1.0F;
                    j2 += 4;
                }
                
                render(i + size, j2 + size, i - size, j2 - size, tex, r, g, b, arrowStrength, true);
            }
            
            //Renders your insanity halo if you are insane
            if(playerSanity <= 0.5F)
            {
                sanityModifier = 1.0F - 2 * playerSanity;
                i = window.getGuiScaledWidth();
                j = window.getGuiScaledHeight();
                double d0 = Mth.lerp((double)sanityModifier, 2.25D, 1.3D);
                float f1 = 0.2F * sanityModifier;
                float f = 0.6F * sanityModifier;
                float f2 = 0.2F * sanityModifier;
                
                d1 = (double)i * d0;
                d2 = (double)j * d0;
                d3 = ((double)i - d1) / 2.0D;
                d4 = ((double)j - d2) / 2.0D;
                d1 += d3;
                d2 += d4;
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
                
                
                render(d1, d2, d3, d4, ClientResourceLocations.INSANITY_OVERLAY, f, f1, f2, 1.0F, false);
            }
            
            //Returns things to normal so we don't mess other stuff up
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }   
    }

    private static void render(double d1, double d2, double d3, double d4, ResourceLocation tex, float r, float g, float b, float alpha, boolean flip)
    {
        float v1 = flip ? 0.0F : 1.0F;
        float v0 = flip ? 1.0F : 0.0F;
        RenderSystem.setShaderTexture(0, tex);
        RenderSystem.setShaderColor(r, g, b, alpha);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(d3, d2, -90.0D).uv(0.0F, v1).endVertex();
        bufferbuilder.vertex(d1, d2, -90.0D).uv(1.0F, v1).endVertex();
        bufferbuilder.vertex(d1, d4, -90.0D).uv(1.0F, v0).endVertex();
        bufferbuilder.vertex(d3, d4, -90.0D).uv(0.0F, v0).endVertex();
        tesselator.end();
    }
}
