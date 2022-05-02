package asyetuntitled.client.event;

import java.util.List;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import asyetuntitled.common.messages.ClientThoughtsData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;

public class ThoughtsRenderer
{

    public static void render(Window window, PoseStack stack)
    {
        if(ClientThoughtsData.displayTime > 0)
        {
            Minecraft mc = Minecraft.getInstance();
            List<TranslatableComponent> messages = ClientThoughtsData.getMessageSplit();
            float f = ClientThoughtsData.displayTime > 10 ? 1.0F : ClientThoughtsData.displayTime / 10F;
          
            for(int i = 0 ; i < messages.size() ; i++)
            {
                TranslatableComponent message = messages.get(i);
                float width = mc.font.width(message.getVisualOrderText()) / 2;
                int mid = window.getGuiScaledWidth() / 2;
                int alpha = Math.round(f * 255);
                
                stack.pushPose();
                stack.translate(0.0D, 0.0D, 50.0D);
                RenderSystem.enableBlend();
                mc.font.drawShadow(stack, message, mid - width, 40 + 10*i, 16777215 + (alpha << 24));
                RenderSystem.disableBlend();
                stack.popPose();    
            }
            ClientThoughtsData.displayTime--;
        }
    }

}
