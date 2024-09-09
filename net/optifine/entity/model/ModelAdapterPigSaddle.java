package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterPigSaddle extends ModelAdapterQuadruped {
   public ModelAdapterPigSaddle() {
      super(EntityType.f_20510_, "pig_saddle", 0.7F);
   }

   public Model makeModel() {
      return new PigModel(bakeModelLayer(ModelLayers.f_171160_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PigRenderer customRenderer = new PigRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new PigModel(bakeModelLayer(ModelLayers.f_171160_));
      customRenderer.f_114477_ = 0.7F;
      EntityRenderer render = rendererCache.get(EntityType.f_20510_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof PigRenderer renderPig)) {
         Config.warn("Not a PigRenderer: " + String.valueOf(render));
         return null;
      } else {
         SaddleLayer layer = new SaddleLayer(renderPig, (PigModel)modelBase, new ResourceLocation("textures/entity/pig/pig_saddle.png"));
         renderPig.removeLayers(SaddleLayer.class);
         renderPig.m_115326_(layer);
         return renderPig;
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      PigRenderer renderer = (PigRenderer)er;
      List layers = renderer.getLayers(SaddleLayer.class);

      SaddleLayer layer;
      for(Iterator var5 = layers.iterator(); var5.hasNext(); layer.f_117387_ = textureLocation) {
         layer = (SaddleLayer)var5.next();
      }

      return true;
   }
}
