package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3871_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4390_;
import net.minecraft.src.C_513_;

public class ModelAdapterSnowman extends ModelAdapter {
   public ModelAdapterSnowman() {
      super(C_513_.f_20528_, "snow_golem", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_3871_(bakeModelLayer(C_141656_.f_171243_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3871_ modelSnowman)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelSnowman.m_142109_().getChildModelDeep("upper_body");
      } else if (modelPart.equals("body_bottom")) {
         return modelSnowman.m_142109_().getChildModelDeep("lower_body");
      } else if (modelPart.equals("head")) {
         return modelSnowman.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("right_hand")) {
         return modelSnowman.m_142109_().getChildModelDeep("right_arm");
      } else if (modelPart.equals("left_hand")) {
         return modelSnowman.m_142109_().getChildModelDeep("left_arm");
      } else {
         return modelPart.equals("root") ? modelSnowman.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "body_bottom", "head", "right_hand", "left_hand", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4390_ render = new C_4390_(renderManager.getContext());
      render.g = (C_3871_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
