package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterEnderman extends ModelAdapterBiped {
   public ModelAdapterEnderman() {
      super(EntityType.f_20566_, "enderman", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new EndermanModel(bakeModelLayer(ModelLayers.f_171142_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      EndermanRenderer render = new EndermanRenderer(renderManager.getContext());
      render.f_115290_ = (EndermanModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
