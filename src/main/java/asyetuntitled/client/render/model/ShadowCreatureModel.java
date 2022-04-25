package asyetuntitled.client.render.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.PathfinderMob;

public class ShadowCreatureModel<T extends PathfinderMob> extends HierarchicalModel<T> {
	
	public ShadowCreatureModel(ModelPart part) {}
	   
	public ModelPart root() { return null; 	}

	@Override
	public void setupAnim(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}

}