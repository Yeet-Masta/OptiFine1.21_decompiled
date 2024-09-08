package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3855_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4379_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishBig extends ModelAdapter {
   public ModelAdapterPufferFishBig() {
      super(C_513_.f_20516_, "puffer_fish_big", 0.2F);
   }

   public C_3840_ makeModel() {
      return new C_3855_(bakeModelLayer(C_141656_.f_171171_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3855_ modelPufferFishBig)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("fin_right")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("right_blue_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("left_blue_fin");
      } else if (modelPart.equals("spikes_front_top")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("top_front_fin");
      } else if (modelPart.equals("spikes_middle_top")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("top_middle_fin");
      } else if (modelPart.equals("spikes_back_top")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("top_back_fin");
      } else if (modelPart.equals("spikes_front_right")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("right_front_fin");
      } else if (modelPart.equals("spikes_front_left")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("left_front_fin");
      } else if (modelPart.equals("spikes_front_bottom")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("bottom_front_fin");
      } else if (modelPart.equals("spikes_middle_bottom")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("bottom_middle_fin");
      } else if (modelPart.equals("spikes_back_bottom")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("bottom_back_fin");
      } else if (modelPart.equals("spikes_back_right")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("right_back_fin");
      } else if (modelPart.equals("spikes_back_left")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("left_back_fin");
      } else {
         return modelPart.equals("root") ? modelPufferFishBig.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{
         "body",
         "fin_right",
         "fin_left",
         "spikes_front_top",
         "spikes_middle_top",
         "spikes_back_top",
         "spikes_front_right",
         "spikes_front_left",
         "spikes_front_bottom",
         "spikes_middle_bottom",
         "spikes_back_bottom",
         "spikes_back_right",
         "spikes_back_left",
         "root"
      };
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4379_ customRenderer = new C_4379_(renderManager.getContext());
      customRenderer.e = shadowSize;
      C_4331_ render = rendererCache.get(C_513_.f_20516_, index, () -> customRenderer);
      if (!(render instanceof C_4379_ renderFish)) {
         Config.warn("Not a PufferfishRenderer: " + render);
         return null;
      } else if (!Reflector.RenderPufferfish_modelBig.exists()) {
         Config.warn("Model field not found: RenderPufferfish.modelBig");
         return null;
      } else {
         Reflector.RenderPufferfish_modelBig.setValue(renderFish, modelBase);
         return renderFish;
      }
   }
}
