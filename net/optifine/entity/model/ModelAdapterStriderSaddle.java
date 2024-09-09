package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterStriderSaddle extends ModelAdapterStrider {
   public ModelAdapterStriderSaddle() {
      super(EntityType.f_20482_, "strider_saddle", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new StriderModel(bakeModelLayer(ModelLayers.f_171252_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      StriderRenderer customRenderer = new StriderRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new StriderModel(bakeModelLayer(ModelLayers.f_171252_));
      customRenderer.f_114477_ = 0.5F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20482_, index, () -> customRenderer);
      if (!(render instanceof StriderRenderer renderStrider)) {
         Config.warn("Not a StriderRenderer: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.SaddleLayer layer = new net.minecraft.client.renderer.entity.layers.SaddleLayer(
            renderStrider, (StriderModel)modelBase, new net.minecraft.resources.ResourceLocation("textures/entity/strider/strider_saddle.png")
         );
         renderStrider.removeLayers(net.minecraft.client.renderer.entity.layers.SaddleLayer.class);
         renderStrider.m_115326_(layer);
         return renderStrider;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      StriderRenderer renderer = (StriderRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.SaddleLayer layer : renderer.getLayers(net.minecraft.client.renderer.entity.layers.SaddleLayer.class)) {
         layer.f_117387_ = textureLocation;
      }

      return true;
   }
}
