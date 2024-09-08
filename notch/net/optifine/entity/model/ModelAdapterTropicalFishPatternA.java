package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3877_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4402_;
import net.minecraft.src.C_4457_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishPatternA extends ModelAdapterTropicalFishA {
   public ModelAdapterTropicalFishPatternA() {
      super(C_513_.f_20489_, "tropical_fish_pattern_a", 0.2F);
   }

   public C_3840_ makeModel() {
      return new C_3877_(bakeModelLayer(C_141656_.f_171259_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4402_ customRenderer = new C_4402_(renderManager.getContext());
      customRenderer.g = new C_3877_(bakeModelLayer(C_141656_.f_171259_));
      customRenderer.e = 0.2F;
      C_4331_ render = rendererCache.get(C_513_.f_20489_, index, () -> customRenderer);
      if (!(render instanceof C_4402_ renderTropicalFish)) {
         Config.warn("Not a RenderTropicalFish: " + render);
         return null;
      } else {
         C_4457_ layer = (C_4457_)renderTropicalFish.getLayer(C_4457_.class);
         if (layer == null || !layer.custom) {
            layer = new C_4457_(renderTropicalFish, renderManager.getContext().m_174027_());
            layer.custom = true;
         }

         if (!Reflector.TropicalFishPatternLayer_modelA.exists()) {
            Config.warn("Field not found: TropicalFishPatternLayer.modelA");
            return null;
         } else {
            Reflector.TropicalFishPatternLayer_modelA.setValue(layer, modelBase);
            renderTropicalFish.removeLayers(C_4457_.class);
            renderTropicalFish.a(layer);
            return renderTropicalFish;
         }
      }
   }

   public boolean setTextureLocation(IEntityRenderer er, C_5265_ textureLocation) {
      C_4402_ renderTropicalFish = (C_4402_)er;

      for (C_4457_ layer : renderTropicalFish.getLayers(C_4457_.class)) {
         C_3877_ modelA = (C_3877_)Reflector.TropicalFishPatternLayer_modelA.getValue(layer);
         if (modelA != null) {
            modelA.locationTextureCustom = textureLocation;
         }
      }

      return true;
   }
}
