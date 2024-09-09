package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishPatternA extends ModelAdapterTropicalFishA {
   public ModelAdapterTropicalFishPatternA() {
      super(EntityType.f_20489_, "tropical_fish_pattern_a", 0.2F);
   }

   public Model makeModel() {
      return new TropicalFishModelA(bakeModelLayer(ModelLayers.f_171259_));
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      TropicalFishRenderer customRenderer = new TropicalFishRenderer(renderManager.getContext());
      customRenderer.f_115290_ = new TropicalFishModelA(bakeModelLayer(ModelLayers.f_171259_));
      customRenderer.f_114477_ = 0.2F;
      EntityRenderer render = rendererCache.get(EntityType.f_20489_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof TropicalFishRenderer renderTropicalFish)) {
         Config.warn("Not a RenderTropicalFish: " + String.valueOf(render));
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

   public boolean setTextureLocation(IEntityRenderer er, ResourceLocation textureLocation) {
      TropicalFishRenderer renderTropicalFish = (TropicalFishRenderer)er;
      List layers = renderTropicalFish.getLayers(TropicalFishPatternLayer.class);
      Iterator var5 = layers.iterator();

      while(var5.hasNext()) {
         TropicalFishPatternLayer layer = (TropicalFishPatternLayer)var5.next();
         TropicalFishModelA modelA = (TropicalFishModelA)Reflector.TropicalFishPatternLayer_modelA.getValue(layer);
         if (modelA != null) {
            modelA.locationTextureCustom = textureLocation;
         }
      }

      return true;
   }
}
