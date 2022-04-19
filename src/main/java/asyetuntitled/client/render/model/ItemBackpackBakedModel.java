package asyetuntitled.client.render.model;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

public class ItemBackpackBakedModel implements IDynamicBakedModel {

	private final ItemOverrides overrideList = ItemOverrides.EMPTY;

	private static final Map<ItemTransforms.TransformType, Transformation> TRANSFORMS;

	static {
		ImmutableMap.Builder<ItemTransforms.TransformType, Transformation> builder = ImmutableMap.builder();
		builder.put(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, new Transformation(
				new Vector3f(0, -2 / 16f, -4.5f / 16f),
				new Quaternion(85, -90, 0, true),
				new Vector3f(0.75f, 0.75f, 0.75f), null
		));
		builder.put(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, new Transformation(
				new Vector3f(0, -2 / 16f, -4.5f / 16f),
				new Quaternion(85, -90, 0, true),
				new Vector3f(0.75f, 0.75f, 0.75f), null
		));
		builder.put(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, new Transformation(
				new Vector3f(0, 0, 0),
				new Quaternion(0, 0, 0, true),
				new Vector3f(0.5f, 0.5f, 0.5f), null
		));
		builder.put(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, new Transformation(
				new Vector3f(0, 0, 0),
				new Quaternion(0, 0, 0, true),
				new Vector3f(0.5f, 0.5f, 0.5f), null
		));
		builder.put(ItemTransforms.TransformType.HEAD, new Transformation(
				new Vector3f(0, 14.25f / 16f, 0),
				new Quaternion(0, 0, 0, true),
				new Vector3f(1, 1, 1), null
		));
		builder.put(ItemTransforms.TransformType.GUI, new Transformation(
				new Vector3f(0, 1.25f / 16f, 0),
				new Quaternion(30, 225, 0, true),
				new Vector3f(0.9f, 0.9f, 0.9f), null
		));
		builder.put(ItemTransforms.TransformType.GROUND, new Transformation(
				new Vector3f(0, 3 / 16f, 0),
				new Quaternion(0, 0, 0, true),
				new Vector3f(0.5f, 0.5f, 0.5f), null
		));
		builder.put(ItemTransforms.TransformType.FIXED, new Transformation(
				new Vector3f(0, 0, -2.25f / 16f),
				new Quaternion(0, 0, 0, true),
				new Vector3f(0.75f, 0.75f, 0.75f), null
		));
		TRANSFORMS = builder.build();
	}
	@Override
	public boolean useAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean usesBlockLight() {
		return true;
	}

	@Override
	public boolean isCustomRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return null;
	}

	@Override
	public ItemOverrides getOverrides()
	{
		return overrideList;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) 
	{
		return null;
	}

	@Override
	public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack matrixStack) {
		if (cameraTransformType == ItemTransforms.TransformType.NONE) {
			return this;
		}

		Transformation tr = TRANSFORMS.get(cameraTransformType);

		if (!tr.isIdentity()) {
			tr.push(matrixStack);
		}
		return this;
	}


}
