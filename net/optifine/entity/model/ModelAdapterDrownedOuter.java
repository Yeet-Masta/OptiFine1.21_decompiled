package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterDrownedOuter extends ModelAdapterDrowned {
   public ModelAdapterDrownedOuter() {
      super(EntityType.f_20562_, "drowned_outer", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new DrownedModel(bakeModelLayer(ModelLayers.f_171139_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      DrownedRenderer customRenderer = new DrownedRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new DrownedModel(bakeModelLayer(ModelLayers.f_171139_));
      customRenderer.f_114477_ = 0.75F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20562_, index, () -> customRenderer);
      if (!(render instanceof DrownedRenderer renderDrowned)) {
         Config.warn("Not a DrownedRenderer: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.DrownedOuterLayer layer = new net.minecraft.client.renderer.entity.layers.DrownedOuterLayer(
            renderDrowned, renderManager.getContext().m_174027_()
         );
         layer.f_116908_ = (DrownedModel<T>)modelBase;
         renderDrowned.removeLayers(net.minecraft.client.renderer.entity.layers.DrownedOuterLayer.class);
         renderDrowned.m_115326_(layer);
         return renderDrowned;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      DrownedRenderer renderer = (DrownedRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.DrownedOuterLayer layer : renderer.getLayers(
         net.minecraft.client.renderer.entity.layers.DrownedOuterLayer.class
      )) {
         layer.customTextureLocation = textureLocation;
      }

      return true;
   }
}
