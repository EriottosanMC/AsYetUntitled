package asyetuntitled.client.render.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;

@OnlyIn(Dist.CLIENT)
public class ItemBackpackModel implements IModelGeometry<ItemBackpackModel>
{
	private final Transformation THIRD_PERSON_HAND = new Transformation(new Vector3f(0, -2 / 16F, -4.5F / 16f),
																	new Quaternion(85, -90, 0, true),
																	new Vector3f(0.75F, 0.75F, 0.75f), null);
	

	private final Transformation FIRST_PERSON_HAND = new Transformation(new Vector3f(0, -2 / 16F, -4.5F / 16f),
																	new Quaternion(85, -90, 0, true),
																	new Vector3f(0.75F, 0.75F, 0.75f), null);

	private final Transformation HEAD = new Transformation(new Vector3f(0, 14.25F / 16F, 0),
															new Quaternion(0, 0, 0, true),
															new Vector3f(1, 1, 1), null);
	
	private final Transformation GUI = new Transformation(new Vector3f(0, 1.25F / 16F, 0),
															new Quaternion(30, 225, 0, true),
															new Vector3f(0.9F, 0.9F, 0.9F), null);

	private final Transformation GROUND = new Transformation(new Vector3f(0, 3 / 16F, 0),
																new Quaternion(0, 0, 0, true),
																new Vector3f(0.5F, 0.5F, 0.5F), null);

	private final Transformation FIXED = new Transformation(new Vector3f(0, 0, -2.25F / 16F),
																new Quaternion(0, 0, 0, true),
																new Vector3f(0.75F, 0.75F, 0.75F), null);


	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, 
			ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) 
	{
	    return new IDynamicBakedModel() {
			
			@Override
			public boolean usesBlockLight() {
				return true;
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
			public boolean isCustomRenderer() {
				return true;
			}
			
			@Override
			public TextureAtlasSprite getParticleIcon() {
				return null;
			}
			
			@Override
			public ItemOverrides getOverrides() {
				return ItemOverrides.EMPTY;
			}
			
			@Override
			public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
				return null;
			}
			
			@Override
			public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack matrixStack) {
				if (cameraTransformType == ItemTransforms.TransformType.NONE) {
					return this;
				}

				Transformation transformation = Transformation.identity();
				
				if(cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) transformation = THIRD_PERSON_HAND;
				else if(cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || cameraTransformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) transformation = FIRST_PERSON_HAND;
				else if(cameraTransformType == ItemTransforms.TransformType.HEAD) transformation = HEAD;
				else if(cameraTransformType == ItemTransforms.TransformType.GUI) transformation = GUI;
				else if(cameraTransformType == ItemTransforms.TransformType.FIXED) transformation = FIXED;
				else if(cameraTransformType == ItemTransforms.TransformType.GROUND) transformation = GROUND;

				if (!transformation.isIdentity()) {
					transformation.push(matrixStack);
				}
				return this;
			}
		};
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter,
			Set<Pair<String, String>> missingTextureErrors) 
	{
		return Collections.emptyList();
	}
	
}
