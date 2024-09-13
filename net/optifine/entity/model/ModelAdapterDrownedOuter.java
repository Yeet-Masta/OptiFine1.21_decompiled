package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterDrownedOuter extends ModelAdapterDrowned {
   public ModelAdapterDrownedOuter() {
      super(EntityType.f_20562_, "drowned_outer", 0.5F);
   }

   @Override
   public Model makeModel() {
      return new DrownedModel(bakeModelLayer(ModelLayers.f_171139_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      DrownedRenderer customRenderer = new DrownedRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new DrownedModel(bakeModelLayer(ModelLayers.f_171139_));
      customRenderer.f_114477_ = 0.75F;
      EntityRenderer render = rendererCache.get(EntityType.f_20562_, index, () -> customRenderer);
      if (!(render instanceof DrownedRenderer renderDrowned)) {
         Config.warn("Not a DrownedRenderer: " + render);
         return null;
      } else {
         DrownedOuterLayer layer = new DrownedOuterLayer(renderDrowned, renderManager.getContext().m_174027_());
         layer.f_116908_ = (DrownedModel<T>)modelBase;
         renderDrowned.removeLayers(DrownedOuterLayer.class);
         renderDrowned.m_115326_(layer);
         return renderDrowned;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      DrownedRenderer renderer = (DrownedRenderer)er;

      for (DrownedOuterLayer layer : renderer.getLayers(DrownedOuterLayer.class)) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
