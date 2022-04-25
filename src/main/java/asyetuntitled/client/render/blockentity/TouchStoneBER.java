package asyetuntitled.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import asyetuntitled.client.util.ClientPlayerSpawnPoints;
import asyetuntitled.common.block.BlocksRegistry;
import asyetuntitled.common.block.entity.TouchStoneBE;
import asyetuntitled.common.player.spawn.SpawnPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.client.model.data.EmptyModelData;

public class TouchStoneBER implements BlockEntityRenderer<TouchStoneBE>
{
	private final BlockEntityRendererProvider.Context context;
	
	public TouchStoneBER(BlockEntityRendererProvider.Context context)
	{
		this.context = context;
	}

	@Override
	public void render(TouchStoneBE touchstone, float partialTicks, PoseStack stack, MultiBufferSource buffer,
			int combinedOverlay, int packedLight) 
	{
		BlockRenderDispatcher dispatcher = this.context.getBlockRenderDispatcher();
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;
		
		//Renders the main touchstone
		stack.pushPose();
		stack.scale(1.0f, 1.0f, 1.0f);
		dispatcher.renderSingleBlock(BlocksRegistry.TOUCHSTONE_BASE.get().defaultBlockState(), stack, buffer, combinedOverlay, packedLight, EmptyModelData.INSTANCE);
		stack.popPose();
		
		
		//Gets the rune for render and enchants if the player can spawn at this touchstone
		final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		final ItemStack rune = touchstone.getRune();
		if(ClientPlayerSpawnPoints.isSpawnPoint(new SpawnPoint(touchstone.getBlockPos(), touchstone.getLevel().dimension()))) 
		{
			rune.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
		}
		stack.pushPose();
		stack.translate(0.5D, 0.5D, 0.5D);
		stack.scale(1.0F, 1.4F, 1.0F);
		stack.mulPose(Vector3f.XP.rotationDegrees(90));
		itemRenderer.renderStatic(player, rune, TransformType.GUI, false, stack, buffer, touchstone.getLevel(), combinedOverlay, packedLight, 0);
		stack.popPose();
	}

}
