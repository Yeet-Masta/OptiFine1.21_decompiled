package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterStrayOuter extends ModelAdapterStray {
   public ModelAdapterStrayOuter() {
      super(EntityType.f_20481_, "stray_outer", 0.7F);
   }

   @Override
   public Model makeModel() {
      return new SkeletonModel(bakeModelLayer(ModelLayers.f_171250_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      StrayRenderer customRenderer = new StrayRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new SkeletonModel(bakeModelLayer(ModelLayers.f_171250_));
      customRenderer.f_114477_ = 0.7F;
      EntityRenderer render = rendererCache.get(EntityType.f_20481_, index, () -> customRenderer);
      if (!(render instanceof StrayRenderer renderStray)) {
         Config.warn("Not a SkeletonModelRenderer: " + render);
         return null;
      } else {
         ResourceLocation STRAY_CLOTHES_LOCATION = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
         SkeletonClothingLayer layer = new SkeletonClothingLayer(
            renderStray, renderManager.getContext().m_174027_(), ModelLayers.f_171250_, STRAY_CLOTHES_LOCATION
         );
         layer.f_314940_ = (SkeletonModel<T>)modelBase;
         renderStray.removeLayers(SkeletonClothingLayer.class);
         renderStray.m_115326_(layer);
         return renderStray;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      StrayRenderer renderer = (StrayRenderer)er;

      for (SkeletonClothingLayer layer : renderer.getLayers(SkeletonClothingLayer.class)) {
         layer.f_316006_ = textureLocation;
      }

      return true;
   }
}
