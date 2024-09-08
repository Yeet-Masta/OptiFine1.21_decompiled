package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3877_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4402_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishA extends ModelAdapter {
   public ModelAdapterTropicalFishA() {
      super(C_513_.f_20489_, "tropical_fish_a", 0.2F);
   }

   public ModelAdapterTropicalFishA(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3877_(bakeModelLayer(C_141656_.f_171258_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3877_ modelTropicalFish)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("tail")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("tail");
      } else if (modelPart.equals("fin_right")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("left_fin");
      } else if (modelPart.equals("fin_top")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("top_fin");
      } else {
         return modelPart.equals("root") ? modelTropicalFish.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "tail", "fin_right", "fin_left", "fin_top", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4402_ customRenderer = new C_4402_(renderManager.getContext());
      customRenderer.e = shadowSize;
      C_4331_ render = rendererCache.get(C_513_.f_20489_, index, () -> customRenderer);
      if (!(render instanceof C_4402_ renderFish)) {
         Config.warn("Not a TropicalFishRenderer: " + render);
         return null;
      } else if (!Reflector.RenderTropicalFish_modelA.exists()) {
         Config.warn("Model field not found: RenderTropicalFish.modelA");
         return null;
      } else {
         Reflector.RenderTropicalFish_modelA.setValue(renderFish, modelBase);
         return renderFish;
      }
   }
}
