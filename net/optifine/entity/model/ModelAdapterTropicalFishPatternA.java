package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishPatternA extends ModelAdapterTropicalFishA {
   public ModelAdapterTropicalFishPatternA() {
      super(EntityType.f_20489_, "tropical_fish_pattern_a", 0.2F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new TropicalFishModelA(bakeModelLayer(ModelLayers.f_171259_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      TropicalFishRenderer customRenderer = new TropicalFishRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new TropicalFishModelA(bakeModelLayer(ModelLayers.f_171259_));
      customRenderer.f_114477_ = 0.2F;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20489_, index, () -> customRenderer);
      if (!(render instanceof TropicalFishRenderer renderTropicalFish)) {
         Config.warn("Not a RenderTropicalFish: " + render);
         return null;
      } else {
         TropicalFishPatternLayer layer = (TropicalFishPatternLayer)renderTropicalFish.getLayer(TropicalFishPatternLayer.class);
         if (layer == null || !layer.custom) {
            layer = new TropicalFishPatternLayer(renderTropicalFish, renderManager.getContext().m_174027_());
            layer.custom = true;
         }

         if (!Reflector.TropicalFishPatternLayer_modelA.exists()) {
            Config.warn("Field not found: TropicalFishPatternLayer.modelA");
            return null;
         } else {
            Reflector.TropicalFishPatternLayer_modelA.setValue(layer, modelBase);
            renderTropicalFish.removeLayers(TropicalFishPatternLayer.class);
            renderTropicalFish.m_115326_(layer);
            return renderTropicalFish;
         }
      }
   }

   @Override
   public boolean setTextureLocation(IEntityRenderer er, net.minecraft.resources.ResourceLocation textureLocation) {
      TropicalFishRenderer renderTropicalFish = (TropicalFishRenderer)er;

      for (TropicalFishPatternLayer layer : renderTropicalFish.getLayers(TropicalFishPatternLayer.class)) {
         TropicalFishModelA modelA = (TropicalFishModelA)Reflector.TropicalFishPatternLayer_modelA.getValue(layer);
         if (modelA != null) {
            modelA.locationTextureCustom = textureLocation;
         }
      }

      return true;
   }
}
