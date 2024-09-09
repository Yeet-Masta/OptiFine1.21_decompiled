package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterStray extends ModelAdapterBiped {
   public ModelAdapterStray() {
      super(EntityType.f_20481_, "stray", 0.7F);
   }

   public ModelAdapterStray(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SkeletonModel(bakeModelLayer(ModelLayers.f_171247_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      StrayRenderer render = new StrayRenderer(renderManager.getContext());
      render.f_115290_ = (SkeletonModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
