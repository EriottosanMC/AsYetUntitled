package asyetuntitled.common.messages;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;

public class ClientThoughtsData
{
    private static TranslatableComponent message = new TranslatableComponent("");
    public static int displayTime = 0;
    
    public static void changeThought(TranslatableComponent newMessage)
    {
        ClientThoughtsData.message = newMessage;
        displayTime = 170;
    }
    
    public static List<TranslatableComponent> getMessageSplit()
    {
        ImmutableList.Builder<TranslatableComponent> builder = ImmutableList.builder();
        String content = message.getString();
        Style style = message.getStyle();
        Pair<String, Style> pair = new Pair<String, Style>(content, style);
        pair = applyFormatIfSpecified(pair, "{b}", ChatFormatting.BOLD);
        pair = applyFormatIfSpecified(pair, "{i}", ChatFormatting.ITALIC);
        pair = applyFormatIfSpecified(pair, "{u}", ChatFormatting.UNDERLINE);
        pair = applyFormatIfSpecified(pair, "{st}", ChatFormatting.STRIKETHROUGH);
        pair = applyFormatIfSpecified(pair, "{ob}", ChatFormatting.OBFUSCATED);
        
        content = pair.getFirst();
        style = pair.getSecond();
        int i = content.indexOf("/");

        while(i > 0)
        {
            builder.add((TranslatableComponent) new TranslatableComponent(content.substring(0, i)).setStyle(style));
            content = content.substring(i + 1);
            i = content.indexOf("/");
        }
        
        builder.add((TranslatableComponent) new TranslatableComponent(content).setStyle(style));
        
        return builder.build();
    }

    private static Pair<String, Style> applyFormatIfSpecified(Pair<String, Style> pair, String prefix, ChatFormatting format)
    {
        String content = pair.getFirst();
        Style style = pair.getSecond();
        if(content.startsWith(prefix))
        {
            content = content.substring(prefix.length());
            style = style.applyFormat(format);
        }
        
        return new Pair<String, Style>(content, style);
    }

}
