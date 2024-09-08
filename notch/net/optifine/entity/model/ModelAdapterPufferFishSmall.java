package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3857_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4379_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishSmall extends ModelAdapter {
   public ModelAdapterPufferFishSmall() {
      super(C_513_.f_20516_, "puffer_fish_small", 0.2F);
   }

   public C_3840_ makeModel() {
      return new C_3857_(bakeModelLayer(C_141656_.f_171173_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3857_ modelPufferFishSmall)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("eye_right")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("right_eye");
      } else if (modelPart.equals("eye_left")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("left_eye");
      } else if (modelPart.equals("fin_right")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("left_fin");
      } else if (modelPart.equals("tail")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("back_fin");
      } else {
         return modelPart.equals("root") ? modelPufferFishSmall.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "eye_right", "eye_left", "tail", "fin_right", "fin_left", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4379_ customRenderer = new C_4379_(renderManager.getContext());
      customRenderer.e = shadowSize;
      C_4331_ render = rendererCache.get(C_513_.f_20516_, index, () -> customRenderer);
      if (!(render instanceof C_4379_ renderFish)) {
         Config.warn("Not a PufferfishRenderer: " + render);
         return null;
      } else if (!Reflector.RenderPufferfish_modelSmall.exists()) {
         Config.warn("Model field not found: RenderPufferfish.modelSmall");
         return null;
      } else {
         Reflector.RenderPufferfish_modelSmall.setValue(renderFish, modelBase);
         return renderFish;
      }
   }
}
