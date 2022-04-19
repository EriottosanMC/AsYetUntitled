package asyetuntitled.client.render.entity;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomRenderType extends RenderType {

	public CustomRenderType(String name, VertexFormat vertexFormat, VertexFormat.Mode mode, int bufferSize, 
			boolean affectsCrumbling, boolean sortOnUpload, Runnable setup, Runnable clear) 
	{
		super(name, vertexFormat, mode, bufferSize, affectsCrumbling, sortOnUpload, setup, clear);
	}

    private static CompositeState addState(ShaderStateShard shard) {
        return CompositeState.builder()
                .setLightmapState(LIGHTMAP)
                .setShaderState(shard)
                .setTextureState(BLOCK_SHEET_MIPPED)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setOutputState(TRANSLUCENT_TARGET)
                .createCompositeState(true);
    }

    public static final RenderType ADD = create("translucent",
            DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS,
            2097152, true, true,
            addState(RENDERTYPE_TRANSLUCENT_SHADER));
}
