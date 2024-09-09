package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterStrayOuter extends ModelAdapterStray {
   public ModelAdapterStrayOuter() {
      super(EntityType.f_20481_, "stray_outer", 0.7F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SkeletonModel(bakeModelLayer(ModelLayers.f_171250_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      StrayRenderer customRenderer = new StrayRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new SkeletonModel(bakeModelLayer(ModelLayers.f_171250_));
      customRenderer.f_114477_ = 0.7F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20481_, index, () -> customRenderer);
      if (!(render instanceof StrayRenderer renderStray)) {
         Config.warn("Not a SkeletonModelRenderer: " + render);
         return null;
      } else {
         net.minecraft.resources.ResourceLocation STRAY_CLOTHES_LOCATION = new net.minecraft.resources.ResourceLocation(
            "textures/entity/skeleton/stray_overlay.png"
         );
         net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer layer = new net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer(
            renderStray, renderManager.getContext().m_174027_(), ModelLayers.f_171250_, STRAY_CLOTHES_LOCATION
         );
         layer.f_314940_ = (SkeletonModel<T>)modelBase;
         renderStray.removeLayers(net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer.class);
         renderStray.m_115326_(layer);
         return renderStray;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      StrayRenderer renderer = (StrayRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer layer : renderer.getLayers(
         net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer.class
      )) {
         layer.f_316006_ = textureLocation;
      }

      return true;
   }
}
