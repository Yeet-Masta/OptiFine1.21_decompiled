package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterStriderSaddle extends ModelAdapterStrider {
   public ModelAdapterStriderSaddle() {
      super(EntityType.f_20482_, "strider_saddle", 0.5F);
   }

   @Override
   public Model makeModel() {
      return new StriderModel(bakeModelLayer(ModelLayers.f_171252_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      StriderRenderer customRenderer = new StriderRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new StriderModel(bakeModelLayer(ModelLayers.f_171252_));
      customRenderer.f_114477_ = 0.5F;
      EntityRenderer render = rendererCache.get(EntityType.f_20482_, index, () -> customRenderer);
      if (!(render instanceof StriderRenderer renderStrider)) {
         Config.warn("Not a StriderRenderer: " + render);
         return null;
      } else {
         SaddleLayer layer = new SaddleLayer(renderStrider, (StriderModel)modelBase, new ResourceLocation("textures/entity/strider/strider_saddle.png"));
         renderStrider.removeLayers(SaddleLayer.class);
         renderStrider.m_115326_(layer);
         return renderStrider;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      StriderRenderer renderer = (StriderRenderer)er;

      for (SaddleLayer layer : renderer.getLayers(SaddleLayer.class)) {
         layer.f_117387_ = textureLocation;
      }

      return true;
   }
}
