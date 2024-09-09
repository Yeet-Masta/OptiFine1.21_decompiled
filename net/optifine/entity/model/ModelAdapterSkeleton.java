package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterSkeleton extends ModelAdapterBiped {
   public ModelAdapterSkeleton() {
      super(EntityType.f_20524_, "skeleton", 0.7F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SkeletonModel(bakeModelLayer(ModelLayers.f_171236_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SkeletonRenderer render = new SkeletonRenderer(renderManager.getContext());
      render.f_115290_ = (SkeletonModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
