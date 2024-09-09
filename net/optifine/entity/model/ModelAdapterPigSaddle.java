package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterPigSaddle extends ModelAdapterQuadruped {
   public ModelAdapterPigSaddle() {
      super(EntityType.f_20510_, "pig_saddle", 0.7F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new PigModel(bakeModelLayer(ModelLayers.f_171160_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PigRenderer customRenderer = new PigRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new PigModel(bakeModelLayer(ModelLayers.f_171160_));
      customRenderer.f_114477_ = 0.7F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20510_, index, () -> customRenderer);
      if (!(render instanceof PigRenderer renderPig)) {
         Config.warn("Not a PigRenderer: " + render);
         return null;
      } else {
         net.minecraft.client.renderer.entity.layers.SaddleLayer layer = new net.minecraft.client.renderer.entity.layers.SaddleLayer(
            renderPig, (PigModel)modelBase, new net.minecraft.resources.ResourceLocation("textures/entity/pig/pig_saddle.png")
         );
         renderPig.removeLayers(net.minecraft.client.renderer.entity.layers.SaddleLayer.class);
         renderPig.m_115326_(layer);
         return renderPig;
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      PigRenderer renderer = (PigRenderer)er;

      for (net.minecraft.client.renderer.entity.layers.SaddleLayer layer : renderer.getLayers(net.minecraft.client.renderer.entity.layers.SaddleLayer.class)) {
         layer.f_117387_ = textureLocation;
      }

      return true;
   }
}
