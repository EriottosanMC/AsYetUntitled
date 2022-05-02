package asyetuntitled.client.render.item;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import asyetuntitled.client.screen.ClientCustomBundleTooltip;
import asyetuntitled.client.util.ClientResourceLocations;
import asyetuntitled.common.item.CustomBundleTooltip;
import asyetuntitled.common.item.ItemBackpack;
import asyetuntitled.common.item.ItemsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemRenderer
{

    public static void registerAll()
    {
        registerItemProperties();
        registerTooltips();
    }
    public static void registerItemProperties()
    {
        ItemProperties.register(ItemsRegistry.DUMMY_RUNE.get(), ClientResourceLocations.ALPHABET, (stack, level, living, id) -> {
            CompoundTag tag = stack.getOrCreateTag();
            float f = 0.0F;
            if(tag.contains("alphabet"))
            {
                f = tag.getFloat("alphabet");
            }
            return f;
        });        
    }
    
    public static void registerTooltips()
    {
        MinecraftForgeClient.registerTooltipComponentFactory(CustomBundleTooltip.class, ClientCustomBundleTooltip::new);
    }
    
    public static void renderDamage(InventoryScreen screen)
    {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if(chest.getItem() instanceof ItemBackpack backpack)
        {
            List<ItemStack> parts = backpack.getParts(chest);
            for(ItemStack part : parts)
            {
                if(part.isBarVisible())
                {
                    RenderSystem.disableDepthTest();
                    RenderSystem.disableTexture();
                    RenderSystem.disableBlend();
                    Tesselator tesselator = Tesselator.getInstance();
                    BufferBuilder bufferbuilder = tesselator.getBuilder();
                    int i = part.getBarWidth();
                    int j = part.getBarColor();
                    int x = screen.getGuiLeft() + 10;
                    int y = screen.getGuiTop() + 40;
                    fillRect(bufferbuilder, x, y, 13, 2, 0, 0, 0, 255);
                    fillRect(bufferbuilder, x, y, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
                    RenderSystem.enableBlend();
                    RenderSystem.enableTexture();
                    RenderSystem.enableDepthTest();
                }
            }
        }        
    }
    
    private static void fillRect(BufferBuilder buffer, int d1, int d2, int d3, int d4, int r, int g, int b, int a) 
    {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex((double)(d1 + 0), (double)(d2 + 0), 0.0D).color(r, g, b, a).endVertex();
        buffer.vertex((double)(d1 + 0), (double)(d2 + d4), 0.0D).color(r, g, b, a).endVertex();
        buffer.vertex((double)(d1 + d3), (double)(d2 + d4), 0.0D).color(r, g, b, a).endVertex();
        buffer.vertex((double)(d1 + d3), (double)(d2 + 0), 0.0D).color(r, g, b, a).endVertex();
        buffer.end();
        BufferUploader.end(buffer);
   }

}
