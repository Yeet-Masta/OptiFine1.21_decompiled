package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3800_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4310_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterBat extends ModelAdapter {
   public ModelAdapterBat() {
      super(C_513_.f_20549_, "bat", 0.25F);
   }

   public C_3840_ makeModel() {
      return new C_3800_(bakeModelLayer(C_141656_.f_171265_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3800_ modelBat)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelBat.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("body")) {
         return modelBat.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("right_wing")) {
         return modelBat.m_142109_().getChildModelDeep("right_wing");
      } else if (modelPart.equals("left_wing")) {
         return modelBat.m_142109_().getChildModelDeep("left_wing");
      } else if (modelPart.equals("outer_right_wing")) {
         return modelBat.m_142109_().getChildModelDeep("right_wing_tip");
      } else if (modelPart.equals("outer_left_wing")) {
         return modelBat.m_142109_().getChildModelDeep("left_wing_tip");
      } else if (modelPart.equals("feet")) {
         return modelBat.m_142109_().getChildModelDeep("feet");
      } else {
         return modelPart.equals("root") ? modelBat.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing", "feet", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4310_ render = new C_4310_(renderManager.getContext());
      render.g = (C_3800_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
