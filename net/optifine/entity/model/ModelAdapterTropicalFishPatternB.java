package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishPatternB extends ModelAdapterTropicalFishB {
   public ModelAdapterTropicalFishPatternB() {
      super(EntityType.f_20489_, "tropical_fish_pattern_b", 0.2F);
   }

   @Override
   public Model makeModel() {
      return new TropicalFishModelB(bakeModelLayer(ModelLayers.f_171257_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      TropicalFishRenderer customRenderer = new TropicalFishRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new TropicalFishModelB(bakeModelLayer(ModelLayers.f_171257_));
      customRenderer.f_114477_ = 0.2F;
      EntityRenderer render = rendererCache.get(EntityType.f_20489_, index, () -> customRenderer);
      if (!(render instanceof TropicalFishRenderer renderTropicalFish)) {
         Config.warn("Not a RenderTropicalFish: " + render);
         return null;
      } else {
         TropicalFishPatternLayer layer = (TropicalFishPatternLayer)renderTropicalFish.getLayer(TropicalFishPatternLayer.class);
         if (layer == null || !layer.custom) {
            layer = new TropicalFishPatternLayer(renderTropicalFish, renderManager.getContext().m_174027_());
            layer.custom = true;
         }

         if (!Reflector.TropicalFishPatternLayer_modelB.exists()) {
            Config.warn("Field not found: TropicalFishPatternLayer.modelB");
            return null;
         } else {
            Reflector.TropicalFishPatternLayer_modelB.setValue(layer, modelBase);
            renderTropicalFish.removeLayers(TropicalFishPatternLayer.class);
            renderTropicalFish.m_115326_(layer);
            return renderTropicalFish;
         }
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      TropicalFishRenderer renderTropicalFish = (TropicalFishRenderer)er;

      for (TropicalFishPatternLayer layer : renderTropicalFish.getLayers(TropicalFishPatternLayer.class)) {
         TropicalFishModelB modelB = (TropicalFishModelB)Reflector.TropicalFishPatternLayer_modelB.getValue(layer);
         if (modelB != null) {
            modelB.locationTextureCustom = textureLocation;
         }
      }

      return true;
   }
}
