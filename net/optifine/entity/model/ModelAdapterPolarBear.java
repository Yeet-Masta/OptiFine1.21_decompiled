package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PolarBearModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterPolarBear extends ModelAdapterQuadruped {
   public ModelAdapterPolarBear() {
      super(EntityType.f_20514_, "polar_bear", 0.7F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new PolarBearModel(bakeModelLayer(ModelLayers.f_171170_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PolarBearRenderer render = new PolarBearRenderer(renderManager.getContext());
      render.f_115290_ = (PolarBearModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
