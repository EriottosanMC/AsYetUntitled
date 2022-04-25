package asyetuntitled.client.render.item;

import javax.annotation.Nonnull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import asyetuntitled.client.render.model.ItemBackpackModel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public enum BackpackItemStackLoader implements IModelLoader<ItemBackpackModel> 
{
	INSTANCE;

	@Override
	public void onResourceManagerReload(@Nonnull ResourceManager resourceManager) {}

	@Nonnull
	@Override
	public ItemBackpackModel read(@Nonnull JsonDeserializationContext deserializationContext, @Nonnull JsonObject modelContents) 
	{
		return new ItemBackpackModel();
		
	}
	
}
